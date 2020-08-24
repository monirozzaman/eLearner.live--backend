package live.elearners.domain.repository;

import live.elearners.domain.model.days.Monday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MondayRepository extends JpaRepository<Monday, Long> {
}
