package live.elearners.domain.repository;

import live.elearners.domain.model.days.Sunday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SundayRepository extends JpaRepository<Sunday, Long> {
}
