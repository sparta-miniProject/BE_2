package com.sparta.be.exception;

import com.sparta.be.errorcode.ErrorCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestApiException extends RuntimeException{

    private final ErrorCode errorCode;

    //getter
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }

    // 생성자
    public RestApiException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

}
