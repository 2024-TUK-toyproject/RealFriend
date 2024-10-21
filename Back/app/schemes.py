from pydantic import BaseModel, Field
from typing import List, Dict, Optional, Union


# Response Model
class CommoneResponse(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")

class Error_response(BaseModel):
    detail : str = Field("404: {'status': 'error', 'detail': '에러 사유'}")

class Last_call_content(BaseModel):
    date : str = Field(..., example = "2024-07-25")
    updateTime : int = Field(..., example = "14", description="업데이트 당시 시간")
    difference : int = Field(..., example = "100", description="어제와의 통화시간 차이, 양수면 증가, 음수면 감소")
    users : List[Dict[str,Optional[str]]] = Field([{"name" : "홍길동", "phone" : "010-1234-5678", "duration" : "90", "type" : "1"}], example = [{"name" : "홍길동", "phone" : "010-1234-5678", "duration" : "90", "type" : "1"}])

class Last_call_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Last_call_content

class Get_profile_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Dict[str, str] = Field({"name" : "홍길동", "phone" : "010-1234-5678", "profileImage" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg"}, example = {"name" : "홍길동", "phone" : "010-1234-5678", "profileImage" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg"})

class Profile_only_response(BaseModel):
    profileImage : str = Field(..., example = "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg")

class Profile_modify_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Dict[str, str] = Field({"profileImage" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg"}, example = {"profileImage" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg"})

class Most_friendly_list(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    duration : int = Field(..., example = "600", description="통화시간(초)")
    count : int = Field(..., example = "10", description="통화횟수")

class Call_record_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : List[Most_friendly_list]

#사용자 등록 관련
class Register_user_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Dict[str, str] = Field({"phone" : "010-1234-1234"}, example = {"phone" : "010-1234-1234"})

class Certificate_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Dict[str, Optional[Union[str, bool]]] = Field(
        {
            "userId": "123456",
            "isExist" : True,
            "accessToken" : "asdasdasd",
            "refreshToken" : "dsadsadas"
        }
    )

#공유 앨범
class Album_list_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : List[Dict[str, Optional[Union[str, int, bool]]]] = Field([{"albumId" : "123456", "albumName" : "가족사진", "albumThumnail" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg", "albumMemberCount" : 2, "albumPictureCount" : 10, "isStared" : True}
                                            ,{"albumId" : "654321", "albumName" : "여행사진", "albumThumnail" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg", "albumMemberCount" : 3, "albumPictureCount" : 10, "isStared" : True}])

class Album_create_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Dict[str, str]= Field({"albumId" : "123456"}, example = {"albumId" : "123456"})

#친구 관련
class Friend_list_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content: List[Dict[str, Optional[Union[str, bool]]]] = Field(
    [
        {
            "userId": "123456",
            "name": "홍길동",
            "phone": "010-1234-5678",
            "profileImage": "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg",
            "isFriend": True  # 불린 타입 예시
        }
    ])

class Friend_request_list_response(BaseModel):
    status: str = Field("success", example="success or error")
    message: str = Field("성공메시지 or 오류메시지")
    content: Dict[str, List[Dict[str, Optional[Union[str, bool]]]]] = Field(
        {
            "오늘": [
                {
                    "friendRequestId" : "123456",
                    "userId": "123456",
                    "name": "홍길동",
                    "phone": "010-1234-5678",
                    "profileImage": "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg",
                    "time" : "14:20:01"
                },
                {
                    "friendRequestId" : "123456",
                    "userId": "654321",
                    "name": "김철수",
                    "phone": "010-1234-5678",
                    "profileImage": "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg",
                    "time" : "14:20:01"
                }
            ],
            "어제": [
                {
                    "friendRequestId" : "123456",
                    "userId": "123456",
                    "name": "홍길동",
                    "phone": "010-1234-5678",
                    "profileImage": "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg",
                    "time" : "14:20:01"
                }
            ],
            "2024-08-10": [
                {
                    "friendRequestId" : "123456",
                    "userId": "654321",
                    "name": "김철수",
                    "phone": "010-1234-5678",
                    "profileImage": "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg",
                    "time" : "14:20:01"
                }
            ]
        }
    )

class Friend_recommend_response(BaseModel):
    status: str = Field("success", example="success or error")
    message: str = Field("성공메시지 or 오류메시지")
    content: List[Dict[str, Optional[Union[str, bool]]]] = Field(
        [
            {
                "userId": "123456",
                "name": "홍길동",
                "phone": "010-1234-5678",
                "profileImage": "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg",
                "isExist": True  # 불린 타입 예시
            }
        ]
    )

class token_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Dict[str, str] = Field({"userID" : "123456", "accessToken" : "123456", "refreshToken" : "123456"})

class Login_response(BaseModel):
    status : str = Field("success", example = "success or error or retry")
    message : str = Field("성공메시지 or 오류메시지 or 재시도 메시지")
    content: Optional[Dict[str, Optional[str]]] = Field(
        None,
        example={"accessToken": "asdasdasd", "refreshToken": "asdasdasd"}
    )

class Album_info_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Dict[str, Optional[Union[str, int, float]]] = Field({"albumName" : "가족사진", "albumMemberCount" : 2, "albumPictureCount" : 10, "currentUsage" : 100, "totalUsage" : 15360000.0})

class Album_picture_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : List[Dict[str,str]] = Field([{"pictureId" : "123456", "pictureUrl" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg"}])

class Album_picture_info_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : Dict[str, Optional[Union[int, float, str]]] = Field({"name" : "asdasd", "usage" : 10, "date" : "2024-07-25", "time" : "14:20:01", "uploadName" : "홍길동", "profileUrl" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg", "uploadDate" : "2024-07-25", "uploadTime" : "14:20:01"})

class Album_reply_response(BaseModel):
    status : str = Field("success", example = "success or error")
    message : str = Field("성공메시지 or 오류메시지")
    content : List[Dict[str, str]] = Field([{"replyId" : "123456", "userName" : "홍길동", "userProfile" : "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg", "date" : "2024-07-25", "time" : "14:20:01", "message" : "댓글 내용"}])

# Request Model
class Phone_list(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")

class Phone_request(BaseModel):
    content : List[Phone_list]

class Call_record_list(BaseModel):
    name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    date : str = Field(..., example = "2024.11.20.14.20.01")
    duration : int = Field(..., example = "90")
    type : int = Field(..., example = "1", description="1 : 수신, 2 : 발신, 3 : 부재중")

class Call_record_request(BaseModel):
    content : List[Call_record_list]

class User_info_request(BaseModel):
    #name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    company : str = Field(..., example="skt")

class Certificate_request(BaseModel):
    code : str = Field(..., example = "123456")
    #name : str = Field(..., example = "홍길동")
    phone : str = Field(..., example = "010-1234-5678")
    company : str = Field(..., example="skt")
    
class Set_profile_request(BaseModel):
    userId : str = Field(..., example = "123456")
    name : str = Field(..., example = "홍길동")

class Album_create_request(BaseModel):
    content : List[Dict[str, str]] = Field(
        [{"friendId" : "654321"}, {"friendId" : "123456"}]
    )

class Add_friend_request(BaseModel):
    friendId : str = Field(..., example = "654321")

class Reject_friend_request(BaseModel):
    friendRequestId : str = Field(..., example = "123456")

class Accept_friend_request(BaseModel):
    friendRequestId : str = Field(..., example = "123456")

class Delete_friend_request(BaseModel):
    content : List[Dict[str, str]] = Field(
        [{"friendId" : "654321"}]
    )

class FCM_request(BaseModel):
    token : str = Field(..., example = "ksjdnfjkdasnfljsknafljansdfjlsakn")
    title : str = Field(..., example = "CONNEX")
    body : str = Field(..., example = "친구 요청이 왔습니다.")

class Friend_recommend_request(BaseModel):
    content : List[Dict[str, str]] = Field(
        [{
            "name" : "홍길동", 
            "phone" : "010-1234-5678"
        }]
    )

class Album_authority_request(BaseModel):
    albumId : str = Field(..., example = "123456")
    content : List[Dict[str, str]] = Field(
        [{
            "friendId" : "123456",
            "post" : "1",
            "delete" : "1"
        },
        {
            "friendId" : "654321",
            "post" : "1",
            "delete" : "1"
        }]
    )

class Album_thumbnail_request(BaseModel):
    albumId : str = Field(..., example = "123456")
    albumName : str = Field(..., example = "가족사진")
    albumThumbnail : str = Field(..., example = "https://s3.ap-northeast-2.amazonaws.com/album-app/123456/123456.jpg")

class photo_delete_request(BaseModel):
    content : List[Dict[str, str]] = Field([{"photoId" : "123456"}, {"photoId" : "654321"}])

class Album_reply_request(BaseModel):
    photoId : str = Field(..., example = "123456")
    content : str = Field(..., example = "댓글 내용")