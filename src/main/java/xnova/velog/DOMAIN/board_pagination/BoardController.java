package xnova.velog.DOMAIN.board_pagination;

import org.springframework.web.bind.annotation.*;
import xnova.velog.DOMAIN.board_pagination.DTO.BoardResponseDTO;

@RestController
public class BoardController {

    //private final CommentService commentService; // 댓글 서비스
    private final BoardService postService;

    public BoardController(BoardService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public BoardResponseDTO<BoardResponseDTO.PageResponseDTO<BoardResponseDTO.BoardSummaryDTO>> getPosts(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "30") int size) {
        return postService.getPosts(cursor, size);
    }

    /*// 게시물의 댓글 목록을 가져오는 메서드
    @GetMapping("/posts/{postId}/comments")
    public PostResponseDTO<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    // 게시물에 댓글을 추가하는 메서드
    @PostMapping("/posts/{postId}/comments")
    public PostResponseDTO<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment) {
        return commentService.addComment(postId, comment);
    }

    // 게시물의 특정 댓글을 수정하는 메서드
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public PostResponseDTO<Comment> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Comment comment) {
        return commentService.updateComment(postId, commentId, comment);
    }

    // 게시물의 특정 댓글을 삭제하는 메서드
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public PostResponseDTO<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new PostResponseDTO<>("success", "Comment deleted successfully", null);
    }*/
}
