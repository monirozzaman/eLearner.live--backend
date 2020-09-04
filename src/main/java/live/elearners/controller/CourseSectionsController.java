package live.elearners.controller;

import live.elearners.dto.request.CourseSectionsRequest;
import live.elearners.dto.response.CourseSectionsIdentityResponse;
import live.elearners.dto.response.CourseSectionsResponse;
import live.elearners.services.AuthService;
import live.elearners.services.CourseSectionsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public ResponseEntity<CourseSectionsResponse> getSections(@PageableDefault(size = 10) Pageable pageable) {
        return courseSectionsService.getSections(pageable);

    }

    @PutMapping("/{sectionId}")
    public ResponseEntity<Void> update(HttpServletRequest httpServletRequest, @RequestBody CourseSectionsRequest courseSectionsRequest, @PathVariable String sectionId) {
        authService.pink(httpServletRequest);
        return courseSectionsService.updateBySectionId(sectionId, courseSectionsRequest);

    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<Void> update(HttpServletRequest httpServletRequest, @PathVariable String sectionId) {
        authService.pink(httpServletRequest);
        return courseSectionsService.deleteBySectionId(sectionId);

    }
}
