package xnova.velog.DOMAIN.board_pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xnova.velog.DOMAIN.board_pagination.DTO.BoardResponseDTO;
import xnova.velog.Entity.Post;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public BoardResponseDTO<BoardResponseDTO.PageResponseDTO<Post>> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> boardPage = boardRepository.findAll(pageable);

        // Pagination 정보 설정
        BoardResponseDTO.PageResponseDTO.PaginationDTO pagination = new BoardResponseDTO.PageResponseDTO.PaginationDTO();
        pagination.setTotalPages(boardPage.getTotalPages());
        pagination.setTotalElements(boardPage.getTotalElements());
        pagination.setCurrentPage(boardPage.getNumber());
        pagination.setPageSize(boardPage.getSize());
        pagination.setFirstPage(boardPage.isFirst());
        pagination.setLastPage(boardPage.isLast());

        // Sort 정보 설정
        BoardResponseDTO.PageResponseDTO.SortDTO sort = new BoardResponseDTO.PageResponseDTO.SortDTO();
        sort.setSorted(boardPage.getSort().isSorted());
        sort.setUnsorted(boardPage.getSort().isUnsorted());
        sort.setEmpty(boardPage.getSort().isEmpty());

        // 전체 응답 DTO 설정
        BoardResponseDTO.PageResponseDTO<Post> responseData = new BoardResponseDTO.PageResponseDTO<>();
        responseData.setPagination(pagination);
        responseData.setSort(sort);
        responseData.setContent(boardPage.getContent());

        // API 응답 설정
        BoardResponseDTO<BoardResponseDTO.PageResponseDTO<Post>> response = new BoardResponseDTO<>();
        response.setStatus("success");
        response.setMessage("Pagination response retrieved successfully");
        response.setData(responseData);

        return response;
    }
}
