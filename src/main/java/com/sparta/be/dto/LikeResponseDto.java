package com.sparta.be.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class LikeResponseDto {
    private boolean success;
    private String msg;
    private int statusCode;

    public LikeResponseDto(boolean success, String msg, int statusCode) {
        this.success = success;
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
