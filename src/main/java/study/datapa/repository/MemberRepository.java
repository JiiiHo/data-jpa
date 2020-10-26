package study.datapa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datapa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //
    @Query(value = "select m" +
            " from Member m" +
            " left join m.team t",
            countQuery = "select count(m.username)" +
                    " from Member m") //countQuery를 분리할 수 있다. countQuery에는 조인이 필요 없으므로 따로 쓰면 성능 향상
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // Modifying이 있어야 executeUpdate 실행 update는 db에 바로 넣어서 영속성에는 변경되지 않는다 em.flush(), em.clear()를 해야한다
                                            // 또는 clearAutomatically를 적용해야한다
    @Query("update Member m set m.age = m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m" +
            " from Member m" +
            " join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) // fetch join을 쿼리 없이 실
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m" +
            " from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUsername(@Param("username") String username);


}
