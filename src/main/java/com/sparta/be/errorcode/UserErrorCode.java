package com.sparta.be.errorcode;

import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode{

    ONLY_FOR_ADMIN("관리자만 가능합니다.", HttpStatus.BAD_REQUEST.value()),
    WRONG_USERNAME_PATTERN("유저명은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.", HttpStatus.BAD_REQUEST.value()),
    WRONG_PASSWORD_PATTERN("비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 합니다.", HttpStatus.BAD_REQUEST.value()),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value()),
    NO_USER("회원을 찾을 수 없습니다.",HttpStatus.NOT_FOUND.value()),
    WRONG_ADMINTOKEN("관리자 암호가 틀려 등록이 불가능합니다.", HttpStatus.BAD_REQUEST.value()),
    OVERLAPPED_USERNAME("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()),
    INVALID_TOKEN("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());

    private final String msg;
    private final int statusCode;

    UserErrorCode(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
    @Override
    public String getMsg(){
        return this.msg;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

}
