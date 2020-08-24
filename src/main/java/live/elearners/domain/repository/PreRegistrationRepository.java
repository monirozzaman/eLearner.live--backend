package live.elearners.domain.repository;

import live.elearners.domain.model.PreRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreRegistrationRepository extends JpaRepository<PreRegistration, String> {
}
