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

        #오늘 날짜 전체 통화 기록을 가져온다
        last_call = self.db.query(models.call_record_info).filter(models.call_record_info.user_id == user_id).filter(models.call_record_info.date == self.today.strftime('%Y-%m-%d')).order_by(models.call_record_info.time.desc()).all()
        if last_call is None:
            raise HTTPException(status_code=400, detail="통화 기록이 없습니다.")
        
        #전화번호별로 통화횟수와 통화시간을 구한다
        phone_dict = {}
        for record in last_call:
            if record.phone in phone_dict:
                phone_dict[record.phone]["duration"] += int(record.duration)
                phone_dict[record.phone]["count"] += 1
                phone_dict[record.phone]["name"] = record.name
            else:
                phone_dict[record.phone] = {"duration" : int(record.duration), "count" : 1, "name" : record.name}

        #통화시간이 긴 3명을 가져온다
        try:
            phone_list = sorted(phone_dict.items(), key=lambda x: x[1]["duration"], reverse=True)[:3]
        except:
            #3명이 되지 않을 경우 긴순서대로 정렬해서 반환
            phone_list = sorted(phone_dict.items(), key=lambda x: x[1]["duration"], reverse=True)

        print(phone_list)
        content = {"date" : self.today.strftime('%Y-%m-%d'), "users" : []}
        for phone in phone_list:
            content["users"].append({
                "name" : phone[1]["name"],
                "phone" : phone[0],
                "duration" : str(phone[1]["duration"]),
                "count" : str(phone[1]["count"])
            })
        


        return Last_call_response(status = "success", message = "통화 기록 조회 성공", content = content)
    
    def check_user_id(self, user_id : str):
        user = self.db.query(models.user_info).filter(models.user_info.user_id == user_id).first()
        if user is None:
            raise HTTPException(status_code=400, detail="사용자 정보가 없습니다.")

