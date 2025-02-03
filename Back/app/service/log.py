from fastapi import HTTPException, status, Depends, UploadFile
from sqlalchemy.orm import Session

from ..database import Database
from .. import models

class LogService:
    def __init__(self, db: Session = Depends(Database().get_session)):
        self.db = db

    async def write_log(self, userId : str, message : str, date : str, time : str):
        new_log = models.log_info(
            user_id = userId,
            message = message, 
            date = date,
            time = time
        )
        self.db.add(new_log)
        self.db.commit()