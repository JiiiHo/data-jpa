package study.datapa.repository;

import org.springframework.stereotype.Repository;
import study.datapa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
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
        return Optional.ofNullable(member);
    }

    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from Member m" +
                " where m.age = :age" +
                " order by m.username desc ", Member.class )
                .setParameter("age", age)
                .setFirstResult(offset) //처음
                .setMaxResults(limit)//갯수
                .getResultList();
    }

    public long totalCount(int age) {
        return em.createQuery("select count(m)" +
                " from Member m" +
                " where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }
}
