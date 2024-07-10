package xnova.velog.DOMAIN.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xnova.velog.DOMAIN.auth.jwt.AuthTokens;
import xnova.velog.DOMAIN.auth.jwt.AuthTokensGenerator;
import xnova.velog.DOMAIN.auth.oauth2.OAuthInfoResponse;
import xnova.velog.DOMAIN.auth.oauth2.OAuthLoginParams;
import xnova.velog.DOMAIN.auth.oauth2.RequestOAuthInfoService;
import xnova.velog.Entity.Member;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        log.info("params: {}", params);
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        log.info("oAuthInfoResponse: {}", oAuthInfoResponse);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        log.info("memberId: {}", memberId);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    @Transactional
    public Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getId();
    }
}