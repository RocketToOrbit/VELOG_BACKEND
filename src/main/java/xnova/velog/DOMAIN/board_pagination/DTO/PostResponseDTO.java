package xnova.velog.DOMAIN.board_pagination.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO<T> {
    private String status; // 응답 상태 (예: 성공, 실패)
    private String message; // 응답 메시지
    private T data; // 실제 데이터 (제네릭 타입)
    private Long nextCursor; // 다음 커서를 제공하기 위한 필드 (커서 기반 페이지네이션용)

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PageResponseDTO<T> {
        private PaginationDTO pagination; // 페이지네이션 정보
        private SortDTO sort; // 정렬 정보
        private List<T> content; // 페이지 콘텐츠

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class PaginationDTO {
            private long totalElements; // 총 요소 수
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class SortDTO {
            private boolean isSorted; // 정렬 여부
            private boolean isEmpty; // 데이터가 비어있는지 여부
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostSummaryDTO {
        private Long postId;
        private String title;
        private Long memberId;
        private String status;
        private LocalDateTime createAt;
    }
}
