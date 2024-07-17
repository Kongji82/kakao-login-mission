package org.example.kakao_login.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.net.URI;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.example.kakao_login.service.KakaoAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping()
public class KakaoLoginController {

    private final KakaoAuthService kakaoAuthService;

    public KakaoLoginController(KakaoAuthService kakaoAuthService) {
        this.kakaoAuthService = kakaoAuthService;
    }

    // redirect 설정 URL
    @GetMapping("/login/oauth2/kakao")
    public ResponseEntity<Map> kakaoLogin(HttpServletRequest request) {
        String code = request.getParameter("code");
        ResponseEntity<Map> response;
        String accessToken;
        try {
            // 카카오 서버로부터 토큰 발행
            response = kakaoAuthService.getKakaoToken(code);
            accessToken = String.valueOf(response.getBody().get("access_token"));

            // 카카오 서버로부터 유저 정보 가져오기
            ResponseEntity<Map> kakaoUserInfo = kakaoAuthService.getKakaoUserInfo(accessToken);

            // 세션 저장
            request.getSession().invalidate();
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", kakaoUserInfo.getBody().get("id"));
        } catch (Exception e) {
            throw new RuntimeException("login fail");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/userinfo"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/login"));
        return new ResponseEntity(header, HttpStatus.FOUND);
    }
}


