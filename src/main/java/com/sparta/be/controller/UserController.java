package com.sparta.be.controller;


import com.sparta.be.dto.CompleteResponseDto;
import com.sparta.be.dto.LoginRequestDto;
import com.sparta.be.dto.SignupRequestDto;
import com.sparta.be.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = {"User API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    // 회원가입
    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public CompleteResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    // 로그인
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public CompleteResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    // id 중복체크
    @ApiOperation(value = "회원가입 id 중복체크")
    @GetMapping("/idCheck/{username}")
    public CompleteResponseDto idCheck(@PathVariable String username) {
        return userService.idCheck(username);
    }
}
