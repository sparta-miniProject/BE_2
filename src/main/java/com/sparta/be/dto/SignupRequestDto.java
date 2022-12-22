package com.sparta.be.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {
    //  ^([\w\.\_\-])*[a-zA-Z0-9]+([\w\.\_\-])*([a-zA-Z0-9])+([\w\.\_\-])+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,8}$
//     @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[0-9]).{4,10}$",
//     message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
//     message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 합니다.")
//     @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%.^&*-]).{8,15}
    @Size(min = 4, max = 10)
    @Pattern(regexp="^(?=.*?[0-9])(?=.*?[a-z]).{4,10}$")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern (regexp="^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[#?!@$%.^&*-])(?=\\S+$).{8,15}$")
    private String password;

    @Size(min = 8, max = 15)
    @Pattern (regexp="^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[#?!@$%.^&*-])(?=\\S+$).{8,15}$")
    private String checkPassword;

    private boolean admin = false;
    private String adminToken = "";

}
