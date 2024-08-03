package xnova.velog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId; // 게시물 ID, 기본 키로 자동 생성

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 게시물 작성자

    @Column(nullable = false)
    private String title; // 게시물 제목

    @Column
    private String imageUrl; // 게시물 이미지 URL

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 게시물 내용, null이 될 수 없으며, TEXT 타입으로 정의

    @Column
    private int likes; // 좋아요 수

    @Column
    private int hits; // 조회 수

    @Column(nullable = false)
    private String status; // 게시물 상태 (예: ACTIVE, INACTIVE)

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    // 게시물에 달린 댓글 목록
    // cascade = CascadeType.ALL: 게시물 엔티티에 대한 모든 작업(삽입, 업데이트, 삭제 등)이 댓글 엔티티에도 전파됨.
    // orphanRemoval = true: 게시물 엔티티와의 관계가 끊어진 댓글 엔티티는 자동으로 삭제됨.

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<TagMapping> tags = new ArrayList<>(); // 게시물에 달린 태그 목록

    @Column(nullable = false)
    private String boardTitle; // 게시판 제목
}
