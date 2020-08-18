package live.elearners.domain.repository;

import live.elearners.domain.model.Learners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnersRepository extends JpaRepository<Learners, String> {
}
