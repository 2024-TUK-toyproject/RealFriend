from fastapi import HTTPException, status, Depends
from sqlalchemy.orm import Session


from ..database import Database
from .. import models
from ..schemes import *
from datetime import datetime
from pytz import timezone

class download_service:
    def __init__(self, db: Session = Depends(Database().get_session)):
        self.db = db
        self.today = datetime.now(timezone('Asia/Seoul'))

    async def get_last_call(self, user_id : str) -> Last_call_response:
        
        self.check_user_id(user_id)

        last_call = self.db.query(models.call_record_info).filter(models.call_record_info.user_id == user_id).order_by(models.call_record_info.date.desc()).first()
        if last_call is None:
            raise HTTPException(status_code=400, detail="통화 기록이 없습니다.")
        
        return Last_call_response(
            name = last_call.name,
            phone = last_call.phone,
            date = last_call.date,
            time = last_call.time,
            duration = last_call.duration,
            type = last_call.type
        )
    
    def check_user_id(self, user_id : str):
        user = self.db.query(models.user_info).filter(models.user_info.user_id == user_id).first()
        if user is None:
            raise HTTPException(status_code=400, detail="사용자 정보가 없습니다.")

