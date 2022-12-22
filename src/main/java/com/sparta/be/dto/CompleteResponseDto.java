package com.sparta.be.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteResponseDto {
    private String msg;
    private int statusCode;

    public static CompleteResponseDto success(String msg) {
        return new CompleteResponseDto(msg, HttpStatus.OK.value());
    }

}
