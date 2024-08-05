package xnova.velog.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    private Set<TagMapping> tagMapping = new HashSet<>();

    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}