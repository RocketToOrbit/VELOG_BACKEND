package xnova.velog.DOMAIN.post;

import lombok.*;
import xnova.velog.Entity.Member;
import xnova.velog.Entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
//데이터를 받아올 땐 DTO로 받아옴
public class PostDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String title;
        private String imageUrl;
        private String content;
        private String status;
        private Long memberId;
        private List<TagDTO> tags;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private Long postId;
        private String title;
        private String imageUrl;
        private String content;
        private int likes;
        private int hits;
        private String status;
        private Member member;
        private List<TagDTO> tags;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response fromEntity(Post post) {
            return Response.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .imageUrl(post.getImageUrl())
                    .content(post.getContent())
                    .likes(post.getLikes())
                    .hits(post.getHits())
                    .status(post.getStatus())
                    .member(post.getMember())
                    .tags(post.getTags().stream()
                            .map(tagMapping -> new TagDTO(tagMapping.getTag().getId(), tagMapping.getTag().getTagName()))
                            .collect(Collectors.toList()))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }
}
