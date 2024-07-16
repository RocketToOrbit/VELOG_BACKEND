package xnova.velog.DOMAIN.auth.DTO;


import lombok.*;
import xnova.velog.Entity.Member;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개 변수로 하는 생성자
@Builder
public class MemberDTO {
    private String memberUUID;
    private String email;
    private String nickname;
    private String name;
    private String velogId;
    private String follow;
    private String follower;
    private Member.OAuthProvider oAuthProvider;

    // entity -> dto
    public static MemberDTO fromEntity(Member member) {
        return MemberDTO.builder()
                .memberUUID(member.getMemberUUID())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .velogId(member.getVelogId())
                .follow(member.getFollow())
                .follower(member.getFollower())
                .oAuthProvider(member.getOAuthProvider())
                .build();
    }
}
