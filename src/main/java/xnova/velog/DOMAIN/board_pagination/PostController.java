package xnova.velog.DOMAIN.board_pagination;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;
import xnova.velog.Entity.Post;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public PostResponseDTO<PostResponseDTO.PageResponseDTO<Post>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {
        return postService.getPosts(page, size);
    }
}
