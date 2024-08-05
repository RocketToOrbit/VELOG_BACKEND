package xnova.velog.DOMAIN.auth.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xnova.velog.Entity.Member;

@Component
@RequiredArgsConstructor
public class NaverApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String LOGOUT_URL = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id={client_id}&client_secret={client_secret}&access_token={access_token}&service_provider=NAVER";
    @Value("${naver.url.auth}")
    private String authUrl;

    @Value("${naver.url.api}")
    private String apiUrl;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    @Override
    public Member.OAuthProvider oAuthProvider() {
        return Member.OAuthProvider.NAVER;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        String url = authUrl + "/oauth2.0/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        NaverTokens response = restTemplate.postForObject(url, request, NaverTokens.class);

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v1/nid/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, NaverInfoResponse.class);
    }

//    public void logout(String accessToken) {
//        String url = LOGOUT_URL.replace("{client_id}", clientId)
//                .replace("{client_secret}", clientSecret)
//                .replace("{access_token}", accessToken);
//
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//        if (response.getStatusCode() != HttpStatus.OK) {
//            throw new RuntimeException("네이버 로그아웃 요청 실패: " + response.getStatusCode());
//        }
//    }
}