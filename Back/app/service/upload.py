from fastapi import HTTPException, status, Depends
from sqlalchemy.orm import Session


from ..database import Database
from .. import models
from ..schemes import *
from datetime import datetime
from pytz import timezone


class upload_service:
    def __init__(self, db: Session = Depends(Database().get_session)):
        self.db = db
        self.today = datetime.now(timezone('Asia/Seoul'))

    async def upload_phone_info(self, phone_list : Phone_request) -> CommoneResponse:
        for phone in phone_list.content:
            new_phone = models.phone_info(
                user_id = phone_list.userId,
                name = phone.name,
                phone = phone.phone,
                create_date = self.today.strftime('%Y-%m-%d'),
                last_modified_date = self.today.strftime('%Y-%m-%d')
            )
            self.db.add(new_phone)
        self.db.commit()

        return CommoneResponse(status = "success", message = "연락처 동기화 성공")
    
    async def upload_call_record(self, call_record_list : Call_record_request) -> CommoneResponse:

        for call_record in call_record_list.content:
            date1, time = self.divide_date(call_record.date)
            new_call_record = models.call_record_info(
                user_id = call_record_list.userId,
                name = call_record.name,
                phone = call_record.phone,
                date = date1,
                time = time, 
                duration = call_record.duration,
                type = call_record.type
            )
            self.db.add(new_call_record)
        
        self.db.commit()

        return CommoneResponse(status = "success", message = "통화 기록 동기화 성공")
    
    def divide_date(self, date):
        #2024.11.20.14.20.01
        #날짜와 시간 분할
        date = date.split('.')
        year = date[0]
        month = date[1]
        day = date[2]
        hour = date[3]
        minute = date[4]
        second = date[5]
        date1 = f"{year}-{month}-{day}"
        time = f"{hour}:{minute}:{second}"
        return date1, time