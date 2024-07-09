package xnova.velog.DOMAIN.auth.oauth2;

import org.springframework.util.MultiValueMap;
import xnova.velog.Entity.Member;

public interface OAuthLoginParams {
    Member.OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
