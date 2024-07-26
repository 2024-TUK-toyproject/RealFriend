from pydantic import BaseModel, Field
from typing import List, Dict, Optional


# Response Model
class CommoneResponse(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")

class Last_call_response(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    date : str = Field(..., example = "2024-07-25")
    time : str = Field(..., example = "14:20:01")
    duration : int = Field(..., example = "90", description="통화시간(초)")
    type : int = Field(..., example = "1", description="1 : 수신, 2 : 발신, 3 : 부재중")

class Profile_response(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    profile_image : str = Field(..., example = "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg")

class Most_friendly_list(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    duration : int = Field(..., example = "600", description="통화시간(초)")
    count : int = Field(..., example = "10", description="통화횟수")

class Call_record_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : List[Most_friendly_list]

# Request Model
class Phone_list(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")

class Phone_request(BaseModel):
    userId : str = Field(..., example = "123456")
    content : List[Phone_list]

class Call_record_list(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    date : str = Field(..., example = "2024.11.20.14.20.01")
    duration : str = Field(..., example = "00:01:30")
    type : int = Field(..., example = "1", description="1 : 수신, 2 : 발신, 3 : 부재중")

class Call_record_request(BaseModel):
    userId : str = Field(..., example = "123456")
    content : List[Call_record_list]

class User_info_request(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    
class Set_profile_request(BaseModel):
    userId : str = Field(..., example = "123456")
    

