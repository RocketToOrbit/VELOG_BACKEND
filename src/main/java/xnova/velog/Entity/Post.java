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
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String title;

    @Column
    private String tag;

    @Column
    private String image;

    @Column
    private String content;

    @Column
    private int likes;

    @Column
    private int hits;

    @Column
    private String status;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}