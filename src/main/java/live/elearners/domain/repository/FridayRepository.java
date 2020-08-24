package live.elearners.domain.repository;

import live.elearners.domain.model.days.Friday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridayRepository extends JpaRepository<Friday, Long> {
}
