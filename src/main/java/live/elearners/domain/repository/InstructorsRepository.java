package live.elearners.domain.repository;

import live.elearners.domain.model.Instructors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, String> {
    @Query(value = "SELECT u.instructorId FROM Instructors u WHERE u.phoneNo = ?1", nativeQuery = true)
    String findIdByPhoneNoNative(String phoneNumber);
}
