package live.elearners.domain.repository;

import live.elearners.domain.model.Instructors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorsRepository extends JpaRepository<Instructors, String> {
}
