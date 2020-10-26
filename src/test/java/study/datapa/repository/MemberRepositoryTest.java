package study.datapa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datapa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() throws Exception {
        Member member = Member.builder()
                .username("test")
                .build();
        Member save = memberRepository.save(member);
        Member findMember = memberRepository.findById(save.getId()).orElseThrow(() -> new Exception());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void testMember2() throws Exception {
        Member member = Member.builder()
                .username("test")
                .age(10)
                .build();
        memberRepository.save(member);
        member = Member.builder()
                .username("test2")
                .age(10)
                .build();
        memberRepository.save(member);
        member = Member.builder()
                .username("test3")
                .age(10)
                .build();
        memberRepository.save(member);
        member = Member.builder()
                .username("test4")
                .age(13)
                .build();
        memberRepository.save(member);
        member = Member.builder()
                .username("test5")
                .age(10)
                .build();
        memberRepository.save(member);
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<Member> byAge = memberRepository.findByAge(10, pageRequest);
        for (Member member1 : byAge) {
            System.out.println("member : " + member1);
        }
    }
}