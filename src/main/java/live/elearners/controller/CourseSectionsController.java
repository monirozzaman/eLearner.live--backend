package live.elearners.controller;


import com.google.gson.Gson;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("sections")
@CrossOrigin("*")
public class CourseSectionsController {
    private final CourseSectionsService courseSectionsService;
    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<CourseSectionsIdentityResponse> create(HttpServletRequest httpServletRequest,
                                                                 @RequestParam("courseSectionsRequestInString") String courseSectionsRequestInString,
                                                                 @RequestParam("file") MultipartFile file) {
        authService.pink(httpServletRequest);

        Gson g = new Gson();
        CourseSectionsRequest courseSectionsRequest = g.fromJson(courseSectionsRequestInString, CourseSectionsRequest.class);
        return courseSectionsService.addNewSection(courseSectionsRequest, file);

    }

    @GetMapping()
    public ResponseEntity<CourseSectionsResponse> getSections(@PageableDefault(size = 10) Pageable pageable) {
        return courseSectionsService.getSections(pageable);

    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<CourseSectionsResponse> getSectionsById(@PathVariable String sectionId) {
        return courseSectionsService.getSectionsById(sectionId);

    }

    @PutMapping("/{sectionId}")
    public ResponseEntity<Void> update(HttpServletRequest httpServletRequest,
                                       @RequestParam("courseSectionsRequestInString") String courseSectionsRequestInString,
                                       @PathVariable String sectionId,
                                       @RequestParam("file") MultipartFile file) {
        authService.pink(httpServletRequest);
        Gson g = new Gson();
        CourseSectionsRequest courseSectionsRequest = g.fromJson(courseSectionsRequestInString, CourseSectionsRequest.class);
        return courseSectionsService.updateBySectionId(sectionId, courseSectionsRequest, file);

    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<Void> update(HttpServletRequest httpServletRequest, @PathVariable String sectionId) {
        authService.pink(httpServletRequest);
        return courseSectionsService.deleteBySectionId(sectionId);

    }
}
