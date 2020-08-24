package live.elearners.domain.repository;

import live.elearners.domain.model.days.Wednesday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WendesdayRepository extends JpaRepository<Wednesday, Long> {
}
