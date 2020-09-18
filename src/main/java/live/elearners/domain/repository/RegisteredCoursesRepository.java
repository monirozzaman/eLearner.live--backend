package live.elearners.domain.repository;

import live.elearners.domain.model.RegisteredCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredCoursesRepository extends JpaRepository<RegisteredCourses, String> {

}
