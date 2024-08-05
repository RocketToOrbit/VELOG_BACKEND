package xnova.velog.DOMAIN.board_pagination.utils;

import org.springframework.data.domain.Page;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;

public class PaginationUtils {

    // 페이징 정보를 생성하는 메서드
    public static <T> PostResponseDTO.PageResponseDTO.PaginationDTO createPagination(Page<T> page) {
        return PostResponseDTO.PageResponseDTO.PaginationDTO.builder()
                .totalPages(page.getTotalPages()) // 총 페이지 수
                .totalElements(page.getTotalElements()) // 총 요소 수
                .currentPage(page.getNumber()) // 현재 페이지 번호
                .pageSize(page.getSize()) // 페이지 크기
                .isFirstPage(page.isFirst()) // 첫 번째 페이지 여부
                .isLastPage(page.isLast()) // 마지막 페이지 여부
                .build();
    }

    // 정렬 정보를 생성하는 메서드
    public static <T> PostResponseDTO.PageResponseDTO.SortDTO createSort(Page<T> page) {
        return PostResponseDTO.PageResponseDTO.SortDTO.builder()
                .isSorted(page.getSort().isSorted()) // 정렬 여부
                .isUnsorted(page.getSort().isUnsorted()) // 정렬되지 않은 여부
                .isEmpty(page.getSort().isEmpty()) // 정렬 정보가 비어있는지 여부
                .build();
    }
}
