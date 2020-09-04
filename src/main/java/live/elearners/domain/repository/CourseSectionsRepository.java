package live.elearners.domain.repository;

import live.elearners.domain.model.CourseSections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSectionsRepository extends JpaRepository<CourseSections, String> {
}
