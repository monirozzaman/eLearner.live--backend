package live.elearners.domain.repository;

import live.elearners.domain.model.CourseClassTimeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseClassTimeScheduleRepository extends JpaRepository<CourseClassTimeSchedule, Long> {
}
