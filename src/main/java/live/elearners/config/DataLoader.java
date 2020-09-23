package live.elearners.config;

import live.elearners.domain.model.Admin;
import live.elearners.domain.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private AdminRepository adminRepository;

    @Autowired
    public DataLoader(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void run(ApplicationArguments args) {
        Admin admin = new Admin();
        admin.setIsEmailVerified(true);
        admin.setAdminId("258921");
        admin.setEmail("elearner.live@gmail.com");
        admin.setName("eLearners.live-Admin");
        admin.setPhoneNo("01988841890");
        admin.setAuthUuid("258921");
        adminRepository.save(admin);
    }
}
