package live.elearners.domain.repository;

import live.elearners.domain.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    @Query(value = "SELECT u FROM Admin u WHERE u.phoneNo = :phoneNumber")
    Admin findAdminIdByPhoneNoNative(@Param("phoneNumber") String phoneNumber);
}
