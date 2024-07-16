package xnova.velog.DOMAIN.board_pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import xnova.velog.Entity.Post;

public interface BoardRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
}