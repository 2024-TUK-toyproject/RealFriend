from fastapi import HTTPException, status, Depends, UploadFile
from sqlalchemy.orm import Session


from ..database import Database
from .. import models
from ..schemes import *
from ..random_generator import RandomNumberGenerator
from ..config import Config

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

    async def register_user(self, user_info : User_info_request) -> CommoneResponse:
        exiting_user = self.db.query(models.user_info).filter(models.user_info.phone == user_info.phone).first()
        if exiting_user is not None:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="이미 등록된 사용자입니다.")

        for _ in range(10):
            new_id = self.rng.generate_unique_random_number(100000, 999999)

        new_user = models.user_info(
            user_id = new_id,
            name = user_info.name,
            phone = user_info.phone,
            create_date = self.today.strftime('%Y-%m-%d'),
            last_modified_date = self.today.strftime('%Y-%m-%d')
        )
        self.db.add(new_user)
        self.db.commit()

        return CommoneResponse(status = "success", message = "사용자 등록 성공")
    
    async def set_profile(self, userId : str, file : UploadFile) -> CommoneResponse:
        user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if user is None:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
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

        return CommoneResponse(status = "success", message = "프로필 사진 등록 성공")
    
    async def get_profile(self, userId : str) -> Profile_response:
        user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if user is None:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        
        return Profile_response(
            name = user.name,
            phone = user.phone,
            profile_image = user.profile_image
        )