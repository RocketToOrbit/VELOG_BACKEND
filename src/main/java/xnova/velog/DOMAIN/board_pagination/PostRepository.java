package xnova.velog.DOMAIN.board_pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xnova.velog.Entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 최신순으로 게시물 목록을 조회하는 메서드
    // 커서 기반 페이지네이션을 위해 `createdAt` 필드를 기준으로 조회합니다.
    @Query("SELECT p FROM Post p WHERE (:cursor IS NULL OR p.createAt < :cursor) ORDER BY p.createAt DESC")
    List<Post> findAllByOrderByCreateAtDesc(@Param("cursor") Long cursor, Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY p.createAt DESC")
    List<Post> findAllByOrderByCreateAtDesc(Pageable pageable);
}
