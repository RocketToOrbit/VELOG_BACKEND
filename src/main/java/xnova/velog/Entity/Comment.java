package xnova.velog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId; // 댓글 ID, 기본 키로 자동 생성

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // 댓글이 속한 게시물

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 댓글 작성자

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 댓글 내용, null이 될 수 없으며, TEXT 타입으로 정의

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;
    // 댓글에 대한 답글 목록
    // cascade = CascadeType.ALL: 댓글 엔티티에 대한 모든 작업(삽입, 업데이트, 삭제 등)이 답글 엔티티에도 전파됨.
    // orphanRemoval = true: 댓글 엔티티와의 관계가 끊어진 답글 엔티티는 자동으로 삭제됨.
}
