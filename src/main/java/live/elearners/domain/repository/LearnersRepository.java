package live.elearners.domain.repository;

import live.elearners.domain.model.Learners;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnersRepository extends JpaRepository<Learners, String> {
}
