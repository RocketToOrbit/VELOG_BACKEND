package xnova.velog.DOMAIN.post;

import org.springframework.data.jpa.repository.JpaRepository;
import xnova.velog.Entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);
}
