package xnova.velog.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class TagMapping extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "TagMapping_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public TagMapping(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }
}
