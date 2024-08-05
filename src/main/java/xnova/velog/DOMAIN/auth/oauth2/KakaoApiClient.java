package xnova.velog.DOMAIN.auth.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xnova.velog.Entity.Member;


@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";

    @Value("${kakao.url.auth}") // @Value: application.yml 파일에서 설정된 값을 Java 클래스에서 쉽게 사용
    private String authUrl;

    @Value("${kakao.url.api}")
    private String apiUrl;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.logout-redirect-uri}")
    private String logoutRedirectUri;

    private final RestTemplate restTemplate;

    // 카카오 OAuthProvider 반환
    @Override
    public Member.OAuthProvider oAuthProvider() {
        return Member.OAuthProvider.KAKAO;
    }

    // OAuth 인증을 통해 액세스 토큰을 요청
    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        String url = authUrl + "/oauth/token"; // 카카오에게 보낼 요청 URL

        // HttpHeaders 객체 생성 및 Content-Type 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // MediaType: HTTP 요청 또는 응답의 본문 데이터의 형식을 지정

        // 본문 body 설정
        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);

        // HTTP 헤더와 본문을 포함
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        // RestTemplate을 사용하여 HTTP POST 요청을 보내고, 응답을 KakaoTokens 객체로 받는다.
        // (요청을 보낼 URL, HttpEntity 객체(헤더와 본문을 포함한), 응답 본문을 매핑할 클래스 타입)
        KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);

        // 응답에서 액세스 토큰을 추출하여 반환
        assert response != null;
        return response.getAccessToken();
    }
    // 카카오 API 서버에 액세스 토큰을 사용하여 사용자 정보를 요청
    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken); // Authorization 헤더에 Bearer 토큰을 추가

        // 요청 본문에 폼 데이터를 추가하기 위한 MultiValueMap 객체를 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        // property_keys라는 키에 kakao_account.email과 kakao_account.profile 값을 포함한 JSON 배열을 문자열로 추가
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        // KakaoInfoResponse 객체 반환
        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);

    }
    // 카카오톡 로그아웃 요청
    public void logout(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(LOGOUT_URL, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("카카오 로그아웃 요청 실패: " + response.getStatusCode());
        }
    }
}
