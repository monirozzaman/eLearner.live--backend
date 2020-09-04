package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.CourseSections;
import live.elearners.domain.repository.CourseSectionsRepository;
import live.elearners.dto.request.CourseSectionsRequest;
import live.elearners.dto.response.CourseSectionsIdentityResponse;
import live.elearners.dto.response.CourseSectionsResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


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

    public ResponseEntity<Void> updateBySectionId(String sectionId, CourseSectionsRequest courseSectionsRequest) {
        if (authUtil.getRole().endsWith("ADMIN")) {
            Optional<CourseSections> courseSectionsOptional = courseSectionsRepository.findById(sectionId);
            if (!courseSectionsOptional.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            CourseSections courseSections = courseSectionsOptional.get();
            courseSections.setSectionName(courseSectionsRequest.getSectionName());
            courseSectionsRepository.save(courseSections);
            return new ResponseEntity(new CourseSectionsIdentityResponse(sectionId), HttpStatus.OK);
        } else {
            return new ResponseEntity("Access Deny", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Void> deleteBySectionId(String sectionId) {
        if (authUtil.getRole().endsWith("ADMIN")) {
            Optional<CourseSections> courseSectionsOptional = courseSectionsRepository.findById(sectionId);
            if (!courseSectionsOptional.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            courseSectionsRepository.deleteById(sectionId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity("Access Deny", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<CourseSectionsResponse> getSections(Pageable pageable) {
        Page<CourseSections> courseSectionsPage = courseSectionsRepository.findAll(pageable);
        CourseSectionsResponse courseSectionsResponse = new CourseSectionsResponse();
        courseSectionsResponse.setPage(courseSectionsPage.getNumber());
        courseSectionsResponse.setSize(courseSectionsPage.getSize());
        courseSectionsResponse.setTotalElements(courseSectionsPage.getTotalElements());
        courseSectionsResponse.setTotalPages(courseSectionsPage.getTotalPages());

        courseSectionsResponse.setSections(courseSectionsRepository.findAll());


        return new ResponseEntity(courseSectionsResponse, HttpStatus.OK);
    }
}
