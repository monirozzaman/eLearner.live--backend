package live.elearners.domain.repository;

import live.elearners.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    @Query(value = "SELECT * FROM course u WHERE u.course_section_id = ?1", nativeQuery = true)
    Optional<List<Course>> findCourseByCourseSectionId(String courseSectionId);

    @Query(value = "SELECT * FROM course u WHERE u.course_instructor_id = ?1", nativeQuery = true)
    Optional<List<Course>> findCourseByInstructorId(String instructorId);
}
