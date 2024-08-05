package xnova.velog.DOMAIN.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xnova.velog.DOMAIN.auth.MemberRepository;
import xnova.velog.DOMAIN.auth.jwt.AuthTokens;
import xnova.velog.DOMAIN.auth.jwt.AuthTokensGenerator;
import xnova.velog.DOMAIN.auth.jwt.JwtTokenProvider;
import xnova.velog.DOMAIN.auth.oauth2.*;
import xnova.velog.Entity.Member;

@Slf4j
@Service
@RequiredArgsConstructor
// 사용자가 OAuth를 통해 로그인할 때 필요한 정보를 처리하고,새로운 사용자를 생성하거나 기존 사용자를 찾는 등의 작업을 수행.
// 로그인에 성공하면 JWT 토큰을 생성하여 반환
public class OAuthLoginService {
    private final MemberRepository memberRepository; // 회원 정보를 관리하는 저장소
    private final AuthTokensGenerator authTokensGenerator; // AuthTokens을 얻음
    private final RequestOAuthInfoService requestOAuthInfoService; //사용자 정보를 얻음
    private final KakaoApiClient kakaoApiClient;
    private final NaverApiClient naverApiClient;
    private final JwtTokenProvider jwtTokenProvider;

    //
    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params); // OAuth 제공자로부터 사용자 정보를 요청
        Long memberId = findOrCreateMember(oAuthInfoResponse); // 사용자 정보를 기반으로 새로운 사용자를 생성하거나 기존 사용자를 찾음
        return authTokensGenerator.generate(memberId); // JWT 토큰을 생성하여 반환
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail()) // 이메일로 사용자 찾기
                .map(Member::getId) // 사용자가 존재하면 해당 사용자의 ID를 반환
                .orElseGet(() -> newMember(oAuthInfoResponse)); // 사용자가 존재하지 않으면 새로운 사용자를 생성하고, 생성된 사용자의 ID를 반환
    }

    @Transactional
    public Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        log.info("새로운 회원 생성 시도");
        try {
            Member member = Member.builder() // 새로운 Member 객체를 빌더 패턴을 사용하여 생성
                    .email(oAuthInfoResponse.getEmail())
                    .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                    .build();

            Member savedMember = memberRepository.save(member);
            return savedMember.getId(); // 새로운 사용자를 데이터베이스에 저장하고, 저장된 사용자의 ID를 반환
        } catch (Exception e) {
            log.error("회원 저장 중 오류 발생", e);
            throw e;
        }
    }

    public void logout(String accessToken, Member.OAuthProvider provider) {
        jwtTokenProvider.invalidateToken(accessToken);

        if (provider == Member.OAuthProvider.KAKAO) {
            kakaoApiClient.logout(accessToken);
        }
    }
//        } else if (provider == Member.OAuthProvider.NAVER) {
//            naverApiClient.logout(accessToken);
//        }

}