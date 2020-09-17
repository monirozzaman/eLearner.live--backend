package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.config.FileStorageService;
import live.elearners.domain.model.*;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.CourseSectionsRepository;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.dto.request.CoursePublishRequest;
import live.elearners.dto.request.CourseRequest;
import live.elearners.dto.request.ReviewRequest;
import live.elearners.dto.response.CourseIdentityResponse;
import live.elearners.dto.response.CourseResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseService {

    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;
    private final InstructorsRepository instructorsRepository;
    private final LearnersRepository learnersRepository;
    private final CourseSectionsRepository courseSectionsRepository;
    private final FileStorageService fileStorageService;

    public ResponseEntity<CourseIdentityResponse> createCourse(CourseRequest courseRequest, MultipartFile file) {
        String courseId;
        String getCurrentDate = authUtil.getCurrentDate().replaceAll("/", "");
        Optional<Instructors> optionalInstructors = instructorsRepository.findById(courseRequest.getCourseInstructorId());
        if (!optionalInstructors.isPresent()) {
            return new ResponseEntity(new CourseIdentityResponse("Instructor not found"), HttpStatus.NOT_FOUND);
        }
        Instructors instructors = optionalInstructors.get();

        Optional<CourseSections> courseSectionsOptional = courseSectionsRepository.findById(courseRequest.getCourseSectionId());
        if (!courseSectionsOptional.isPresent()) {
            return new ResponseEntity(new CourseIdentityResponse("Course Section Not Found"), HttpStatus.NOT_FOUND);

        }
        CourseSections courseSections = courseSectionsOptional.get();

        Optional<List<Course>> optionalCourseListByCourseId = courseRepository.findCourseByCourseSectionId(courseRequest.getCourseSectionId());
        if (!optionalCourseListByCourseId.isPresent()) {
            courseId = getCurrentDate + "-" + courseRequest.getCourseSectionId() + "-" + 1;
        } else {
            List<Course> courseListByCourseId = optionalCourseListByCourseId.get();
            courseId = getCurrentDate + "-" + courseRequest.getCourseSectionId() + "-" + (courseListByCourseId.size() + 1);
        }
        /*Start upload image*/
        String fileName = null;
        String fileDownloadUri = null;
        if (authUtil.getRole().equals("ADMIN")) {
            if (!file.isEmpty()) {
                fileName = fileStorageService.storeFile(file, file.getOriginalFilename());
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

            Course course = new Course();
            course.setCourseId(courseId);
            course.setCreateBy(authUtil.getLoggedUserId());
            course.setIsPublish(false);
            course.setCourseGoal(courseRequest.getCourseGoal());
            course.setCourseSectionId(courseRequest.getCourseSectionId());
            course.setCourseSectionName(courseSections.getSectionName());
            course.setCourseSectionDetails(courseSections.getSectionDetails());
            course.setCourseName(courseRequest.getCourseName());
            course.setCourseTotalDurationInDays(courseRequest.getCourseTotalDurationInDays());
            course.setCourseMaxNumberOfLearner(courseRequest.getCourseMaxNumberOfLearner());
            course.setCourseOrientationDate(courseRequest.getCourseOrientationDate());
            course.setCourseStartingDate(courseRequest.getCourseStartingDate());
            course.setCourseFinishingDate(courseRequest.getCourseFinishingDate());
            course.setCourseTotalDurationInDays(courseRequest.getCourseTotalDurationInDays());
            course.setCourseNumberOfClasses(courseRequest.getCourseNumberOfClasses());
            course.setCourseClassDuration(courseRequest.getCourseClassDuration());
            course.setYoutubeEmbeddedLink(courseRequest.getYoutubeEmbeddedLink());
            course.setCourseClassTimeSchedule(courseRequest.getCourseClassTimeSchedule());
            course.setCourseInstructorId(courseRequest.getCourseInstructorId());
            course.setCourseInstructorName(instructors.getName());
            course.setCourseInstructorPhoneNumber(instructors.getPhoneNo());
            course.setCourseInstructorQualification(instructors.getQualificationInfo().getQualification());
            course.setCoursePriceInTk(courseRequest.getCoursePriceInTk());
            course.setOffer(courseRequest.getOffer());
            course.setImageDetails(imageDetails);
            courseRepository.save(course);


        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access deny");
        }

        return new ResponseEntity(new CourseIdentityResponse(courseId), HttpStatus.OK);
    }

    public ResponseEntity<CourseResponse> getCourse(Pageable pageable) {
        List<Course> activeCoursesList = new ArrayList<>();

        Page<Course> coursesPageable = courseRepository.findAll(pageable);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setPage(coursesPageable.getNumber());
        courseResponse.setSize(coursesPageable.getSize());
        courseResponse.setTotalElements(coursesPageable.getTotalElements());
        courseResponse.setTotalPages(coursesPageable.getTotalPages());
        for (Course course : courseRepository.findAll()) {

                activeCoursesList.add(course);

        }
        courseResponse.setItems(activeCoursesList);
        return new ResponseEntity(courseResponse, HttpStatus.OK);

    }

    public ResponseEntity<Course> getCourseById(String courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            return new ResponseEntity(optionalCourse.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
        }
    }

    public void updateCourseById(String courseId, CourseRequest courseUpdateRequest, MultipartFile file) {

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Optional<Instructors> optionalInstructors = instructorsRepository.findById(courseUpdateRequest.getCourseInstructorId());
        if (!optionalInstructors.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Instructor not found");
        }
        Instructors instructors = optionalInstructors.get();

        Optional<CourseSections> courseSectionsOptional = courseSectionsRepository.findById(courseUpdateRequest.getCourseSectionId());
        if (!courseSectionsOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Section Not Found");
        }
        CourseSections courseSections = courseSectionsOptional.get();
        Course course = optionalCourse.get();

        String fileName = null;
        String fileDownloadUri = null;
        if (optionalCourse.isPresent()) {
            if (authUtil.getRole().equals("ADMIN")) {
                if (!file.isEmpty()) {
                    fileName = fileStorageService.storeFile(file, course.getImageDetails().getName());
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


                course.setCreateBy(authUtil.getLoggedUserId());
                course.setIsPublish(false);
                course.setCourseSectionId(courseUpdateRequest.getCourseSectionId());
                course.setCourseSectionName(courseSections.getSectionName());
                course.setCourseSectionDetails(courseSections.getSectionDetails());
                course.setCourseName(courseUpdateRequest.getCourseName());
                course.setCourseGoal(courseUpdateRequest.getCourseGoal());
                course.setCourseMaxNumberOfLearner(courseUpdateRequest.getCourseMaxNumberOfLearner());
                course.setCourseOrientationDate(courseUpdateRequest.getCourseOrientationDate());
                course.setCourseStartingDate(courseUpdateRequest.getCourseStartingDate());
                course.setCourseFinishingDate(courseUpdateRequest.getCourseFinishingDate());
                course.setCourseTotalDurationInDays(courseUpdateRequest.getCourseTotalDurationInDays());
                course.setCourseNumberOfClasses(courseUpdateRequest.getCourseNumberOfClasses());
                course.setCourseClassDuration(courseUpdateRequest.getCourseClassDuration());
                course.setYoutubeEmbeddedLink(courseUpdateRequest.getYoutubeEmbeddedLink());
                course.setCourseClassTimeSchedule(courseUpdateRequest.getCourseClassTimeSchedule());
                course.setCourseInstructorId(courseUpdateRequest.getCourseInstructorId());
                course.setCourseInstructorName(instructors.getName());
                course.setCourseInstructorPhoneNumber(instructors.getPhoneNo());
                course.setCourseInstructorQualification(instructors.getQualificationInfo().getQualification());
                course.setCoursePriceInTk(courseUpdateRequest.getCoursePriceInTk());
                course.setOffer(courseUpdateRequest.getOffer());
                course.setImageDetails(imageDetails);
                courseRepository.save(course);

            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Deny");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
        }
    }

    public ResponseEntity<Void> coursePublishByCourseId(String courseId, CoursePublishRequest coursePublishRequest) {
        if (authUtil.getRole().equals("ADMIN")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (optionalCourse.isPresent()) {
                Course course = optionalCourse.get();
                course.setIsPublish(coursePublishRequest.getIsPublish());
                courseRepository.save(course);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access deny");

        }

    }

    public ResponseEntity<Void> deleteCourseById(String courseId) {
        //TODO: Need to fixing related all data delete by courseId
        if (authUtil.getRole().equals("ADMIN")) {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            courseRepository.deleteById(courseId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access deny");
        }
    }


    public ResponseEntity<List<Course>> getCourseBySectionId(String courseSectionId) {

        List<Course> activeCoursesList = new ArrayList<>();
        Optional<List<Course>> optionalCourseList = courseRepository.findCourseByCourseSectionId(courseSectionId);
        if (!optionalCourseList.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courses Not Found");
        }
        for (Course course : optionalCourseList.get()) {
            if (course.getIsPublish()) {
                activeCoursesList.add(course);
            }
        }

        return new ResponseEntity(activeCoursesList, HttpStatus.OK);

    }

    public byte[] getImageByName(String imageName, HttpServletResponse response, HttpServletRequest request) {

        InputStream in = null;
        try {
            in = new ClassPathResource("/static/images/" + imageName).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<List<Learners>> getLearnersInCourse(String courseId) {
        List<Learners> learnersList = new ArrayList<>();
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("INSTRUCTOR")) {
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {

            }
            Course course = optionalCourse.get();
            for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                Optional<Learners> optionalLearners = learnersRepository.findById(registeredLearner.getLearnerId());
                if (!optionalLearners.isPresent()) {

                }
                learnersList.add(optionalLearners.get());
            }
            return new ResponseEntity(learnersList, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Void> addNewReview(ReviewRequest reviewRequest, String courseId) {
        if (authUtil.getRole().equals("LEARNER") || authUtil.getRole().equals("ADMIN")) {

            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            Course course = optionalCourse.get();
            CourseReviewer courseReviewer = new CourseReviewer();
            courseReviewer.setName(authUtil.getLoggedUserName());
            courseReviewer.setBatch(authUtil.getLoggedUserId().substring(authUtil.getLoggedUserId().length() - 2));
            courseReviewer.setReview(reviewRequest.getReview());
            courseReviewer.setStar(reviewRequest.getStar());
            course.getCourseReviewers().add(courseReviewer);
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Void> deleteReview(String courseId, Long reviewId) {
        if (authUtil.getRole().equals("ADMIN")) {

            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (!optionalCourse.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            Course course = optionalCourse.get();
            CourseReviewer courseReviewerObject = new CourseReviewer();
            for (CourseReviewer courseReviewer : course.getCourseReviewers()) {
                System.err.println(courseReviewer.getId().equals(reviewId));
                if (courseReviewer.getId().equals(reviewId)) {
                    System.err.println(courseReviewer);
                    courseReviewerObject = courseReviewer;
                }

            }
            course.getCourseReviewers().remove(courseReviewerObject);
            courseRepository.save(course);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<CourseResponse> getPublishedCourses(Pageable pageable) {
        List<Course> activeCoursesList = new ArrayList<>();

        Page<Course> coursesPageable = courseRepository.findAll(pageable);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setPage(coursesPageable.getNumber());
        courseResponse.setSize(coursesPageable.getSize());
        courseResponse.setTotalElements(coursesPageable.getTotalElements());
        courseResponse.setTotalPages(coursesPageable.getTotalPages());
        for (Course course : courseRepository.findAll()) {
            if (course.getIsPublish()) {
                activeCoursesList.add(course);
            }
        }
        courseResponse.setItems(activeCoursesList);
        return new ResponseEntity(courseResponse, HttpStatus.OK);
    }
}
