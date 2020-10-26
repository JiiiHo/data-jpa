package study.datapa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.datapa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //
    @Query(value = "select m" +
            " from Member m" +
            " left join m.team t",
            countQuery = "select count(m.username)" +
                    " from Member m") //countQuery를 분리할 수 있다. countQuery에는 조인이 필요 없으므로 따로 쓰면 성능 향상
    Page<Member> findByAge(int age, Pageable pageable);
}
