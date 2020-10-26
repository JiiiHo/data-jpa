package study.datapa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datapa.entity.Member;
import study.datapa.entity.Team;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EntityManager em;

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

    @Test
    public void findMemberLazy() {
        //given
        Team teamA = Team.builder()
                .name("teamA")
                .build();
        Team teamB = Team.builder()
                .name("teamB")
                .build();

        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(Member.builder()
                .username("member1")
                .age(10)
                .team(teamA)
                .build());
        memberRepository.save(Member.builder()
                .username("member2")
                .age(10)
                .team(teamB)
                .build());

        em.flush();
        em.clear();

        //when

        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            System.out.println("member = " + member.getUsername());
            // N + 1
            System.out.println("member.team = " + member.getTeam().getName());
        }
    }

    @Test
    public void findMemberfetchJoin() {
        //given
        Team teamA = Team.builder()
                .name("teamA")
                .build();
        Team teamB = Team.builder()
                .name("teamB")
                .build();

        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(Member.builder()
                .username("member1")
                .age(10)
                .team(teamA)
                .build());
        memberRepository.save(Member.builder()
                .username("member2")
                .age(10)
                .team(teamB)
                .build());

        em.flush();
        em.clear();

        //when

        List<Member> all = memberRepository.findMemberFetchJoin();
        for (Member member : all) {
            System.out.println("member = " + member.getUsername());
            // N + 1
            System.out.println("member.team = " + member.getTeam().getName());
        }
    }


}