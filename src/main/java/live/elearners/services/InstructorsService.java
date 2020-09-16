package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.config.FileStorageService;
import live.elearners.domain.model.ImageDetails;
import live.elearners.domain.model.Instructors;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.dto.request.InstructorUpdateRequest;
import live.elearners.exception.ForbiddenException;
import live.elearners.exception.ResourseNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;


@Service
@AllArgsConstructor
public class InstructorsService {
    private final AuthUtil authUtil;
    private final InstructorsRepository instructorsRepository;
    private final FileStorageService fileStorageService;
    ;

    public ResponseEntity<Instructors> getInstructorById(String instructorId) {
        if (authUtil.getRole().equals("INSTRUCTOR") || authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
            Optional<Instructors> optionalInstructors = instructorsRepository.findById(instructorId);
            if (!optionalInstructors.isPresent()) {
                throw new RuntimeException("Instructor Not Found");
            }
            return new ResponseEntity(optionalInstructors.get(), HttpStatus.OK);
        } else {
            throw new ForbiddenException("Access Deny");
        }

    }

    public ResponseEntity<Instructors> updateInstructorById(InstructorUpdateRequest signUpInstructorRequest, MultipartFile file) {
        System.out.println(authUtil.getRole());
        if (authUtil.getRole().equals("INSTRUCTOR")) {
            Optional<Instructors> instructorsOptional = instructorsRepository.findById(authUtil.getLoggedUserId());
            if (!instructorsOptional.isPresent()) {
                throw new ResourseNotFoundException("Instructor not found");
            }
            String fileName = null;
            String fileDownloadUri = null;
            if (!file.isEmpty()) {
                fileName = fileStorageService.storeFile(file, instructorsOptional.get().getImageDetails().getName());
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

            Instructors instructors = instructorsOptional.get();
            instructors.setName(signUpInstructorRequest.getName());
            instructors.setPhoneNo(signUpInstructorRequest.getPhoneNo());
            instructors.setCurrentAddress(signUpInstructorRequest.getCurrentAddress());
            instructors.setQualificationInfo(signUpInstructorRequest.getQualificationInfo());
            instructors.setImageDetails(imageDetails);
            instructors.setName(signUpInstructorRequest.getName());
            instructorsRepository.save(instructors);
            return new ResponseEntity(instructors, HttpStatus.OK);
        } else {
            throw new ForbiddenException("Access Deny");
        }

    }
}
