package xnova.velog.DOMAIN.board_pagination.utils;

import org.springframework.data.domain.Page;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;
import xnova.velog.Entity.Post;

public class PostConverter {

    public static PostResponseDTO<PostResponseDTO.PageResponseDTO<Post>> toPostResponseDTO(Page<Post> postPage) {
        PostResponseDTO.PageResponseDTO<Post> responseData = PostResponseDTO.PageResponseDTO.<Post>builder()
                .pagination(PaginationUtils.createPagination(postPage))
                .sort(PaginationUtils.createSort(postPage))
                .content(postPage.getContent())
                .build();

        return PostResponseDTO.<PostResponseDTO.PageResponseDTO<Post>>builder()
                .status("success")
                .message("Pagination response retrieved successfully")
                .data(responseData)
                .build();
    }
}
