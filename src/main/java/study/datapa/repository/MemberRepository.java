package study.datapa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datapa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
