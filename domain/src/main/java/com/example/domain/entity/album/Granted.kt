package com.example.domain.entity.album

enum class Granted(val value: String, val description: String) {
    MEMBER(value = "founder", description = "앨범 창시자, 사진 추가, 삭제 가능(다른 사람것도), 복구 가능, 앨범 용량 확대 가능, 권한 수정"),
    MANAGER(value = "manager", description = "사진 추가, 삭제 가능(다른 사람의 사진도)"),
    FOUNDER(value = "member", description = "사진 추가, 삭제 가능(자기 사진만)")
}