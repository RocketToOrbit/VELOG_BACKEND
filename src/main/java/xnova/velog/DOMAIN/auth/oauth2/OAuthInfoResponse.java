package xnova.velog.DOMAIN.auth.oauth2;

import xnova.velog.Entity.Member;

public interface OAuthInfoResponse {
    String getEmail();
    Member.OAuthProvider getOAuthProvider();
}