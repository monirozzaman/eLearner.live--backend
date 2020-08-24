package live.elearners.domain.repository;

import live.elearners.domain.model.days.Saturday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaturdayRepository extends JpaRepository<Saturday, Long> {
}
