package xnova.velog.DOMAIN.post;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;
    private String tagName;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String tagName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String tagName;
    }
}
