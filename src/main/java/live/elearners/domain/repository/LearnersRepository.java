package live.elearners.domain.repository;

import live.elearners.domain.model.Learners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnersRepository extends JpaRepository<Learners, String> {
    @Query(value = "SELECT u.learnerId FROM Learners u WHERE u.phoneNo = ?1", nativeQuery = true)
    String findIdByPhoneNoNative(String phoneNumber);
}
