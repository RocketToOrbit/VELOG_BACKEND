package xnova.velog.WEB;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xnova.velog.DOMAIN.auth.service.OAuthLoginService;
import xnova.velog.DOMAIN.auth.jwt.AuthTokens;
import xnova.velog.DOMAIN.auth.oauth2.KakaoLoginParams;
import xnova.velog.DOMAIN.auth.oauth2.NaverLoginParams;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PostMapping("/naver")
    public ResponseEntity<AuthTokens> loginNaver(@RequestBody NaverLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}