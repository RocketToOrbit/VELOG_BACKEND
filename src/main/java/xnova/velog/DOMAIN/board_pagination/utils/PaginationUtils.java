package xnova.velog.DOMAIN.board_pagination.utils;

import org.springframework.data.domain.Page;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;

public class PaginationUtils {

    public static <T> PostResponseDTO.PageResponseDTO.PaginationDTO createPagination(Page<T> page) {
        return PostResponseDTO.PageResponseDTO.PaginationDTO.builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .isFirstPage(page.isFirst())
                .isLastPage(page.isLast())
                .build();
    }

    public static <T> PostResponseDTO.PageResponseDTO.SortDTO createSort(Page<T> page) {
        return PostResponseDTO.PageResponseDTO.SortDTO.builder()
                .isSorted(page.getSort().isSorted())
                .isUnsorted(page.getSort().isUnsorted())
                .isEmpty(page.getSort().isEmpty())
                .build();
    }
}
