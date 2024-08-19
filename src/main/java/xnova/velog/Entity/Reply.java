package xnova.velog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reply")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId; // 답글 ID, 기본 키로 자동 생성

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment; // 답글이 속한 댓글

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 답글 작성자

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 답글 내용, null이 될 수 없으며, TEXT 타입으로 정의
}
