package live.elearners.domain.repository;

import live.elearners.domain.model.RegisteredLearner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredLearnerRepository extends JpaRepository<RegisteredLearner, Long> {
}
