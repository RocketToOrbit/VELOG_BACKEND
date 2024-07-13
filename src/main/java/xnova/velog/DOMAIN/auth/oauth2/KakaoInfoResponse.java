package xnova.velog.DOMAIN.auth.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import xnova.velog.Entity.Member;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
// 카카오 API로부터 받은 사용자 정보 응답을 매핑하기 위한 클래스
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true) // 응답값이 null? 무시해.
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public Member.OAuthProvider getOAuthProvider() {
        return Member.OAuthProvider.KAKAO;
    }
}