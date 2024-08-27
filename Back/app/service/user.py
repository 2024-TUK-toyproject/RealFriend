from fastapi import HTTPException, status, Depends, UploadFile
from sqlalchemy.orm import Session
from sqlalchemy import and_, or_


from ..database import Database
from .. import models
from ..schemes import *
from ..random_generator import RandomNumberGenerator
from ..config import Config
from ..util import JWTService
from ..httpException import CustomException, CustomException2
from ..fcm_service import send_push_notification

from datetime import datetime, timedelta

import boto3
import uuid
import urllib

s3 = boto3.client(
    "s3",
    aws_access_key_id=Config.s3_access_key,
    aws_secret_access_key=Config.s3_secret_key
)



class User_service:
    def __init__(self, db: Session = Depends(Database().get_session)):
        self.db = db
        self.today = datetime.now()
        self.rng = RandomNumberGenerator()
        self.jwt = JWTService()

    async def register_user(self, user_info : User_info_request) -> CommoneResponse:
        
        existing_temp_user = self.db.query(models.temp_user_info).filter(models.temp_user_info.phone == user_info.phone).first()

        phone_num = self.format_phone_number(user_info.phone)

        if not existing_temp_user:
            new_temp_user = models.temp_user_info(
                phone = phone_num,
                create_date = self.today.strftime('%Y-%m-%d')
            )
            self.db.add(new_temp_user)
            self.db.commit()
        else:
            existing_temp_user.create_date = self.today.strftime('%Y-%m-%d')
            self.db.commit()

        return CommoneResponse(status = "success", message = "사용자 등록 성공")
    
    # 인증번호로직 추후에 추가
    async def certification_user(self, request : Certificate_request) -> Certificate_response:
        if request.code:
            pass
        request_phone = self.format_phone_number(request.phone)
        user = self.db.query(models.temp_user_info).filter(models.temp_user_info.phone == request_phone).first()

        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.phone == request.phone).first()
        if existing_user is not None:
            existing_user.last_login_date = self.today.strftime('%Y-%m-%d')
            self.db.query(models.temp_user_info).filter(models.temp_user_info.phone == request.phone).delete()
            new_token = self.jwt.create_access_token(existing_user.phone, existing_user.user_id)
            refresh_token = self.jwt.create_refresh_token(existing_user.phone, existing_user.user_id)
            existing_user.refresh_token = refresh_token
            self.db.commit()
            
            return Certificate_response(status = "success", message = "인증 성공", content = {"userId" : str(existing_user.user_id), "isExist" : True, "accessToken" : new_token, "refreshToken" : refresh_token})
        
        for _ in range(10):
            new_id = self.rng.generate_unique_random_number(100000, 999999)
        
        phone_num = self.format_phone_number(request.phone)

        new_user = models.user_info(
            user_id = new_id,
            phone = phone_num,
            create_date = user.create_date,
            last_login_date = self.today.strftime('%Y-%m-%d'),
            refresh_token = self.jwt.create_refresh_token(phone_num, new_id)
        )
        new_token = self.jwt.create_access_token(phone_num, new_id)
        self.db.add(new_user)
        self.db.commit()

        # temp_user_info 삭제
        self.db.query(models.temp_user_info).filter(models.temp_user_info.phone == request.phone).delete()
        self.db.commit()
        return Certificate_response(status = "success", message = "인증 성공", content = {"userId" : str(new_id), "isExist" : False, "accessToken" : new_token, "refreshToken" : new_user.refresh_token})
    
    async def set_profile(self, userId : str, name : str, fcmToken : str, file : UploadFile) -> CommoneResponse:
        user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        existing_fcm_token = self.db.query(models.fcm_token_info).filter(models.fcm_token_info.user_id == userId).first()
        if existing_fcm_token:
            existing_fcm_token.fcm_token = fcmToken
        else:
            new_fcm_token = models.fcm_token_info(
                user_id = userId,
                fcm_token = fcmToken
            )
            self.db.add(new_fcm_token)
        
        if file:
            filen_name = f"{str(uuid.uuid4())}.jpeg"
            s3_key = f"profile/{userId}/{filen_name}"

            s3.upload_fileobj(
                file.file,
                Config.s3_bucket,
                s3_key
            )

            user.profile_image = f"https://%s.s3.amazonaws.com/profile/%s/%s" % (
                Config.s3_bucket,
                userId,
                urllib.parse.quote(filen_name)
            )
        else:
            user.profile_image = f"https://%s.s3.amazonaws.com/profile/default_profile/default.png" % Config.s3_bucket
        
        user.name = name

        self.db.commit()

        return CommoneResponse(status = "success", message = "프로필 사진 등록 성공")
    
    async def get_profile(self, token : str) -> Get_profile_response:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        print(user["key"])
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        print(existing_user.name, existing_user.phone, existing_user.profile_image)
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        return Get_profile_response(
            status = "success",
            message = "프로필 조회 성공",
            content = dict(
                name = existing_user.name,
                phone = existing_user.phone,
                profileImage = existing_user.profile_image
            )
        )
    
    async def modify_profile(self, token : str, file : UploadFile) -> Profile_modify_response:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        filen_name = f"{str(uuid.uuid4())}.jpeg"
        s3_key = f"profile/{existing_user.user_id}/{filen_name}"

        s3.upload_fileobj(
            file.file,
            Config.s3_bucket,
            s3_key
        )

        user.profile_image = f"https://%s.s3.amazonaws.com/profile/%s/%s" % (
            Config.s3_bucket,
            user["key"],
            urllib.parse.quote(filen_name)
        )

        self.db.commit()

        return Profile_modify_response(status = "success", message = "프로필 사진 수정 성공", content = {"profileImage" : existing_user.profile_image})
    
    async def add_friend(self, request : Add_friend_request, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        friend = self.db.query(models.user_info).filter(models.user_info.user_id == request.friendId).first()
        if friend is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="친구 정보가 없습니다.")
        
        is_friend = self.db.query(models.is_friend).filter(
            or_(
                and_(
                    models.is_friend.to_user_id == user["key"],
                    models.is_friend.from_user_id == request.friendId
                ),
                and_(
                    models.is_friend.to_user_id == request.friendId,
                    models.is_friend.from_user_id == user["key"]
                )
            )
        ).first()
        if is_friend is not None:
            if is_friend.is_friend == True:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="이미 친구입니다.")
            else:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="이미 친구 요청을 보냈습니다.")
        
        for _ in range(10):
            new_id = self.rng.generate_unique_random_number(100000, 999999)

        new_friend = models.is_friend(
            friend_id = new_id,
            to_user_id = request.friendId,
            from_user_id = user["key"],
            is_friend = False,
            create_date = self.today.strftime('%Y-%m-%d'),
            create_time = self.today.strftime('%H:%M:%S')
        )
        self.db.add(new_friend)
        self.db.commit()

        #fcm 알림
        friend_fcm_token = self.db.query(models.fcm_token_info).filter(models.fcm_token_info.user_id == request.friendId).first()

        if friend_fcm_token:
            data = {
                "title" : "CONNEX",
                "body" : "%s(으)로 부터 친구 요청이 왔습니다."%{existing_user.name},
            }
            await send_push_notification(friend_fcm_token.fcm_token, data)

        return CommoneResponse(status = "success", message = "친구 요청 성공")

    async def reject_friend(self, request : Reject_friend_request, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)

        if user is None:
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        friend = self.db.query(models.is_friend).filter(models.is_friend.friend_id == request.friendRequestId).first()
        if friend is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="친구 요청 정보가 없습니다.")
        
        self.db.query(models.is_friend).filter(models.is_friend.friend_id == request.friendRequestId).delete()
        self.db.commit()

        return CommoneResponse(status = "success", message = "친구 요청 거절 성공")

    async def login(self, token : str) -> Login_response:
        user = self.jwt.check_token_expired(token)
        if user is None:
            print(1)
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        
        user_info = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if user_info is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        if token == user_info.refresh_token:
            new_access_token = self.jwt.create_access_token(user_info.phone, user_info.user_id)
            new_refresh_token = self.jwt.create_refresh_token(user_info.phone, user_info.user_id)
            user_info.refresh_token = new_refresh_token
            self.db.commit()
            return Login_response(status = "success", message = "new token 발급 완료", content = {"accessToken" : new_access_token, "refreshToken" : new_refresh_token})
        
        user_info.last_login_date = self.today.strftime('%Y-%m-%d')
        self.db.commit()

        return Login_response(status = "success", message = "로그인 성공", content = {"accessToken" : None, "refreshToken" : None})

    async def get_friend_list(self, token : str) -> Friend_list_response:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        friends = self.db.query(models.is_friend).filter(
            and_(
                or_(
                    models.is_friend.to_user_id == user["key"],
                    models.is_friend.from_user_id == user["key"]
                ),
                models.is_friend.is_friend == True  
            )
        ).all()
        friend_list = []
        for friend in friends:
            if friend.to_user_id == user["key"]:
                fri = self.db.query(models.user_info).filter(models.user_info.user_id == friend.from_user_id).first()
            else:
                fri = self.db.query(models.user_info).filter(models.user_info.user_id == friend.to_user_id).first()
            friend_list.append({
                "userId" : fri.user_id,
                "name" : fri.name,
                "phone" : fri.phone,
                "profileImage" : fri.profile_image,
                "isFriend" : True
            })
        phone_list = self.db.query(models.phone_info).filter(models.phone_info.user_id == user["key"]).all()
        for phone in phone_list:
            if phone.is_friend == False:
                friend_list.append({
                    "userId" : str(self.rng.generate_unique_random_number(10000,99999)),   #임의의 중복되지 않는 id
                    "name" : phone.name,
                    "phone" : phone.phone,
                    "profileImage" : f"https://%s.s3.amazonaws.com/profile/default_profile/default.png" % Config.s3_bucket,
                    "isFriend" : False
                })
        
        #friend_list 이름을 가나다순 정렬
        friend_list = sorted(friend_list, key = lambda x: x["name"])

        return Friend_list_response(status = "success", message = "친구 리스트 조회 성공", content = friend_list)
    
    async def get_friend_request_list(self, token : str) -> Friend_request_list_response:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        request_friends = self.db.query(models.is_friend).filter(
            and_(
                models.is_friend.to_user_id == user["key"],
                models.is_friend.is_friend == False
            )
        ).all()

        friend_list = {
            "오늘" : [],
            "어제" : []
        }
        yesterday = self.today - timedelta(days = 1)
        #날짜별 분류
        for request in request_friends:
            friend = self.db.query(models.user_info).filter(models.user_info.user_id == request.from_user_id).first()
            print(request.create_date)
            print(self.today.strftime('%Y-%m-%d'))
            if request.create_date == self.today.strftime('%Y-%m-%d'):
                friend_list["오늘"].append({
                    "friendRequestId" : request.friend_id,
                    "userId" : request.from_user_id,
                    "name" : friend.name,
                    "phone" : friend.phone,
                    "profileImage" : friend.profile_image, 
                    "time" : request.create_time
                })
            elif friend.create_date == yesterday:
                friend_list["어제"].append({
                    "friendRequestId" : request.friend_id,
                    "userId" : request.from_user_id,
                    "name" : friend.name,
                    "phone" : friend.phone,
                    "profileImage" : friend.profile_image,
                    "time" : request.create_time
                })
            else:
                date = request.create_date
                if friend_list.get(date) is None:
                    friend_list[date] = []
                friend_list[date].append({
                    "friendRequestId" : request.friend_id,
                    "userId" : request.from_user_id,
                    "name" : friend.name,
                    "phone" : friend.phone,
                    "profileImage" : friend.profile_image,
                    "time" : request.create_time
                })
            
            #friend_list = {key: value for key, value in friend_list.items() if value}

        
        return Friend_request_list_response(status = "success", message = "친구 요청 리스트 조회 성공", content = friend_list)

    async def accept_friend(self, request : Accept_friend_request, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        friend = self.db.query(models.is_friend).filter(models.is_friend.friend_id == request.friendRequestId).first()
        if friend is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="친구 요청 정보가 없습니다.")
        
        friend.is_friend = True

        #기존 사용자였는지 확인
        is_friend_before = self.db.query(models.user_info).filter(models.user_info.user_id == friend.from_user_id).first()
        if is_friend_before:
            phone = self.db.query(models.phone_info).filter(
                and_(
                    models.phone_info.user_id == user["key"],
                    models.phone_info.phone == is_friend_before.phone
                )
            ).first()
            if phone:
                phone.is_friend = True
        else:
            pass
        self.db.commit()

        return CommoneResponse(status = "success", message = "친구 요청 수락 성공")

    async def delete_friend(self, request : Delete_friend_request, token : str) -> CommoneResponse:  
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰 만료")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        for re_friend in request.content:
            print(re_friend)
            friend = self.db.query(models.is_friend).filter(
                or_(
                    and_(
                        models.is_friend.to_user_id == user["key"],
                        models.is_friend.from_user_id == re_friend["friendId"]
                    ),
                    and_(
                        models.is_friend.to_user_id == re_friend["friendId"],
                        models.is_friend.from_user_id == user["key"]
                    )
                )
            ).first()
            if friend is None:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail=f"(userId : %s) 친구 정보가 없습니다." % re_friend["friendId"])
            
            self.db.query(models.is_friend).filter(models.is_friend.friend_id == friend.friend_id).delete()

        self.db.commit()

        return CommoneResponse(status = "success", message = "친구 삭제 성공")

    async def test_register(self, phone : str) -> token_response:
        existing_user = self.db.query(models.user_info).filter(models.user_info.phone == phone).first()

        if existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="이미 등록된 사용자입니다.")
        
        for _ in range(10):
            new_id = self.rng.generate_unique_random_number(100000, 999999)

        phone_num = self.format_phone_number(phone)

        new_user = models.user_info(
            user_id = new_id,
            phone = phone_num,
            create_date = self.today.strftime('%Y-%m-%d'),
            last_login_date = self.today.strftime('%Y-%m-%d'),
            refresh_token = self.jwt.create_refresh_token(phone, new_id)
        )

        return token_response(
            status = "success",
            message = "사용자 등록 성공",
            content = {
                "userId" : str(new_id),
                "accessToken" : self.jwt.create_access_token(phone, new_id),
                "refreshToken" : new_user.refresh_token
            }
        )

    
    def format_phone_number(self, phone_number : str) -> str:
        if len(phone_number) > 11:
            return phone_number
        else:
            formatted_number = phone_number[:3] + '-' + phone_number[3:7] + '-' + phone_number[7:]
            return formatted_number