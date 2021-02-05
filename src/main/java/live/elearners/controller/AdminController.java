package live.elearners.controller;

import live.elearners.domain.model.Admin;
import live.elearners.dto.response.DashboardResponse;
import live.elearners.services.AdminService;
import live.elearners.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    private final AuthService authService;
    private final AdminService adminService;

    /*
     * GET Mapping
     * */
    @GetMapping()
    public ResponseEntity<List<Admin>> getAdminList(HttpServletRequest httpServletRequest) {
        authService.pink(httpServletRequest);
        return adminService.getAdminList();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardDetails() {
        return adminService.getDashboardDetails();
    }


}
