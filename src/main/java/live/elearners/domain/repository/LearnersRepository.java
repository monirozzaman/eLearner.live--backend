package live.elearners.domain.repository;

import live.elearners.domain.model.Learners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnersRepository extends JpaRepository<Learners, String> {
    @Query(value = "SELECT u FROM Learners u WHERE u.email = :email")
    Learners findIdByEmailNative(@Param("email") String email);
}
