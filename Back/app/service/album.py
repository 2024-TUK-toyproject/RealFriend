from fastapi import status, Depends, UploadFile
from sqlalchemy.orm import Session
from sqlalchemy import and_, or_


from ..database import Database
from .. import models
from ..schemes import *
from ..random_generator import RandomNumberGenerator
from ..util import JWTService
from ..config import Config
from ..fcm_service import send_push_notification

from ..httpException import CustomException
from ..httpException import CustomException2

from datetime import datetime

import boto3
import os

s3 = boto3.client(
    "s3",
    aws_access_key_id=Config.s3_access_key,
    aws_secret_access_key=Config.s3_secret_key
)

class Album_service:
    def __init__(self, db: Session = Depends(Database().get_session)):
        self.db = db
        self.rng = RandomNumberGenerator()
        self.jwt = JWTService()

    async def get_album_list(self, token : str) -> Album_list_response:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        album_list = self.db.query(models.album_member_info).filter(models.album_member_info.user_id == user["key"]).all()
        
        album_list_response = []

        #즐겨찾기 중이면 리스트의 앞에 넣어주기
        for album in album_list:
            existing_album = self.db.query(models.album_info).filter(
                and_(
                    models.album_info.album_id == album.album_id,
                    models.album_info.create_user_id == user["key"]
                )).first()
            #엘범 사용자 수
            album_member_count = self.db.query(models.album_member_info).filter(models.album_member_info.album_id == album.album_id).count()

            #엘범 내 사진 수
            #count, total_size = self.count_album_picture(existing_album.directory)
            count = self.db.query(models.picture_info).filter(and_(models.picture_info.album_id == album.album_id, models.picture_info.is_intrash == False)).count()
            album_data = {
                "albumId": existing_album.album_id,
                "albumName": existing_album.album_name,
                "albumThumbnail": existing_album.album_thumbnail,
                "albumMemberCount": album_member_count,
                "albumPictureCount": count,
                "isStared": album.is_stared
            }
    
            if album.is_stared:
                # 즐겨찾기인 경우 리스트의 앞에 추가
                album_list_response.insert(0, album_data)
            else:
                # 즐겨찾기가 아닌 경우 리스트의 뒤에 추가
                album_list_response.append(album_data)
            
        return Album_list_response(status = "success", message = "앨범 리스트 조회 성공", content = album_list_response)

    async def get_stared_album_list(self, token :str) -> Album_list_response:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        album_list = self.db.query(models.album_member_info).filter(
            and_(
                models.album_member_info.user_id == user["key"],
                models.album_member_info.is_stared == True
            )
        ).all()
        
        album_list_response = []

        for album in album_list:
            existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == album.album_id).first()

            #엘범 사용자 수
            album_member_count = self.db.query(models.album_member_info).filter(models.album_member_info.album_id == album.album_id).count()

            #엘범 내 사진 수
            count, size = self.count_album_picture(existing_album.directory)

            album_data = {
                "albumId": existing_album.album_id,
                "albumName": existing_album.album_name,
                "albumThumbnail": existing_album.album_thumbnail,
                "albumMemberCount": album_member_count,
                "albumPictureCount": count,
                "isStared": album.is_stared
            }
    
            album_list_response.append(album_data)
            
        return Album_list_response(status = "success", message = "즐겨찾기 앨범 리스트 조회 성공", content = album_list_response)

    async def create_album(self, request : Album_create_request, token : str) -> Album_create_response:
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")

        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        for friend in request.content:
            existing_friend = self.db.query(models.user_info).filter(models.user_info.user_id == friend["friendId"]).first()
            if existing_friend is None:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 친구입니다.")
        
        new_key = self.rng.generate_unique_random_number(100000, 999999)
        directroy = f"albums/%s" % (
            new_key
            )
        new_album_member = models.album_member_info(
            album_id = new_key,
            user_id = user["key"],
            is_stared = False,
            autherization = 1       # 1 : founder(추가 삭제, 사진 복구, 엘범 용량 확장, 권한 수정) 2 : manager(사진 추가 삭제) 3 : member(사진 추가 삭제 - 본인것만)
        )
        self.db.add(new_album_member)
        for friend in request.content:
            existing_friend = self.db.query(models.is_friend).filter(
                or_(
                    and_(
                        models.is_friend.from_user_id == user["key"],
                        models.is_friend.to_user_id == friend["friendId"]
                    ),
                    and_(
                        models.is_friend.from_user_id == friend["friendId"],
                        models.is_friend.to_user_id == user["key"]
                    )
                )
            ).first()

            if existing_friend.is_friend is False:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail=f"%s와 친구가 아닙니다." % (friend["friendId"]))
            
            existing_friend.shared_album_id = new_key

            new_album_member = models.album_member_info(
                album_id = new_key,
                user_id = friend["friendId"],
                is_stared = False,
                autherization = 3
            )
            self.db.add(new_album_member)

        new_album = models.album_info(
            album_id = new_key,
            create_user_id = user["key"],
            album_name = "기본 엘범",
            create_date = datetime.now().strftime("%Y-%m-%d"),
            directory = directroy,
            album_thumbnail = f"https://%s.s3.amazonaws.com/albums/default.png" % (Config.s3_bucket), #나중에 바꿔라
            total_usage = 10240000.0, #10GB
            total_member = 10
        )

        self.db.add(new_album)
        self.db.commit()

        # 권한 관련 로직 추가 예정
        return Album_create_response(status = "success", message = "앨범 생성 성공", content = {"albumId" : str(new_key)})
    
    async def set_album_thumbnail(self, request : Album_thumbnail_request, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)

        if user is None:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == request.albumId).first()

        if existing_album is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        existing_album.album_name = request.albumName
        existing_album.album_thumbnail = request.albumThumbnail

        self.db.commit()

        return CommoneResponse(status = "success", message = "앨범 썸네일 설정 성공")

    async def star_album(self, albumId : str, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_member_info).filter(
            and_(
                models.album_member_info.album_id == albumId,
                models.album_member_info.user_id == user["key"]
            )
        ).first()
        if not existing_album:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        if existing_album.is_stared:
            existing_album.is_stared = False
            message = "앨범 즐겨찾기 해제 성공"
        else:
            existing_album.is_stared = True
            message = "앨범 즐겨찾기 설정 성공"
        
        self.db.commit()

        return CommoneResponse(status = "success", message = message)

    async def post_album(self, file : List[UploadFile], album_id : str, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        authority = self.db.query(models.album_member_info).filter(
            and_(
                models.album_member_info.album_id == album_id,
                models.album_member_info.user_id == user["key"]
            )
        ).first()

        if not authority:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
      

        for f in file:
            f.file.seek(0, os.SEEK_END)
            file_size = f.file.tell()
            f.file.seek(0)                  #f.file.tell()은 파일이 열려있을 때 파일의 용량을 가져옴, 비동기 처리시에는 메모리상에 파일 정보가 남아있을때 해당 명령어를 실행하여 정보를 가져올 수 있음, 따라서 파일을 읽은 후에는 파일을 다시 처음으로 돌려놔야함

            name = f.filename.split(":")[0] + '.' + f.filename.split(":")[1].split(".")[1]

            s3.upload_fileobj(
                f.file,
                Config.s3_bucket,
                f"albums/%s/%s" % (album_id, name)
            )
            new_key = self.rng.generate_unique_random_number(10000000, 99999999)
            # f.filename = asdasd/yymmddhhmmss.jpg
            #print(f.filename)
            date = f.filename.split(":")[1].split(".")[0]
            name = f.filename.split(":")[0] + '.' + f.filename.split(":")[1].split(".")[1]
            _date = datetime.strptime(date, "%y%m%d%H%M%S").strftime("%Y-%m-%d")
            _time = datetime.strptime(date, "%y%m%d%H%M%S").strftime("%H:%M:%S")
            new_album_picture = models.picture_info(
                album_id = album_id,
                user_id = user["key"],
                picture_id = new_key,
                name = name,
                upload_date = datetime.now().strftime("%Y-%m-%d"),
                upload_time = datetime.now().strftime("%H:%M:%S"),
                usage = file_size,
                time = _time,
                date = _date,
                is_intrash = False
            )
            self.db.add(new_album_picture)
        
        self.db.commit()
        
        #엛범 맴버들에게 푸시알림 보내기
        friend_id = self.db.query(models.album_member_info).filter(models.album_member_info.album_id == album_id).all()
        for friend in friend_id:
            friend_fcm_token = self.db.query(models.fcm_token_info).filter(models.fcm_token_info.user_id == friend.user_id).first()
            if friend_fcm_token:
                data = {
                "title" : "CONNEX",
                "body" : "%s님이 새로운 사진을 업로드 했어요."%{existing_user.name},
                }
                await send_push_notification(friend_fcm_token.fcm_token, data)
            else:
                pass

        return CommoneResponse(status = "success", message = "앨범 업로드 성공")

    async def create_album_authority(self, request : Album_authority_request, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)

        if user is None:
            raise CustomException(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == request.albumId).first()
        if existing_album is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        for friend in request.content:
            existing_friend = self.db.query(models.user_info).filter(models.user_info.user_id == friend["friendId"]).first()
            if existing_friend is None:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 친구입니다.")
            
            existing_album_member = self.db.query(models.album_member_info).filter(
                and_(
                    models.album_member_info.album_id == request.albumId,
                    models.album_member_info.user_id == friend["friendId"]
                )
            ).first()
            if existing_album_member is None:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="앨범 멤버가 아닙니다.")
            
            if friend["authority"] == "founder":
                existing_album_member.autherization = 1
            elif friend["authority"] == "manager":
                existing_album_member.autherization = 2
            elif friend["authority"] == "member":
                existing_album_member.autherization = 3
            else:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="권한 설정이 잘못되었습니다.")
            
        self.db.commit()

        return CommoneResponse(status = "success", message = "앨범 권한 설정 성공")
    
    async def get_album_info(self, album_id : str, token : str) -> Album_info_response:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == album_id).first()
        if not existing_album:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        album_member = self.db.query(models.album_member_info).filter(models.album_member_info.album_id == album_id)
        album_member_info = []
        for member in album_member:
            member_info = self.db.query(models.user_info).filter(models.user_info.user_id == member.user_id).first()
            if member.autherization == 1:
                authority = "founder"
            elif member.autherization == 2:
                authority = "manager"
            elif member.autherization == 3:
                authority = "member"

            album_usage = 0
            album_picture = self.db.query(models.picture_info).filter(
                and_(
                    models.picture_info.album_id == album_id,
                    models.picture_info.user_id == member.user_id
                )
            ).all()

            for picture in album_picture:
                album_usage += picture.usage
            if member.user_id == user["key"]:
                current_user_size = album_usage
            else:
                pass

            album_member_info.append({
                "userId": member_info.user_id,
                "userName": member_info.name,
                "userProfile": member_info.profile_image,
                "pictureCount": self.db.query(models.picture_info).filter(and_(models.picture_info.user_id == member.user_id, models.picture_info.is_intrash == False)).count(),
                "usage": round(album_usage/1024 , 2),
                "authority": authority
            })
        
        album_picture_count = self.db.query(models.picture_info).filter(and_(models.picture_info.album_id == album_id, models.picture_info.is_intrash == False)).count()
        album_total_size = self.db.query(models.picture_info).filter(models.picture_info.album_id == album_id).all()

        total_size = 0
        for picture in album_total_size:
            total_size += picture.usage
        
        album_picture_count_from_current_user = self.db.query(models.picture_info).filter(
            and_(
                models.picture_info.album_id == album_id,
                models.picture_info.user_id == user["key"],
                models.picture_info.is_intrash == False
            )
        ).count()

        album_info = {
            "albumName": existing_album.album_name,
            "albumThumbnail": existing_album.album_thumbnail,
            "albumFounder" : existing_user.name,
            "albumFoundate" : existing_album.create_date,
            "albumMemberMax" : existing_album.total_member,
            "albumMemberInfo" : album_member_info,
            "albumPictureCount": album_picture_count,
            "albumPictureCountFromCurrentUser": album_picture_count_from_current_user,
            "albumPictureUsageFromCurrentUser": round(current_user_size/1024, 2),
            "trashCount" : self.db.query(models.picture_info).filter(models.picture_info.is_intrash == True).count(), 
            "currentUsage" : round(total_size/1024, 2),
            "totalUsage" : existing_album.total_usage
        }

        return Album_info_response(status = "success", message = "앨범 정보 조회 성공", content = album_info)

    async def get_album_member_info(self, album_id : str, token : str) -> Album_member_info_response:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == album_id).first()
        if not existing_album:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        album_member = self.db.query(models.album_member_info).filter(models.album_member_info.album_id == album_id)
        album_member_info = []
        for member in album_member:
            member_info = self.db.query(models.user_info).filter(models.user_info.user_id == member.user_id).first()
            if member.autherization == 1:
                authority = "founder"
            elif member.autherization == 2:
                authority = "manager"
            elif member.autherization == 3:
                authority = "member"

            album_member_info.append({
                "userId": member_info.user_id,
                "userName": member_info.name,
                "userProfile": member_info.profile_image,
                "pictureCount": self.db.query(models.picture_info).filter(models.picture_info.user_id == member.user_id).count(),
                "authority": authority
            })

        return Album_member_info_response(status = "success", message = "앨범 멤버 정보 조회 성공", content = album_member_info)

    async def get_photo_from_album(self, album_id : str, token : str) -> Album_picture_response:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == album_id).first()
        if not existing_album:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        album_member = self.db.query(models.album_member_info).filter(
            and_(
                models.album_member_info.album_id == album_id,
                models.album_member_info.user_id == user["key"]
            )
        ).first()
        if not album_member:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="앨범 멤버가 아닙니다.")
        
        response = s3.list_objects_v2(
            Bucket=Config.s3_bucket,
            Prefix=existing_album.directory
        )

        photo_list = []
        for content in response.get("Contents", []):
            pic_info = self.db.query(models.picture_info).filter(models.picture_info.name == content["Key"].split("/")[-1]).first() # albums/685764/스크린샷 2024-10-16 오후 3.54.18.png
            is_stared = self.db.query(models.star_photo_info).filter(
                and_(
                    models.star_photo_info.picture_id == pic_info.picture_id,
                    models.star_photo_info.star_user_id == user["key"]
                )
            ).first()
            if is_stared:
                star = True
            else:
                star = False
            photo_list.append({
                "photoId": pic_info.picture_id,
                "photoUrl": f"https://%s.s3.amazonaws.com/%s" % (Config.s3_bucket, content["Key"]),
                "star" : star
            })
        
        return Album_picture_response(status = "success", message = "앨범 사진 조회 성공", content = photo_list)

    async def get_photo_info(self, photo_id : str, token : str) -> Album_picture_info_response:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_photo = self.db.query(models.picture_info).filter(models.picture_info.picture_id == photo_id).first()
        if not existing_photo:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사진입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == existing_photo.album_id).first()
        if not existing_album:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        album_member = self.db.query(models.album_member_info).filter(
            and_(
                models.album_member_info.album_id == existing_photo.album_id,
                models.album_member_info.user_id == user["key"]
            )
        ).first()
        if not album_member:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="앨범 멤버가 아닙니다.")
        
        photo_info = self.db.query(models.picture_info).filter(models.picture_info.picture_id == photo_id).first()
        if not photo_info:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사진입니다.")
        
        is_stared = self.db.query(models.star_photo_info).filter(
            and_(
                models.star_photo_info.picture_id == photo_id,
                models.star_photo_info.star_user_id == user["key"]
            )
        ).first()
        if is_stared:
            star = True
        else:
            star = False

        upload_user = self.db.query(models.user_info).filter(models.user_info.user_id == photo_info.user_id).first()
        photo_data = {
            "name": photo_info.name,
            "date": photo_info.date,
            "time": photo_info.time,
            "usage": round(photo_info.usage/1024,2),
            "uploadUser": upload_user.name,
            "uploadUserProfile" : upload_user.profile_image,
            "uploadDate": photo_info.upload_date,
            "uploadTime": photo_info.upload_time,
            "isStared" : star
        }

        return Album_picture_info_response(status = "success", message = "사진 정보 조회 성공", content = photo_data)

    async def delete_photo_from_album(self, request : photo_delete_request, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)
        
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        delete = False
        for photo in request.content:
            photoId = photo["photoId"]
            existing_photo = self.db.query(models.picture_info).filter(models.picture_info.picture_id == photoId).first()
            if not existing_photo:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사진입니다.")
            
            existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == existing_photo.album_id).first()
            if not existing_album:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
            
            if delete is False:
                album_member = self.db.query(models.album_member_info).filter(
                    and_(
                        models.album_member_info.album_id == existing_photo.album_id,
                        models.album_member_info.user_id == user["key"]
                    )
                ).first()
                if not album_member:
                    raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="앨범 멤버가 아닙니다.")
            
                if album_member.autherization == 3:
                    if existing_photo.user_id != user["key"]:
                        raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="삭제 권한이 없습니다.")
                else:
                    delete = True
            s3.copy_object(
            Bucket=Config.s3_bucket,
            CopySource={'Bucket': Config.s3_bucket, 'Key': f"albums/{existing_photo.album_id}/{existing_photo.name}"},
            Key=f"albums/{existing_photo.album_id}/trash/{existing_photo.name}"
            )

            s3.delete_object(
                Bucket=Config.s3_bucket,
                Key=f"albums/{existing_photo.album_id}/{existing_photo.name}"
            )
            existing_photo.is_intrash = True
            existing_photo.in_trash_date = datetime.now().strftime("%Y-%m-%d")

        self.db.commit()

        return CommoneResponse(status = "success", message = "사진 휴지통으로 이동 성공")

    async def post_album_reply(self, request : Album_reply_request, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_photo = self.db.query(models.picture_info).filter(models.picture_info.picture_id == request.photoId).first()
        if not existing_photo:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사진입니다.")
        
        album_member = self.db.query(models.album_member_info).filter(
            and_(
                models.album_member_info.album_id == existing_photo.album_id,
                models.album_member_info.user_id == user["key"]
            )
        ).first()
        if not album_member:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="앨범 멤버가 아닙니다.")

        #댓글 권한 논의 필요
        new_key = self.rng.generate_unique_random_number(100000000, 999999999)
        new_album_reply = models.album_reply_info(
            reply_key = new_key,
            picture_id = request.photoId,
            user_id = user["key"],
            date = datetime.now().strftime("%Y-%m-%d"),
            time = datetime.now().strftime("%H:%M:%S"),
            content = request.content
        )
        self.db.add(new_album_reply)
        self.db.commit()

        return CommoneResponse(status = "success", message = "앨범 댓글 작성 성공")

    async def get_album_reply(self, photoId : str, token : str) -> Album_reply_response:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_photo = self.db.query(models.picture_info).filter(models.picture_info.picture_id == photoId).first()
        if not existing_photo:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사진입니다.")
        
        album_member = self.db.query(models.album_member_info).filter(
            and_(
                models.album_member_info.album_id == existing_photo.album_id,
                models.album_member_info.user_id == user["key"]
            )
        ).first()
        if not album_member:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="앨범 멤버가 아닙니다.")
        
        album_reply = self.db.query(models.album_reply_info).filter(models.album_reply_info.picture_id == photoId).all()
        reply_list = []
        for reply in album_reply:
            reply_user = self.db.query(models.user_info).filter(models.user_info.user_id == reply.user_id).first()
            reply_list.append({
                "replyKey": reply.reply_key,
                "userName": reply_user.name,
                "userProfile": reply_user.profile_image,
                "date": reply.date,
                "time": reply.time,
                "message": reply.content
            })

        return Album_reply_response(status = "success", message = "앨범 댓글 조회 성공", content = reply_list)

    async def star_photo(self, photoId : str, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_photo = self.db.query(models.picture_info).filter(models.picture_info.picture_id == photoId).first()
        if not existing_photo:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사진입니다.")
        
        album_member = self.db.query(models.album_member_info).filter(
            and_(
                models.album_member_info.album_id == existing_photo.album_id,
                models.album_member_info.user_id == user["key"]
            )
        ).first()
        if not album_member:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="앨범 멤버가 아닙니다.")
        
        already_star = self.db.query(models.star_photo_info).filter(
            and_(
                models.star_photo_info.picture_id == photoId,
                models.star_photo_info.star_user_id == user["key"]
            )
        ).first()

        if already_star:
            self.db.delete(already_star)
            message = "사진 즐겨찾기 해제 성공"
        else:
            new_star = models.star_photo_info(
                picture_id = photoId,
                upload_user_id = existing_photo.user_id,
                star_user_id = user["key"],
                album_id = existing_photo.album_id
            )
            self.db.add(new_star)
            message = "사진 즐겨찾기 설정 성공"

        self.db.commit()

        return CommoneResponse(status = "success", message = message)

    async def invite_album_member(self, request : Album_invite_request, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == request.albumId).first()
        if not existing_album:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        for friend in request.content:
            existing_friend = self.db.query(models.user_info).filter(models.user_info.user_id == friend["friendId"]).first()
            if not existing_friend:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 친구입니다.")
            
            existing_album_member = self.db.query(models.album_member_info).filter(
                and_(
                    models.album_member_info.album_id == request.albumId,
                    models.album_member_info.user_id == friend["friendId"]
                )
            ).first()
            if existing_album_member:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="이미 초대된 멤버입니다.")
            
            existing_friend = self.db.query(models.is_friend).filter(
                or_(
                    and_(
                        models.is_friend.from_user_id == user["key"],
                        models.is_friend.to_user_id == friend["friendId"]
                    ),
                    and_(
                        models.is_friend.from_user_id == friend["friendId"],
                        models.is_friend.to_user_id == user["key"]
                    )
                )
            ).first()
            if not existing_friend:
                raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="친구가 아닙니다.")
            
            new_album_member = models.album_member_info(
                album_id = request.albumId,
                user_id = friend["friendId"],
                is_stared = False,
                autherization = 3
            )
            self.db.add(new_album_member)

        self.db.commit()

        return CommoneResponse(status = "success", message = "앨범 멤버 초대 성공")
    
    async def recommend_invite_album_member(self, album_id : str, token : str) -> Album_recommend_invite_response:
        user = self.jwt.check_token_expired(token)
        if not user:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if not existing_user:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == album_id).first()
        if not existing_album:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        #친구중 초대되지 않은 친구들 추천
        friend_list = self.db.query(models.is_friend).filter(
            or_(
                models.is_friend.from_user_id == user["key"],
                models.is_friend.to_user_id == user["key"]
            )
        ).all()
        invite_list = self.db.query(models.album_member_info).filter(models.album_member_info.album_id == album_id).all()
        recommend_list = []
        for friend in friend_list:
            if friend.from_user_id == user["key"]:
                friend_id = friend.to_user_id
            else:
                friend_id = friend.from_user_id
            
            is_invite = False
            for invite in invite_list:
                if invite.user_id == friend_id:
                    is_invite = True
                    break
            if is_invite is False:
                friend_info = self.db.query(models.user_info).filter(models.user_info.user_id == friend_id).first()
                recommend_list.append({
                    "userId": friend_info.user_id,
                    "userName": friend_info.name,
                    "userProfile": friend_info.profile_image
                })

        return Album_recommend_invite_response(status = "success", message = "앨범 멤버 추천 성공", content = recommend_list)

    #페이징 사용 -> 1000장 이상가져올 수 있음
    def count_album_picture(self, album_directory : str):
        count = 0
        total_size = 0

        valid_extensions = {'.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tiff', ".heic", ".heif", ".mp4"}
        continuation_token = None

        while True:
            if continuation_token:
                response = s3.list_objects_v2(
                    Bucket=Config.s3_bucket,
                    Prefix=album_directory,
                    ContinuationToken=continuation_token
                )
            else:
                response = s3.list_objects_v2(
                    Bucket=Config.s3_bucket,
                    Prefix=album_directory
                )
            contents = response.get("Contents", [])
            for content in contents:
                file_key = content["Key"]
                # 확장자 확인
                if any(file_key.lower().endswith(ext) for ext in valid_extensions):
                    count += 1
                    total_size += content["Size"]

            if response.get("NextContinuationToken"):
                continuation_token = response["NextContinuationToken"]
            else:
                break
        
        return count, total_size