package live.elearners.domain.repository;

import live.elearners.domain.model.days.Thursday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThursdayRepository extends JpaRepository<Thursday, Long> {
}
