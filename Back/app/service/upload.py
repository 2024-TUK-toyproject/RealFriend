from fastapi import HTTPException, status, Depends
from sqlalchemy.orm import Session
from ..database import Database
from .. import models
from ..schemes import *


class upload_service:
    def __init__(self, db: Session = Depends(Database().get_session)):
        self.db = db

    async def upload_phone_info(phone_list):
