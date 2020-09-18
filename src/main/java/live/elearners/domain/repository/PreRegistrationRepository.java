package live.elearners.domain.repository;

import live.elearners.domain.model.PreRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreRegistrationRepository extends JpaRepository<PreRegistration, String> {

    @Query(value = "SELECT * FROM pre_registration u WHERE u.email = ?1", nativeQuery = true)
    Optional<List<PreRegistration>> findByLearnerId(String email);


}
