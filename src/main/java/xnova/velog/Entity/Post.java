package xnova.velog.Entity;

import jakarta.persistence.*;
import lombok.*;
import xnova.velog.DOMAIN.post.PostDTO;
import xnova.velog.DOMAIN.post.TagDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor //기본 생성자를 자동으로 생성
@AllArgsConstructor //모든 필드를 인수로 가지는 생성자 자동 생성
//모든 필드를 초기화하는 생성자가 필요할 경우 사용
@Builder(toBuilder = true)
//빌더 패턴은 객체 생성 시 필수값과 선택값을 분리하여 객체를 유연하게 생성할 수 있게 함.
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 게시물 작성자

    @Column(nullable = false)
    private String title;

    @Column
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private int likes;

    @Column
    private int hits;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    // 게시물에 달린 댓글 목록
    // cascade = CascadeType.ALL: 게시물 엔티티에 대한 모든 작업(삽입, 업데이트, 삭제 등)이 댓글 엔티티에도 전파됨.
    // orphanRemoval = true: 게시물 엔티티와의 관계가 끊어진 댓글 엔티티는 자동으로 삭제됨.

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    @Column(nullable = false)
    private String boardTitle; // 게시판 제목

    @Builder
    public Post(Long postId, String title, String imageUrl,
                String content, int likes, int hits,
                String status, Member member, List<Tag> tags) {
        this.postId = postId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
        this.likes = likes;
        this.hits = hits;
        this.status = status;
        this.member = member;
        if (tags != null) {
            this.tags = tags;
        }
    }

    public void addTag(Tag tag) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tag);
    }
}
