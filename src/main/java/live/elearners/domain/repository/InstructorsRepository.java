package live.elearners.domain.repository;

import live.elearners.domain.model.Instructors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, String> {
    @Query(value = "SELECT u FROM instructors u WHERE u.email = :email")
    Instructors findIdByEmailNative(@Param("email") String email);
}
