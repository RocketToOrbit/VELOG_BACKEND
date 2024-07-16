package xnova.velog.DOMAIN.board_pagination;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xnova.velog.DOMAIN.board_pagination.DTO.BoardResponseDTO;
import xnova.velog.Entity.Post;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService postService) {
        this.boardService = postService;
    }

    @GetMapping("/posts")
    public BoardResponseDTO<BoardResponseDTO.PageResponseDTO<Post>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {
        return boardService.getPosts(page, size);
    }
}
