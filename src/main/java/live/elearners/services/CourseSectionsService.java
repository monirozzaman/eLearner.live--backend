package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.config.FileStorageService;
import live.elearners.domain.model.CourseSections;
import live.elearners.domain.model.ImageDetails;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseSectionsService {
    private final AuthUtil authUtil;
    private final CourseSectionsRepository courseSectionsRepository;
    private final FileStorageService fileStorageService;

    public ResponseEntity<CourseSectionsIdentityResponse> addNewSection(CourseSectionsRequest courseSectionsRequest, MultipartFile file) {
        String sectionId = authUtil.getRandomIntNumber();
        /*Start upload image*/
        String fileName = null;
        String fileDownloadUri = null;
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
            if (!file.isEmpty()) {
                fileName = fileStorageService.storeFile(file, AuthUtil.getRandomIntNumberForImages() + file.getOriginalFilename());
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/view/")
                        .path(fileName)
                        .toUriString();
            } else {
                System.err.println("File Not found");
            }

            ImageDetails imageDetails = new ImageDetails();
            imageDetails.setName(fileName);
            imageDetails.setType(file.getContentType());
            imageDetails.setImageUrl(fileDownloadUri);
            /*End upload image*/
            CourseSections courseSections = new CourseSections();
            courseSections.setSectionId(sectionId);
            courseSections.setSectionName(courseSectionsRequest.getSectionName());
            courseSections.setSectionDetails(courseSectionsRequest.getSectionDetails());
            courseSections.setImageDetails(imageDetails);
            courseSectionsRepository.save(courseSections);
            return new ResponseEntity(new CourseSectionsIdentityResponse(sectionId), HttpStatus.CREATED);
        } else {
            return new ResponseEntity("Access Deny", HttpStatus.FORBIDDEN);
        }

    }

    public ResponseEntity<Void> updateBySectionId(String sectionId, CourseSectionsRequest courseSectionsRequest, MultipartFile file) {

        if (authUtil.getRole().endsWith("ADMIN") || authUtil.getRole().endsWith("ROLE_ADMIN")) {
            Optional<CourseSections> courseSectionsOptional = courseSectionsRepository.findById(sectionId);
            if (!courseSectionsOptional.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            String fileName = null;
            String fileDownloadUri = null;
            if (!file.isEmpty()) {
                fileName = fileStorageService.storeFile(file, courseSectionsOptional.get().getImageDetails().getName());
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/view/")
                        .path(fileName)
                        .toUriString();
            } else {
                System.err.println("File Not found");
            }

            ImageDetails imageDetails = new ImageDetails();
            imageDetails.setName(fileName);
            imageDetails.setType(file.getContentType());
            imageDetails.setImageUrl(fileDownloadUri);

            CourseSections courseSections = courseSectionsOptional.get();
            courseSections.setSectionName(courseSectionsRequest.getSectionName());
            courseSections.setSectionDetails(courseSectionsRequest.getSectionDetails());
            courseSections.setImageDetails(imageDetails);
            courseSectionsRepository.save(courseSections);

            return new ResponseEntity(new CourseSectionsIdentityResponse(sectionId), HttpStatus.OK);
        } else {
            return new ResponseEntity(new CourseSectionsIdentityResponse("Access Deny"), HttpStatus.OK);
        }
    }


    public ResponseEntity<Void> deleteBySectionId(String sectionId) {
        if (authUtil.getRole().endsWith("ADMIN") || authUtil.getRole().endsWith("ROLE_ADMIN")) {
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

    public ResponseEntity<CourseSectionsResponse> getSectionsById(String sectionId) {

        Optional<CourseSections> courseSectionsOptional = courseSectionsRepository.findById(sectionId);
        if (!courseSectionsOptional.isPresent()) {
            return new ResponseEntity(new CourseSectionsResponse(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(courseSectionsOptional.get(), HttpStatus.OK);
    }
}
