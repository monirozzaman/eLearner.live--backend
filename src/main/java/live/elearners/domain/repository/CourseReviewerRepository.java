package live.elearners.domain.repository;

import live.elearners.domain.model.CourseReviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReviewerRepository extends JpaRepository<CourseReviewer, Long> {
}
