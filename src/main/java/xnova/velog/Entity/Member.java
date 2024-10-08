package xnova.velog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String memberUUID;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private String velogId;

    @Column
    private String follow;

    @Column
    private String follower;

    @Column
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Column
    private String password;

    public enum OAuthProvider {
        KAKAO, NAVER
    }
}
