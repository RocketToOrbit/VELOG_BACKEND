package xnova.velog.DOMAIN.post;

import org.springframework.data.jpa.repository.JpaRepository;
import xnova.velog.Entity.TagMapping;

public interface TagMappingRepository extends JpaRepository<TagMapping, Long> {
}
