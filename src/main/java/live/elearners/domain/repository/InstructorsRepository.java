package live.elearners.domain.repository;

import live.elearners.domain.model.Instructors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, String> {
    @Query(value = "SELECT u FROM Instructors u WHERE u.phoneNo = :phoneNumber")
    Instructors findIdByPhoneNoNative(@Param("phoneNumber") String phoneNumber);
}
