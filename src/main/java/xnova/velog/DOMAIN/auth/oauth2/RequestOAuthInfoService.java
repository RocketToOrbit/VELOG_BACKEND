package xnova.velog.DOMAIN.auth.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xnova.velog.Entity.Member;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
// OAuth 제공자와 해당 클라이언트를 매핑
public class RequestOAuthInfoService {
    private final Map<Member.OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider()); // 클라이언트 얻음
        String accessToken = client.requestAccessToken(params); // 해당 클라어인트의 accessToken 얻음
        return client.requestOauthInfo(accessToken); //accessToken을 이용하여, 사용자 정보를 받음
    }
}