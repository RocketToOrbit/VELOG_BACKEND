package xnova.velog.DOMAIN.board_pagination.DTO;

import lombok.*;

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
            private int totalPages; // 총 페이지 수
            private long totalElements; // 총 요소 수
            private int currentPage; // 현재 페이지 번호
            private int pageSize; // 페이지 크기
            private boolean isFirstPage; // 첫 번째 페이지 여부
            private boolean isLastPage; // 마지막 페이지 여부
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class SortDTO {
            private boolean isSorted; // 정렬 여부
            private boolean isUnsorted; // 정렬되지 않은 여부
            private boolean isEmpty; // 정렬 정보가 비어있는지 여부
        }
    }
}
