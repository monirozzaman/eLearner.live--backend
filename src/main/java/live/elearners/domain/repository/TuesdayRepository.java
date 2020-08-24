package live.elearners.domain.repository;

import live.elearners.domain.model.days.Tuesday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuesdayRepository extends JpaRepository<Tuesday, Long> {
}
