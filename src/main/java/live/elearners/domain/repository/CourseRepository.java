package live.elearners.domain.repository;

import live.elearners.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    @Query(value = "SELECT u.adminId FROM Users u WHERE u.phoneNo = ?1", nativeQuery = true)
    String findAdminIdByPhoneNoNative(String phoneNumber);
}
