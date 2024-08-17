package xnova.velog.DOMAIN.board_pagination.utils;

import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;
import xnova.velog.Entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    // 커서 기반 페이지네이션에서 사용될 PostResponseDTO로 변환하는 메서드
    public static PostResponseDTO<PostResponseDTO.PageResponseDTO<PostResponseDTO.PostSummaryDTO>> toPostResponseDTO(
            List<Post> posts, Long nextCursor, long totalElements, boolean isSorted) {

        // Post -> PostSummaryDTO로 변환
        List<PostResponseDTO.PostSummaryDTO> postSummaryList = posts.stream()
                .map(post -> PostResponseDTO.PostSummaryDTO.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .memberId(post.getMember().getId())  // member_id
                        .status(post.getStatus())
                        .createAt(post.getCreateAt())
                        .build())
                .collect(Collectors.toList());

        PostResponseDTO.PageResponseDTO<PostResponseDTO.PostSummaryDTO> responseData = PostResponseDTO.PageResponseDTO.<PostResponseDTO.PostSummaryDTO>builder()
                .content(postSummaryList) // 리스트 형태의 데이터 설정
                .pagination(PostResponseDTO.PageResponseDTO.PaginationDTO.builder()
                        .totalElements(totalElements) // 총 요소 수 설정
                        .build())
                .sort(PostResponseDTO.PageResponseDTO.SortDTO.builder()
                        .isSorted(isSorted) // 정렬 여부 설정
                        .isEmpty(postSummaryList.isEmpty()) // 리스트가 비어있는지 여부 설정
                        .build())
                .build();

        return PostResponseDTO.<PostResponseDTO.PageResponseDTO<PostResponseDTO.PostSummaryDTO>>builder()
                .status("success") // 응답 상태 설정
                .message("Cursor Pagination response retrieved successfully") // 응답 메시지 설정
                .data(responseData) // 변환된 데이터 설정
                .nextCursor(nextCursor) // 다음 커서 설정
                .build();
    }
}
