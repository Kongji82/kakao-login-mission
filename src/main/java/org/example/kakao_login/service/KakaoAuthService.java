package org.example.kakao_login.service;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class KakaoAuthService {

    private final RestTemplate restTemplate = new RestTemplate();
    public static final String URL = "https://kauth.kakao.com/oauth/token";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String getUserInfoURL;

    // token 가져오기
    public ResponseEntity<Map> getKakaoToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("code", code);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
            .queryParam("grant_type", params.get("grant_type"))
            .queryParam("client_id", params.get("client_id"))
            .queryParam("client_secret", params.get("client_secret"))
            .queryParam("redirect_uri", params.get("redirect_uri"))
            .queryParam("code", params.get("code"));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.POST,
            entity,
            Map.class
        );
        String accessToken = String.valueOf(response.getBody().get("access_token"));
        return response;
    }

    public ResponseEntity<Map> getKakaoUserInfo(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(getUserInfoURL, HttpMethod.GET, entity, Map.class);
        log.info(response.toString());
//        log.info((String) response.getBody().get("id"));
        return response;
    }

}
