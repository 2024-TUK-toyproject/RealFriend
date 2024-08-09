from fastapi import status, Depends, UploadFile
from sqlalchemy.orm import Session


from ..database import Database
from .. import models
from ..schemes import *
from ..random_generator import RandomNumberGenerator
from ..config import Config

from ..httpException import CustomException


class Album_service:
    def __init__(self, db: Session = Depends(Database().get_session)):
        self.db = db
        self.rng = RandomNumberGenerator()

    async def get_album_list(self, userId : str) -> Album_list_response:
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        album_list1 = self.db.query(models.album_info).filter(models.album_info.create_user_id == userId).all()
        album_list2 = self.db.query(models.album_info).filter(models.album_info.with_whom == userId).all()

        album_list = album_list1 + album_list2

        album_list_response = []
        for album in album_list:
            album_list_response.append({"albumId" : album.album_id, "albumName" : album.album_name})

        return Album_list_response(status = "success", message = "앨범 리스트 조회 성공", content = album_list_response)

    async def create_album(self, request : Album_create_request) -> Album_create_response:
        existing_user = self.db.query(models.user_info).filter(models.user_info.user_id == request.userId).first()
        if existing_user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        existing_user2 = self.db.query(models.user_info).filter(models.user_info.user_id == request.withWhom).first()
        if existing_user2 is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="존재하지 않는 사용자입니다.")
        
        valified_user1 = self.db.query(models.is_friend).filter(models.is_friend.from_user_id == request.userId, models.is_friend.to_user_id == request.withWhom).first()
        valified_user2 = self.db.query(models.is_friend).filter(models.is_friend.from_user_id == request.withWhom, models.is_friend.to_user_id == request.userId).first()

        '''existing_album = self.db.query(models.album_info).filter(models.album_info.create_user_id == request.userId, models.album_info.with_whom == request.withWhom).first()
        if existing_album is not None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="이미 존재하는 앨범입니다.")'''
        
        if valified_user1.is_friend == False or valified_user2.is_friend == False:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="친구가 아닙니다.")
        
        for _ in range(10):
            new_key = self.rng.generate_unique_random_number(100000, 999999)

        new_album = models.album_info(
            album_id = new_key,
            album_name = request.albumName,
            create_user_id = request.userId,
            with_whom = request.withWhom,
            directory = f"sharedAlbum/{new_key}"
        )

        self.db.add(new_album)
        self.db.commit()

        return Album_create_response(status = "success", message = "앨범 생성 성공", content = {"albumId" : new_key, "albumName" : request.albumName})