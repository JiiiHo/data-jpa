package study.datapa.repository;

import org.springframework.stereotype.Repository;
import study.datapa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        if (member == null)
            return Optional.empty();
        return Optional.of(member);
    }
}
