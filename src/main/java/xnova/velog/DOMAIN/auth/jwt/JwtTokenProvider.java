package xnova.velog.DOMAIN.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
// JWT(Json Web Token)를 생성하고, 이를 검증하며, 토큰에서 정보를 추출하는 기능
public class JwtTokenProvider {

    private final Key key;
    private final ConcurrentHashMap<String, Date> tokenBlacklist = new ConcurrentHashMap<>();

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    // 특정 주체(subject)와 만료 시간을 기반으로 JWT를 생성
    public String generate(String subject, Date expiredAt) {
        return Jwts.builder() // 빌더 생성
                .setSubject(subject) // JWT의 주체(subject)를 설정
                .setExpiration(expiredAt) // JWT의 만료 시간을 설정
                .signWith(key, SignatureAlgorithm.HS512) // 비밀 키와 HMAC SHA-512 알고리즘을 사용하여 JWT에 서명
                .compact(); // (헤더, 페이로드, 서명)로 만듦
    }
    // JWT 토큰에서 주체(subject)를 추출
    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken); // JWT의 클레임을 파싱
        return claims.getSubject(); // 클레임에서 주체(subject)를 추출하여 반환
    }
    // JWT 토큰을 파싱하여 클레임을 추출
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder() // JWT 파서 빌더를 생성
                    .setSigningKey(key) // 서명 검증을 위한 비밀 키를 설정
                    .build() // 파서를 빌드
                    .parseClaimsJws(accessToken) // 주어진 JWT를 파싱하고 검증
                    .getBody(); // JWT의 클레임을 추출
        } catch (ExpiredJwtException e) { // 만약 JWT가 만료되었으면 ExpiredJwtException 예외가 발생하며, 이 예외에서 클레임을 추출하여 반환
            return e.getClaims();
        }
    }
    public void invalidateToken(String accessToken) {
        Date expiration = parseClaims(accessToken).getExpiration();
        tokenBlacklist.put(accessToken, expiration);
    }
//
//    public boolean isTokenValid(String accessToken) {
//        if (tokenBlacklist.containsKey(accessToken)) {
//            Date expiration = tokenBlacklist.get(accessToken);
//            return new Date().before(expiration);
//        }
//        return true;
//    }
}
