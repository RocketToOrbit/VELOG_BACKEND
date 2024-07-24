package xnova.velog.DOMAIN.board_pagination.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO<T> {
    private String status; // 404, 200같은 응답상태 (프론트가 요청하면 주고, 안주면 빼기~!)
    private String message; //
    private T data; // 객체를 받기위한 T, list 형식

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
