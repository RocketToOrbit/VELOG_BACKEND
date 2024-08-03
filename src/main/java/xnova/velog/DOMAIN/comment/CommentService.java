package xnova.velog.DOMAIN.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xnova.velog.DOMAIN.board_pagination.DTO.PostResponseDTO;
import xnova.velog.DOMAIN.board_pagination.PostRepository;
import xnova.velog.Entity.Comment;
import xnova.velog.Entity.Post;

import java.util.List;

@Service
public class CommentService {
    /*
    CommentService는 Comment 엔티티에 특화된 기능을 제공하므로 제네릭 타입을 사용하지 않습니다.
    대신, PostRepository를 직접 사용하여 Post 엔티티와의 관계를 관리합니다.
     */

    @Autowired
    private CommentRepository commentRepository; // 댓글 리포지토리 주입

    @Autowired
    private PostRepository postRepository; // 게시물 리포지토리 주입

    // 특정 게시물의 댓글 목록을 가져오는 메서드
    public PostResponseDTO<List<Comment>> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostPostId(postId);
        return new PostResponseDTO<>("success", "Comments retrieved successfully", comments);
    }

    // 특정 게시물에 댓글을 추가하는 메서드
    public PostResponseDTO<Comment> addComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment newComment = Comment.builder()
                .post(post) // 댓글이 속한 게시물 설정
                .member(comment.getMember()) // 댓글 작성자 설정
                .content(comment.getContent()) // 댓글 내용 설정
                .build();
        Comment savedComment = commentRepository.save(newComment);
        return new PostResponseDTO<>("success", "Comment added successfully", savedComment);
    }

    // 특정 댓글을 수정하는 메서드
    public PostResponseDTO<Comment> updateComment(Long postId, Long commentId, Comment updatedComment) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        Comment newComment = Comment.builder()
                .commentId(existingComment.getCommentId()) // 기존 댓글 ID 유지
                .post(existingComment.getPost()) // 기존 댓글이 속한 게시물 유지
                .member(existingComment.getMember()) // 기존 댓글 작성자 유지
                .content(updatedComment.getContent()) // 새로운 댓글 내용 설정
                .build();
        Comment savedComment = commentRepository.save(newComment);
        return new PostResponseDTO<>("success", "Comment updated successfully", savedComment);
    }

    // 특정 댓글을 삭제하는 메서드
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }
}
