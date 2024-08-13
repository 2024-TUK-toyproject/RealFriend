from fastapi import HTTPException, status, Depends, UploadFile
from sqlalchemy.orm import Session
from sqlalchemy import and_, or_


from ..database import Database
from .. import models
from ..schemes import *
from ..random_generator import RandomNumberGenerator
from ..config import Config
from ..util import JWTService
from ..httpException import CustomException

from datetime import datetime

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
        '''exiting_user = self.db.query(models.user_info).filter(models.user_info.phone == user_info.phone).first()
        if exiting_user is not None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="이미 등록된 사용자입니다.")'''
        
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

        user = self.db.query(models.temp_user_info).filter(models.temp_user_info.phone == request.phone, models.temp_user_info.create_date == self.today.strftime('%Y-%m-%d')).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.phone == request.phone).first()
        if existing_user is not None:
            existing_user.last_login_date = self.today.strftime('%Y-%m-%d')
            self.db.query(models.temp_user_info).filter(models.temp_user_info.phone == request.phone).delete()
            self.db.commit()
            
            return Certificate_response(status = "success", message = "인증 성공", content = {"userId" : str(existing_user.user_id), "isExist" : str(True)})
        
        for _ in range(10):
            new_id = self.rng.generate_unique_random_number(100000, 999999)
        
        phone_num = self.format_phone_number(request.phone)

        new_user = models.user_info(
            user_id = new_id,
            phone = phone_num,
            create_date = user.create_date,
            last_login_date = self.today.strftime('%Y-%m-%d')
        )
        self.db.add(new_user)
        self.db.commit()

        # temp_user_info 삭제
        self.db.query(models.temp_user_info).filter(models.temp_user_info.phone == request.phone).delete()
        self.db.commit()
        return Certificate_response(status = "success", message = "인증 성공", content = {"userId" : str(new_id), "isExist" : str(False)})
    
    async def set_profile(self, userId : str, name : str, file : UploadFile) -> CommoneResponse:
        user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
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
        user.name = name

        self.db.commit()

        return CommoneResponse(status = "success", message = "프로필 사진 등록 성공")
    
    async def get_profile(self, userId : str) -> Get_profile_response:
        user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        return Get_profile_response(
            name = user.name,
            phone = user.phone,
            profile_image = user.profile_image
        )
    
    async def modify_profile(self, userId : str, file : UploadFile) -> Profile_modify_response:
        user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
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

        self.db.commit()

        return Profile_modify_response(status = "success", message = "프로필 사진 수정 성공", content = {"profileImage" : user.profile_image})
    
    async def add_friend(self, request : Add_friend_request) -> CommoneResponse:
        user = self.db.query(models.user_info).filter(models.user_info.user_id == request.userId).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        friend = self.db.query(models.user_info).filter(models.user_info.user_id == request.friendId).first()
        if friend is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="친구 정보가 없습니다.")
        
        is_friend = self.db.query(models.is_friend).filter(
            or_(
                and_(
                    models.is_friend.user_id == request.userId,
                    models.is_friend.from_user_id == request.friendId
                ),
                and_(
                    models.is_friend.user_id == request.friendId,
                    models.is_friend.from_user_id == request.userId
                )
            )
        ).first()
        if is_friend.is_friend is True:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="이미 친구입니다.")
        
        for _ in range(10):
            new_id = self.rng.generate_unique_random_number(100000, 999999)

        new_friend = models.is_friend(
            friend_id = new_id,
            user_id = request.userId,
            from_user_id = request.friendId,
            is_friend = False,
            create_date = self.today.strftime('%Y-%m-%d'),
            last_modified_date = self.today.strftime('%Y-%m-%d')
        )
        self.db.add(new_friend)
        self.db.commit()


        return CommoneResponse(status = "success", message = "친구 추가 성공")


    # 테스트 아직 안함/통화시간, 통화횟수 추가해야함
    async def get_friend_list(self, userId : str) -> Friend_list_response:
        user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        friends = self.db.query(models.is_friend).filter(
            and_(
                or_(
                    models.is_friend.user_id == userId,
                    models.is_friend.from_user_id == userId
                ),
                models.is_friend.is_friend == True  
            )
        ).all()
        friend_list = []
        for friend in friends:
            if friend.user_id == userId:
                friend = self.db.query(models.user_info).filter(models.user_info.user_id == friend.from_user_id).first()
            else:
                friend = self.db.query(models.user_info).filter(models.user_info.user_id == friend.user_id).first()
            friend_list.append({
                "userId" : friend.user_id,
                "name" : friend.name,
                "phone" : friend.phone,
                "profileImage" : friend.profile_image
            })
        
        return Friend_list_response(status = "success", message = "친구 리스트 조회 성공", content = friend_list)
    
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
            access_token = self.jwt.create_access_token(phone,new_id),
            refresh_token = self.jwt.create_refresh_token(phone, new_id)
        )

        return token_response(
            status = "success",
            message = "사용자 등록 성공",
            content = {
                "userId" : str(new_id),
                "accessToken" : new_user.access_token,
                "refreshToken" : new_user.refresh_token
            }
        )

    
    def format_phone_number(self, phone_number : str) -> str:
        if len(phone_number) > 11:
            return phone_number
        else:
            formatted_number = phone_number[:3] + '-' + phone_number[3:7] + '-' + phone_number[7:]
            return formatted_number