from fastapi import APIRouter, UploadFile, HTTPException, Depends
from fastapi.responses import JSONResponse

from .config import Config
from .schemes import *
from .service.upload import upload_service
from .service.download import download_service
from .service.user import User_service

import boto3
import uuid
import urllib

router = APIRouter()

s3 = boto3.client(
    "s3",
    aws_access_key_id=Config.s3_access_key,
    aws_secret_access_key=Config.s3_secret_key
)

@router.post("/test/upload")
async def upload(file: UploadFile, directory: str):
    try:
        filen_name = f"{str(uuid.uuid4())}.jpg"
        s3_key = f"{directory}/{filen_name}"

        s3.upload_fileobj(
            file.file,
            Config.s3_bucket,
            s3_key
        )

        url = "https://%s.s3.amazonaws.com/%s/%s" % (
            Config.s3_bucket,
            directory,
            urllib.parse.quote(filen_name)
        )
        return JSONResponse(content = "{'url': '%s'}" % url)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/test/setprofile")
async def set_profile(userId : str, file : UploadFile, user_service : User_service = Depends()):
    try:
        return await user_service.set_profile(userId, file)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.get("/test/getprofile/{userId}")
async def get_profile(userId : str, user_service : User_service = Depends()):
    try:
        return await user_service.get_profile(userId)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))




@router.post("/register/users", responses = {200 : {"model" : CommoneResponse, "description" : "사용자 등록 성공"}, 400 : {"model" : CommoneResponse, "description" : "사용자 등록 실패"}})
async def register_user(request : User_info_request, user_service : User_service = Depends()):
    try:
        return await user_service.register_user(request)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/upload/phone", responses = {200 : {"model" : CommoneResponse, "description" : "동기화 성공"}, 400 : {"model" : CommoneResponse, "description" : "동기화 실패"}})
async def upload_phone_info(request : Phone_request, upload_service : upload_service = Depends()):
    try:
        return await upload_service.upload_phone_info(request)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
@router.post("/upload/callrecord", responses = {200 : {"model" : Call_record_response, "description" : "동기화 성공"}, 400 : {"model" : CommoneResponse, "description" : "동기화 실패"}}, summary = "통화 기록 동기화, 응답으로 통화 횟수와 통화 시간이 많은 순으로 정렬된 리스트를 반환합니다.")
async def upload_call_record(request : Call_record_request, upload_service : upload_service = Depends()):
    try:
        return await upload_service.upload_call_record(request)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
@router.get("/get/{user_id}/lastcall", responses = {200 : {"model" : Last_call_response, "description" : "통화 기록 조회 성공"}, 400 : {"model" : CommoneResponse, "description" : "통화 기록 조회 실패"}})
async def get_last_call(user_id : str, download_service : download_service = Depends()):
    try:
        return await download_service.get_last_call(user_id)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))