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
@Setter
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tagName")
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<TagMapping> tagMapping = new ArrayList<>();

    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
