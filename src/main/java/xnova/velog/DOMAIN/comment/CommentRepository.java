package xnova.velog.DOMAIN.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xnova.velog.Entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // CommentRepository는 Comment 엔티티에 특화된 리포지토리이므로 제네릭 타입을 사용하지 않습니다.
    // 특정 게시물의 댓글 목록을 조회하는 메서드
    List<Comment> findByPostPostId(Long postId);
}
