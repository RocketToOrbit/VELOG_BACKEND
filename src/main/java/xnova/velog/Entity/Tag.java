package xnova.velog.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 추가된 어노테이션
@AllArgsConstructor // 추가된 어노테이션
@Builder
@Getter
@Entity
@Table(name = "tag", uniqueConstraints = {@UniqueConstraint(columnNames = {"tagName"})})
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tagName")
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    /*@Builder
    public Tag(Long id, String tagName, Post post) {
        this.id = id;
        this.tagName = tagName;
        this.post = post;
    }*/
}
