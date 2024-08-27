from fastapi import APIRouter, UploadFile, HTTPException, Depends
from fastapi.security import APIKeyHeader
from typing import Optional

from .schemes import *
from .service.upload import upload_service
from .service.download import download_service
from .service.user import User_service
from .service.album import Album_service
from .util import JWTService
from .fcm_service import send_push_notification

router = APIRouter()
jwt = JWTService()


# 테스트 라우트
#User/sharedAlbum
@router.get("/users/get/{user_id}/albumId", responses = {200 : {"model" : Album_list_response, "description" : "앨범 리스트 조회 성공"}, 400 : {"model" : Error_response, "description" : "앨범 리스트 조회 실패"}}, tags = ["Test/User/sharedAlbum"], summary = "앨범 리스트 조회(구현중)")
async def get_album_list(user_id : str, album_service : Album_service = Depends()):
    try:
        return await album_service.get_album_list(user_id)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/users/album/create", responses = {200 : {"model" : Album_create_response, "description" : "앨범 생성 성공"}, 400 : {"model" : Error_response, "description" : "앨범 생성 실패"}}, tags = ["Test/User/sharedAlbum"], summary = "앨범 생성(구현중)")
async def create_album(request : Album_create_request, album_service : Album_service = Depends()):
    try:
        return await album_service.create_album(request)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/test/jwt/makeAccessToken", tags = ["Test/JWT"], summary="테스트용 토큰 생성")
async def test_make_access_token(phone : str, userId : str):
    try:
        return jwt.create_access_token(phone, userId), jwt.create_refresh_token(phone, userId)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/test/fcm/send", tags = ["Test/FCM"], summary = "푸시 알림 테스트")
async def test_send_fcm(request : FCM_request):
    try:
        data = {
            "title" : request.title,
            "body" : request.body
        }
        return await send_push_notification(request.token, data)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


