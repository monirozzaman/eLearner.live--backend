package live.elearners.services;

import live.elearners.config.AuthUtil;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseSectionsService {
    private final AuthUtil authUtil;
    private final CourseSectionsRepository courseSectionsRepository;

    public ResponseEntity<CourseSectionsIdentityResponse> addNewSection(CourseSectionsRequest courseSectionsRequest, MultipartFile file) {
        String sectionId = authUtil.getRandomIntNumber();
        String destinationImagePath = "src/main/resources/images/" + file.getOriginalFilename();
        File img = new File(destinationImagePath);
        if (authUtil.getRole().endsWith("ADMIN")) {
            /*Start upload image*/
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(img));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("File Not found");
            }

            ImageDetails imageDetails = new ImageDetails();
            imageDetails.setName(file.getOriginalFilename());
            imageDetails.setType(file.getContentType());
            imageDetails.setImageUrl(img.getAbsolutePath());
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
        if (authUtil.getRole().endsWith("ADMIN")) {
            Optional<CourseSections> courseSectionsOptional = courseSectionsRepository.findById(sectionId);
            if (!courseSectionsOptional.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            /*Start image upload*/
            String destinationImagePath = "src/main/resources/images/" + file.getOriginalFilename();
            File img = new File(destinationImagePath);
            if (authUtil.getRole().endsWith("ADMIN")) {

                if (!file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        BufferedOutputStream stream =
                                new BufferedOutputStream(new FileOutputStream(img));
                        stream.write(bytes);
                        stream.close();

                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.err.println("File Not found");
                }
                try {
                    CourseSections courseSections = courseSectionsOptional.get();
                    Files.delete(Paths.get(courseSections.getImageDetails().getImageUrl()));
                    courseSections.getImageDetails().setImageUrl(img.getAbsolutePath());
                    courseSections.setSectionName(courseSectionsRequest.getSectionName());
                    courseSectionsRepository.save(courseSections);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
                /*End image upload*/
            }
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
            CourseSections courseSections = courseSectionsOptional.get();
            try {
                Files.delete(Paths.get(courseSections.getImageDetails().getImageUrl()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
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
