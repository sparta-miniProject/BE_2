package com.sparta.be.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequestDto {

    @Size(min = 4, max = 10)
    private String username;

    @Size(min = 8, max = 15)
    private String password;

}
