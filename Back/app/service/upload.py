from fastapi import HTTPException, status, Depends
from sqlalchemy.orm import Session


from ..database import Database
from .. import models
from ..schemes import *

from ..httpException import CustomException
from ..util import JWTService

from datetime import datetime, timedelta
from pytz import timezone


class upload_service:
    def __init__(self, db: Session = Depends(Database().get_session)):
        self.db = db
        self.today = datetime.now(timezone('Asia/Seoul'))
        self.jwt = JWTService()

    async def upload_phone_info(self, phone_list : Phone_request, token : str) -> CommoneResponse:
        
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰이 만료되었습니다.")

        self.check_user_id(user["key"])
        
        for phone in phone_list.content:
            phone_num = self.format_phone_number(phone.phone)
            new_phone = models.phone_info(
                user_id = user["key"],
                name = phone.name,
                phone = phone_num,
                create_date = self.today.strftime('%Y-%m-%d'),
                last_modified_date = self.today.strftime('%Y-%m-%d')
            )
            self.db.add(new_phone)
        self.db.commit()

        return CommoneResponse(status = "success", message = "연락처 동기화 성공")
    
    async def upload_call_record(self, call_record_list : Call_record_request, token : str) -> Call_record_response:  
        user = self.jwt.check_token_expired(token)
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="토큰이 만료되었습니다.")
        
        self.check_user_id(user["key"])

        for call_record in call_record_list.content:
            date1, time = self.divide_date(call_record.date)
            phone_num = self.format_phone_number(call_record.phone)
            new_call_record = models.call_record_info(
                user_id = user["key"],
                name = call_record.name,
                phone = phone_num,
                date = date1,
                time = time, 
                #duration = self.preprocess_duration(call_record.duration),
                duration = call_record.duration,
                type = call_record.type
            )
            self.db.add(new_call_record)
        
        self.db.commit()

        #최대 3개월 이내의 통화내역가져온다
        three_month_ago = self.today - timedelta(days = 90)
        three_month_ago = three_month_ago.strftime('%Y-%m-%d')
        call_record = self.db.query(models.call_record_info).filter(models.call_record_info.user_id == user["key"]).filter(models.call_record_info.date >= three_month_ago).all()
    
        #같은 번호로 통화한 횟수와 통화시간을 구한다
        phone_dict = {}
        for record in call_record:
            if record.phone in phone_dict:
                phone_dict[record.phone]['count'] += 1
                phone_dict[record.phone]['duration'] += record.duration
            else:
                phone_dict[record.phone] = {'name' : record.name, 'count' : 1, 'duration' : record.duration}
        
        #통화횟수가 많은 순으로 정렬
        phone_dict = sorted(phone_dict.items(), key = lambda x : x[1]['count'], reverse = True)

        content = []

        for phone in phone_dict:
            content.append(Most_friendly_list(
                name = phone[1]['name'],
                phone = phone[0],
                duration = phone[1]['duration'],
                count = phone[1]['count']
            ))

        
        return Call_record_response(status = "success", message = "통화 기록 동기화 성공", content = content)
    
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
    
    def preprocess_duration(self, duration):
        #00:01:30
        #시간 분 초 분할
        duration = duration.split(':')
        hour = duration[0]
        minute = duration[1]
        second = duration[2]
        return int(hour)*3600 + int(minute)*60 + int(second)
        
    def check_user_id(self, userId):
        user = self.db.query(models.user_info).filter(models.user_info.user_id == userId).first()
        if user is None:
            raise CustomException(status_code=status.HTTP_400_BAD_REQUEST, detail="사용자 정보가 없습니다.")
        return user
    
    def format_phone_number(self, phone_number : str) -> str:
        if len(phone_number) > 11:
            return phone_number
        else:
            formatted_number = phone_number[:3] + '-' + phone_number[3:7] + '-' + phone_number[7:]
            return formatted_number