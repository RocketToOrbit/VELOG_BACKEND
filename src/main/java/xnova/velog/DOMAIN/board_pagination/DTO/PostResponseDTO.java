package xnova.velog.DOMAIN.board_pagination.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO<T> {
    private String status;
    private String message;
    private T data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PageResponseDTO<T> {
        private PaginationDTO pagination;
        private SortDTO sort;
        private List<T> content;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class PaginationDTO {
            private int totalPages;
            private long totalElements;
            private int currentPage;
            private int pageSize;
            private boolean isFirstPage;
            private boolean isLastPage;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class SortDTO {
            private boolean isSorted;
            private boolean isUnsorted;
            private boolean isEmpty;
        }
    }
}
