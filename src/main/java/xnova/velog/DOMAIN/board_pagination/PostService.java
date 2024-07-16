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

        // Pagination ���� ����
        PostResponseDTO.PageResponseDTO.PaginationDTO pagination = new PostResponseDTO.PageResponseDTO.PaginationDTO();
        pagination.setTotalPages(postPage.getTotalPages());
        pagination.setTotalElements(postPage.getTotalElements());
        pagination.setCurrentPage(postPage.getNumber());
        pagination.setPageSize(postPage.getSize());
        pagination.setFirstPage(postPage.isFirst());
        pagination.setLastPage(postPage.isLast());

        // Sort ���� ����
        PostResponseDTO.PageResponseDTO.SortDTO sort = new PostResponseDTO.PageResponseDTO.SortDTO();
        sort.setSorted(postPage.getSort().isSorted());
        sort.setUnsorted(postPage.getSort().isUnsorted());
        sort.setEmpty(postPage.getSort().isEmpty());

        // ��ü ���� DTO ����
        PostResponseDTO.PageResponseDTO<Post> responseData = new PostResponseDTO.PageResponseDTO<>();
        responseData.setPagination(pagination);
        responseData.setSort(sort);
        responseData.setContent(postPage.getContent());

        // API ���� ����
        PostResponseDTO<PostResponseDTO.PageResponseDTO<Post>> response = new PostResponseDTO<>();
        response.setStatus("success");
        response.setMessage("Pagination response retrieved successfully");
        response.setData(responseData);

        return response;
    }
}
