package xnova.velog.DOMAIN.board_pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;
import xnova.velog.Entity.Post;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostResponseDTO<PostResponseDTO.PageResponseDTO<Post>> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findAll(pageable);

        // Pagination 정보 설정
        PostResponseDTO.PageResponseDTO.PaginationDTO pagination = PostResponseDTO.PageResponseDTO.PaginationDTO.builder()
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .currentPage(postPage.getNumber())
                .pageSize(postPage.getSize())
                .isFirstPage(postPage.isFirst())
                .isLastPage(postPage.isLast())
                .build();

        // Sort 정보 설정
        PostResponseDTO.PageResponseDTO.SortDTO sort = PostResponseDTO.PageResponseDTO.SortDTO.builder()
                .isSorted(postPage.getSort().isSorted())
                .isUnsorted(postPage.getSort().isUnsorted())
                .isEmpty(postPage.getSort().isEmpty())
                .build();

        // 전체 응답 DTO 설정
        PostResponseDTO.PageResponseDTO<Post> responseData = PostResponseDTO.PageResponseDTO.<Post>builder()
                .pagination(pagination)
                .sort(sort)
                .content(postPage.getContent())
                .build();

        // API 응답 설정
        PostResponseDTO<PostResponseDTO.PageResponseDTO<Post>> response = PostResponseDTO.<PostResponseDTO.PageResponseDTO<Post>>builder()
                .status("success")
                .message("Pagination response retrieved successfully")
                .data(responseData)
                .build();

        return response;
    }
}
