package xnova.velog.DOMAIN.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import xnova.velog.Entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String emailId);
}