# 실제 라우트
#autoLogin
@router.post("/login", responses = {200 : {"model" : Login_response, "description" : "로그인 성공"}, 400 : {"model" : Error_response, "description" : "로그인 실패"}}, tags = ["AutoLogin"], summary = "자동 로그인 / access_token 만료 에러시 refresh_token으로 재시도 할 것 / refresh_token으로 재시도시 access_token과 refresh_token이 반환됨 / 로그인 성공시 content는 NULL")
async def login(token = Depends(APIKeyHeader(name = "Authorization")), user_service : User_service = Depends()):
    try:
        return await user_service.login(token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

#Register
@router.post("/register/users", responses = {200 : {"model" : CommoneResponse, "description" : "사용자 등록 성공"}, 400 : {"model" : Error_response, "description" : "사용자 등록 실패"}}, tags = ["Register"], summary = "전화번호, 통신사를 서버로 전송")
async def register_user(request : User_info_request, user_service : User_service = Depends()):
    try:
        return await user_service.register_user(request)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/register/certificate", responses = {200 : {"model" : Certificate_response, "description" : "인증 성공"}, 400 : {"model" : Error_response, "description" : "인증 실패"}}, tags = ["Register"], summary = "인증번호를 서버로 전송(아직 미구현 상태, 인증번호는 아무거나 입력해도 됨) / 기존 사용자인 경우 isExist = true, 신규 사용자인 경우 isExist = false")
async def certification_user(request : Certificate_request, user_service : User_service = Depends()):
    try:
        return await user_service.certification_user(request)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/register/setprofile", responses = {200 : {"model" : CommoneResponse, "description" : "프로필 등록 성공"}, 400 : {"model" : Error_response, "description" : "프로필 등록 실패"}}, tags = ["Register"], summary = "프로필 사진과 사용자 이름을 서버로 전송(쿼리스트링으로 전송 바람) / 이 과정까지만 userId를 사용 이후엔 토큰을 사용")
async def set_profile(userId : str, name : str, fcmToken : str,file : Optional[UploadFile] = None, user_service : User_service = Depends()):
    try:
        return await user_service.set_profile(userId, name, fcmToken, file)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


#User/Profile
@router.get("/users/getprofile", responses = {200 : {"model" : Get_profile_response, "description" : "프로필 조회 성공"}, 400 : {"model" : Error_response, "description" : "프로필 조회 실패"}}, summary= "프로필 사진과 사용자 정보를 불러오는 api", tags = ["User/Profile"], description = "프로필 사진과 사용자 정보를 불러오는 api")
async def get_profile(token = Depends(APIKeyHeader(name="Authorization")), user_service : User_service = Depends()):
    try:
        return await user_service.get_profile(token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/users/modify/profile", responses = {200 : {"model" : Profile_modify_response, "description" : "프로필 수정 성공"}, 400 : {"model" : Error_response, "description" : "프로필 수정 실패"}}, tags = ["User/Profile"], summary = "프로필 사진을 수정하는 api")
async def modify_profile(file : UploadFile, token = Depends(APIKeyHeader(name = "Authorization")), user_service : User_service = Depends()):
    try:
        return await user_service.modify_profile(token, file)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


#User/Phone
@router.post("/users/upload/phone", responses = {200 : {"model" : CommoneResponse, "description" : "동기화 성공"}, 400 : {"model" : Error_response, "description" : "동기화 실패"}}, tags = ["User/Phone"], summary = "전화번호부 동기화")
async def upload_phone_info(request : Phone_request, token = Depends(APIKeyHeader(name = "Authorization")),upload_service : upload_service = Depends()):
    try:
        return await upload_service.upload_phone_info(request, token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
@router.post("/users/upload/callrecord", responses = {200 : {"model" : Call_record_response, "description" : "동기화 성공"}, 400 : {"model" : Error_response, "description" : "동기화 실패"}}, summary = "통화 기록 동기화, 응답으로 통화 횟수와 통화 시간이 많은 순으로 정렬된 리스트를 반환", tags = ["User/Phone"])
async def upload_call_record(request : Call_record_request, token = Depends(APIKeyHeader(name = "Authorization")),upload_service : upload_service = Depends()):
    try:
        return await upload_service.upload_call_record(request, token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
@router.get("/users/get/longestcall", responses = {200 : {"model" : Last_call_response, "description" : "통화 기록 조회 성공"}, 400 : {"model" : Error_response, "description" : "통화 기록 조회 실패"}}, tags = ["User/Phone"], summary = "오늘 날짜의 통화 기록중 긴 통화 시간을 가진 최대 3명을 가져오는 api")
async def get_last_call(token = Depends(APIKeyHeader(name = "Authorization")),download_service : download_service = Depends()):
    try:
        return await download_service.get_last_call(token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    

#User/Friend
@router.get("/users/get/friend", responses = {200 : {"model" : Friend_list_response, "description" : "친구 리스트 조회 성공"}, 400 : {"model" : Error_response, "description" : "친구 리스트 조회 실패"}}, tags = ["User/friend"], summary = "친구 리스트 조회 / is_friend는 서로 친구인지 여부를 나타냄")
async def get_friend_list(token = Depends(APIKeyHeader(name = 'Authorization')), user_service : User_service = Depends()):
    try:
        return await user_service.get_friend_list(token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
@router.post("/users/add/friend", responses = {200 : {"model" : CommoneResponse, "description" : "친구 요청 성공"}, 400 : {"model" : Error_response, "description" : "친구 추가 실패"}}, tags = ["User/friend"], summary = "친구 추가(요청)")
async def add_friend(request : Add_friend_request, token = Depends(APIKeyHeader(name = "Authorization")), user_service : User_service = Depends()):
    try:
        return await user_service.add_friend(request, token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.delete("/users/delete/friend", responses = {200 : {"model" : CommoneResponse, "description" : "친구 삭제 성공"}, 400 : {"model" : Error_response, "description" : "친구 삭제 실패"}}, tags = ["User/friend"], summary = "친구 삭제")
async def delete_friend(request : Delete_friend_request, token = Depends(APIKeyHeader(name = "Authorization")), user_service : User_service = Depends()):
    try:
        return await user_service.delete_friend(request, token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
@router.get("/users/get/friend/request", responses = {200 : {"model" : Friend_request_list_response, "description" : "친구 요청 리스트 조회 성공"}, 400 : {"model" : Error_response, "description" : "친구 요청 리스트 조회"}}, tags = ["User/friend"], summary = "친구 요청 리스트 조회")
async def get_friend_request_list(token = Depends(APIKeyHeader(name = "Authorization")),user_service : User_service = Depends()):
    try:
        return await user_service.get_friend_request_list(token) #친구요청 날짜 필요 / 오늘, 어제는 한글로 그 이전에는 날짜를 키로 map 형태로 반환
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/users/accept/friend", responses = {200 : {"model" : CommoneResponse, "description" : "친구 요청 수락 성공"}, 400 : {"model" : Error_response, "description" : "친구 요청 수락 실패"}}, tags = ["User/friend"], summary = "친구 요청 수락")
async def accept_friend(request : Accept_friend_request, token = Depends(APIKeyHeader(name = "Authorization")), user_service : User_service = Depends()):
    try:
        return await user_service.accept_friend(request, token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.post("/users/reject/friend", responses = {200 : {"model" : CommoneResponse, "description" : "친구 요청 거절 성공"}, 400 : {"model" : Error_response, "description" : "친구 요청 거절 실패"}}, tags = ["User/friend"], summary = "친구 요청 거절")
async def reject_friend(request : Reject_friend_request, token = Depends(APIKeyHeader(name = "Authorization")), user_service : User_service = Depends()):
    try:
        return await user_service.reject_friend(request, token)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

#친구 삭제 필요
#새로운 사진, 공유 엘범 리스트, 즐겨찾기, 즐겨찾기 해제