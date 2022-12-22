//package com.sparta.be.config;

//
//import com.sparta.be.jwt.JwtUtil;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//
//// CORS 란?
//// 백에서 만든서버에 프론트가 요청을해서 값을 가져가는건데 막 가져가게두면 보안에 취약,
//// 백에서 허용할 출처를 세팅해줘야 함  -> 서버 전역적으로 걸수도있고, 특정컨트롤러, 메소드 등에만에도 CORS 가 적용가능
//
//// 스프링 서버 전역적으로 CORS 설정
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
////    public static final String ALLOWED_METHOD_NAMES = "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH";
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")   // 허용할 출처
//                .allowedHeaders("*")
//                .allowedMethods("*")
////                .allowCredentials(true)   // 쿠키 인증 요청 허용
//                .allowedMethods("*")
//                .exposedHeaders(JwtUtil.AUTHORIZATION_HEADER); //JSON 으로 Token 내용 전달;
//        // 허용할 HTTP method
//

//    }
//
//    // 특정 컨트롤러에만 CORS 적용하고 싶을때.
////    @Controller
////    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
////    public class customController {
////
////        // 특정 메소드에만 CORS 적용 가능
////        @GetMapping("/url")
////        @CrossOrigin(origins = "*", methods = RequestMethod.GET)
////        @ResponseBody
////        public List<Object> findAll(){
////            return service.getAll();
////        }
////    }
//
//
//}
