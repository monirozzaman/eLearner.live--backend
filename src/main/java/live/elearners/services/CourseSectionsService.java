package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.CourseSections;
import live.elearners.domain.repository.CourseSectionsRepository;
import live.elearners.dto.request.CourseSectionsRequest;
import live.elearners.dto.response.CourseSectionsIdentityResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CourseSectionsService {
    private final AuthUtil authUtil;
    private final CourseSectionsRepository courseSectionsRepository;

    public ResponseEntity<CourseSectionsIdentityResponse> addNewSection(CourseSectionsRequest courseSectionsRequest) {
        String sectionId = authUtil.getRandomIntNumber();
        if (authUtil.getRole().endsWith("ADMIN")) {
            CourseSections courseSections = new CourseSections();
            courseSections.setSectionId(sectionId);
            courseSections.setSectionName(courseSectionsRequest.getSectionName());
            courseSectionsRepository.save(courseSections);
            return new ResponseEntity(new CourseSectionsIdentityResponse(sectionId), HttpStatus.CREATED);
        } else {
            return new ResponseEntity("Access Deny", HttpStatus.FORBIDDEN);
        }

    }
}
