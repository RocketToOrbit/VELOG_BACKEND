package xnova.velog.DOMAIN.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
// OAuth 인증 과정에서 JWT를 사용하여 액세스 토큰과 리프레시 토큰을 생성
public class AuthTokensGenerator {
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final JwtTokenProvider jwtTokenProvider;

    // OAuth 토큰 생성
    public AuthTokens generate(Long memberId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME); // accessToken 만료기간을 30분 후로 설정
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME); // refreshToken 만료기간을 7일 후로 설정

        String subject = memberId.toString(); // 멤버 ID를 문자열로 변환하여 토큰의 주체로 설정
        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt); //JWT 토큰 프로바이더를 사용하여 액세스 토큰을 생성
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt); // JWT 토큰 프로바이더를 사용하여 리프레시 토큰을 생성s

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public Long extractMemberId(String accessToken) {
        return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
    }
}