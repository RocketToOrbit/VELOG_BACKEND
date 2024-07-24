package xnova.velog.Entity;

import jakarta.persistence.*;
import lombok.*;
import xnova.velog.DOMAIN.post.PostDTO;
import xnova.velog.DOMAIN.post.TagDTO;
import xnova.velog.DOMAIN.post.TagMappingDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor //기본 생성자를 자동으로 생성
@AllArgsConstructor //모든 필드를 인수로 가지는 생성자 자동 생성
//모든 필드를 초기화하는 생성자가 필요할 경우 사용
@Builder //빌더 패턴은 객체 생성 시 필수값과 선택값을 분리하여 객체를 유연하게 생성할 수 있게 함.
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

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

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<TagMapping> tags = new ArrayList<>();

    @Column(nullable = false)
    private String boardTitle;




    public static Post toUpdatePost(PostDTO postDTO) {
        Post post = new Post();
        post.setPostId(postDTO.getPost_id());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setLikes(postDTO.getLikes());
        post.setHits(postDTO.getHits());
        post.setStatus(postDTO.getStatus());
        post.setMember(postDTO.getMember());
        post.setTags(post.getTags());
        return post;
    }

    public static Post toPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setLikes(0);
        post.setHits(0);
        post.setStatus(postDTO.getStatus());
        post.setMember(postDTO.getMember());
        postDTO.setTags(post.getTags().stream()
                .map(tagMapping -> new TagMappingDTO(
                        new TagDTO(tagMapping.getTag().getId(), tagMapping.getTag().getTagName())))
                .collect(Collectors.toSet()));

        post.setTags(postDTO.getTags().stream()
                .map(tagMappingDTO -> {
                    Tag tag = new Tag(postDTO.getTags(), postDTO.getTags().getClass().getName());
                    return new TagMapping(post, tag);
                        }).collect(Collectors.toSet()));*/

        /*post.setTags(postDTO.getTags().stream()
                .map(tagMappingDTO -> new TagMapping(post,
                        new Tag(tagMappingDTO.getTag().getId(), tagMappingDTO.getTag().getTagName())
                )).collect(Collectors.toSet()));


        post.setImageUrl(postDTO.getImageUrl());

        post.setTags(postDTO.getTags().stream()
                .map(tagMappingDTO -> {
                    Tag tag = new Tag(tagMappingDTO.getTag().getId(), tagMappingDTO.getTag().getTagName());
                    return new TagMapping(post, tag);
                }).collect(Collectors.toList()));

        Set<TagMapping> tags = postDTO.getTags().stream()
                .map(tagMappingDTO -> {
                    Tag tag = new Tag();
                    tag.setId(tagMappingDTO.getTag().getId());
                    tag.setTagName(tagMappingDTO.getTag().getTagName());
                    return new TagMapping(post, tag);
                })
                .collect(Collectors.toSet());
        post.setTags(tags);
        return post;
    }
}
