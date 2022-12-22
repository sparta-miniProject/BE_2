package com.sparta.be.config;

import com.sparta.be.jwt.JwtAuthFilter;
import com.sparta.be.jwt.JwtUtil;
import com.sparta.be.security.CustomAccessDeniedHandler;
import com.sparta.be.security.CustomAuthenticationEntryPoint;
import com.sparta.be.security.CustomSecurityFilter;
import com.sparta.be.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtUtil jwtUtil;

    @Bean // 비밀번호 암호화 기능 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())       //db를 바꿔야하나?
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

//    /**
//     * 이 설정을 해주면, 우리가 설정한대로 CorsFilter가 Security의 filter에 추가되어
//     * 예비 요청에 대한 처리를 해주게 됩니다.
//     * cors 개념 참고 - https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F
//     */
//
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // cors 설정
        http.cors();
        // CSRF 설정
        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());


        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()

                // 토큰검증 필요없는 페이지 설정
                .antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/user/login-page").permitAll()
                .antMatchers(HttpMethod.GET,"/api/tops").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user/idCheck/**").permitAll()


                .antMatchers("/swagger-ui/**").permitAll() //스웨거 권한설정 X
                .antMatchers("/swagger-resources/**").permitAll() //스웨거 권한설정 X
                .antMatchers("/swagger-ui.html").permitAll() //스웨거 권한설정 X
                .antMatchers("/v2/api-docs").permitAll() //스웨거 권한설정 X
                .antMatchers("/v3/api-docs").permitAll() //스웨거 권한설정 X
                .antMatchers("/webjars/**").permitAll() //스웨거 권한설정 X

                .anyRequest().authenticated()
                //서버는 JWT 토큰을 검증하고 토큰의 정보를 사용하여 사용자의 인증을 진행해주는 Spring Security 에 등록한 JwtAuthFilter 를 사용하여 인증/인가를 처리한다.
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // Custom 로그인 페이지 사용
//        http.formLogin().loginPage("/api/user/login-page").permitAll();
        // 접근 제한 페이지 이동 설정
//          http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();

        // 서버에서 응답하는 리소스에 접근 가능한 출처를 명시
        // Access-Control-Allow-Origin
        config.addAllowedOrigin("http://localhost:3000");
//        config.addAllowedOrigin("http://sulproject.s3-website.ap-northeast-2.amazonaws.com/"); //요거 변경하시면 됩니다.
//        config.addAllowedOrigin("http://alcoholpj8.s3-website.ap-northeast-2.amazonaws.com/");
        config.addAllowedOrigin("http://sulalcohol8pj.s3-website.ap-northeast-2.amazonaws.com/");

        // 특정 헤더를 클라이언트 측에서 꺼내어 사용할 수 있게 지정
        // 만약 지정하지 않는다면, Authorization 헤더 내의 토큰 값을 사용할 수 없음
        // Access-Control-Expose-Headers
        config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

        // 본 요청에 허용할 HTTP method(예비 요청에 대한 응답 헤더에 추가됨)
        // Access-Control-Allow-Methods
        config.addAllowedMethod("*");

        // 본 요청에 허용할 HTTP header(예비 요청에 대한 응답 헤더에 추가됨)
        // Access-Control-Allow-Headers
        config.addAllowedHeader("*");

        // 기본적으로 브라우저에서 인증 관련 정보들을 요청 헤더에 담지 않음
        // 이 설정을 통해서 브라우저에서 인증 관련 정보들을 요청에 담을 수 있도록 해줍니다.
        // Access-Control-Allow-Credentials
        config.setAllowCredentials(true);

        // allowCredentials 를 true로 하였을 때,
        // allowedOrigin의 값이 * (즉, 모두 허용)이 설정될 수 없도록 검증합니다.
        config.validateAllowCredentials();

        // 어떤 경로에 이 설정을 적용할 지 명시합니다. (여기서는 전체 경로)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
