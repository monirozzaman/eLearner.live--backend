package live.elearners.controller;

import live.elearners.dto.request.CourseSectionsRequest;
import live.elearners.dto.response.CourseSectionsIdentityResponse;
import live.elearners.services.AuthService;
import live.elearners.services.CourseSectionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("sections")
public class CourseSectionsController {
    private final CourseSectionsService courseSectionsService;
    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<CourseSectionsIdentityResponse> create(HttpServletRequest httpServletRequest, @RequestBody CourseSectionsRequest courseSectionsRequest) {
        authService.pink(httpServletRequest);
        return courseSectionsService.addNewSection(courseSectionsRequest);

    }
}
