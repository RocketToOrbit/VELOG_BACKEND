package xnova.velog.DOMAIN.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xnova.velog.Entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying //필수로 있어야 함
    @Query(value = "update Post p set p.hits = p.hits+1 " +
            "where p.postId = :id")
    void updateHits(@Param("id") Long id);

    List<Post> findByStatus(String status);
}
