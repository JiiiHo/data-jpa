package study.datapa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datapa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}