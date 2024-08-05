package xnova.velog.DOMAIN.board_pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xnova.velog.Entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // PostRepository는 Post 엔티티에 특화된 리포지토리이므로 제네릭 타입을 사용하지 않습니다.

    // 최신순으로 게시물 목록을 조회하는 메서드
    @Query("SELECT p FROM Post p ORDER BY p.createAt DESC")
    Page<Post> findAllByOrderByCreateAtDesc(Pageable pageable);
}
