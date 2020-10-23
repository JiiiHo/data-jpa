package study.datapa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datapa.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false) // Rollback 하는 것을 막아줌
class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() throws Exception {
        Member member = Member.builder()
                .username("memberA")
                .build();
        Member save = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(save.getId()).orElseThrow(() -> new Exception());

        assertThat(findMember.getId()).isEqualTo(save.getId());
        assertThat(findMember.getUsername()).isEqualTo(save.getUsername());
        assertThat(findMember).isEqualTo(save);
    }
}