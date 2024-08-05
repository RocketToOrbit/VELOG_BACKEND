package xnova.velog.DOMAIN.board_pagination.utils;

import org.springframework.data.domain.Page;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;

public class PostConverter {
    // PostConverter는 제네릭 타입을 사용하여 다양한 엔티티에 대해 재사용할 수 있도록 합니다.

    // 페이지된 엔티티 정보를 PostResponseDTO로 변환하는 메서드
    public static <T> PostResponseDTO<PostResponseDTO.PageResponseDTO<T>> toPostResponseDTO(Page<T> postPage) {
        PostResponseDTO.PageResponseDTO<T> responseData = PostResponseDTO.PageResponseDTO.<T>builder()
                .pagination(PaginationUtils.createPagination(postPage)) // 페이징 정보 설정
                .sort(PaginationUtils.createSort(postPage)) // 정렬 정보 설정
                .content(postPage.getContent()) // 엔티티 콘텐츠 설정
                .build();

        return PostResponseDTO.<PostResponseDTO.PageResponseDTO<T>>builder()
                .status("success") // 응답 상태 설정
                .message("Pagination response retrieved successfully") // 응답 메시지 설정
                .data(responseData) // 변환된 데이터 설정
                .build();
    }
}