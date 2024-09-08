from fastapi import status, Depends, UploadFile
from sqlalchemy.orm import Session
from sqlalchemy import and_, or_


from ..database import Database
from .. import models
from ..schemes import *
from ..random_generator import RandomNumberGenerator
from ..util import JWTService
from ..config import Config

from ..httpException import CustomException
from ..httpException import CustomException2

import boto3
import uuid

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
            existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == album.album_id).first()
    
            album_data = {
                "albumId": existing_album.album_id,
                "albumName": existing_album.album_name,
                "albumThumbnail": existing_album.album_thumbnail
            }
    
            if album.is_stared:
                # 즐겨찾기인 경우 리스트의 앞에 추가
                album_list_response.insert(0, album_data)
            else:
                # 즐겨찾기가 아닌 경우 리스트의 뒤에 추가
                album_list_response.append(album_data)
            
        return Album_list_response(status = "success", message = "앨범 리스트 조회 성공", content = album_list_response)

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
        directroy = f"https://%s.s3.amazonaws.com/albums/%s" % (
            Config.s3_bucket,
            new_key
            )
        new_album_member = models.album_member_info(
            album_id = new_key,
            user_id = user["key"]
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
                user_id = friend["friendId"]
            )
            self.db.add(new_album_member)

        new_album = models.album_info(
            album_id = new_key,
            create_user_id = user["key"],
            album_name = "기본 엘범",
            directory = directroy,
            album_thumbnail = f"https://%s.s3.amazonaws.com/albums/default.png" % (Config.s3_bucket) #나중에 바꿔라
        )

        self.db.add(new_album)
        self.db.commit()

        # 권한 관련 로직 추가 예정
        return Album_create_response(status = "success", message = "앨범 생성 성공", content = {"albumId" : str(new_key)})
    
    async def set_album_thumbnail(self, albumId : str, albumName : str, file : UploadFile, token : str) -> CommoneResponse:
        user = self.jwt.check_token_expired(token)

        if user is None:
            raise CustomException2(status_code=status.HTTP_401_UNAUTHORIZED, detail="토큰이 만료되었습니다.")
        
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == user["key"]).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        existing_album = self.db.query(models.album_info).filter(models.album_info.album_id == albumId).first()

        if existing_album is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 앨범입니다.")
        
        if file:
            thumbnail_url = f"https://%s.s3.amazonaws.com/albums/%s/%s" % (Config.s3_bucket, albumId, file.filename)
            s3.upload_fileobj(
                file.file,
                Config.s3_bucket,
                f"albums/%s/%s" % (albumId, file.filename)
            )
        else:
            thumbnail_url = f"https://%s.s3.amazonaws.com/albums/default.png" % (Config.s3_bucket)

        existing_album.album_thumbnail = thumbnail_url
        existing_album.album_name = albumName

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
            
            new_authority = models.album_authorization_info(
                album_id = request.albumId,
                user_id = friend["friendId"],
                post = friend["read"],
                delete = friend["delete"]
            )
            self.db.add(new_authority)

        self.db.commit()

        return CommoneResponse(status = "success", message = "앨범 권한 설정 성공")