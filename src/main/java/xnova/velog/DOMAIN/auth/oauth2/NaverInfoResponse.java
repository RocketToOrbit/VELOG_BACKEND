package xnova.velog.DOMAIN.auth.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import xnova.velog.Entity.Member;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
    }

    @Override
    public String getEmail() {
        return response.email;
    }


    @Override
    public Member.OAuthProvider getOAuthProvider() {
        return Member.OAuthProvider.NAVER;
    }
}