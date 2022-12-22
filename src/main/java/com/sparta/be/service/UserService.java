package com.sparta.be.service;


import com.sparta.be.dto.CompleteResponseDto;
import com.sparta.be.dto.LoginRequestDto;
import com.sparta.be.dto.SignupRequestDto;
import com.sparta.be.entity.User;
import com.sparta.be.entity.UserRoleEnum;
import com.sparta.be.errorcode.UserErrorCode;
import com.sparta.be.exception.RestApiException;
import com.sparta.be.jwt.JwtUtil;
import com.sparta.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    static String msg;
    static int statusCode = 400;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional   // 회원가입
    public CompleteResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String pw = signupRequestDto.getPassword();
        String pwcheck = signupRequestDto.getCheckPassword();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 중복검사
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new RestApiException(UserErrorCode.OVERLAPPED_USERNAME);
        }

        // password - passwordcheck 검사
        if (!pw.equals(pwcheck)) {
            throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
        }

        //관리자 검사
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new RestApiException(UserErrorCode.WRONG_ADMINTOKEN);
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
        return CompleteResponseDto.success("회원가입 성공");
    }

    @Transactional(readOnly = true)   // 로그인
    public CompleteResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // id 가 틀림
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RestApiException(UserErrorCode.NO_USER)
        );

        // password 가 틀림
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
        }

        //토큰 부여
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return CompleteResponseDto.success("로그인 성공");
    }

    // id 중복체크
    @Transactional
    public CompleteResponseDto idCheck(String username) {
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new RestApiException(UserErrorCode.OVERLAPPED_USERNAME);
        }
        return CompleteResponseDto.success("사용할 수 있는 아이디입니다.");
    }   //수정해야함  중복은아닌데  정규식에 걸릴수있음

    
}
