package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.domain.model.Course;
import live.elearners.domain.model.CourseSections;
import live.elearners.domain.model.ImageDetails;
import live.elearners.domain.model.Instructors;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.CourseSectionsRepository;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.dto.request.CoursePublishRequest;
import live.elearners.dto.request.CourseRequest;
import live.elearners.dto.response.CourseIdentityResponse;
import live.elearners.dto.response.CourseResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CourseService {

    private final AuthUtil authUtil;
    private final CourseRepository courseRepository;
    private final InstructorsRepository instructorsRepository;
    private final CourseSectionsRepository courseSectionsRepository;

    public ResponseEntity<CourseIdentityResponse> createCourse(CourseRequest courseRequest, MultipartFile file) {
        String courseId;
        String getCurrentDate = authUtil.getCurrentDate().replaceAll("/", "");
        Optional<Instructors> optionalInstructors = instructorsRepository.findById(courseRequest.getCourseInstructorId());
        if (!optionalInstructors.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Instructor not found");
        }
        Instructors instructors = optionalInstructors.get();

        Optional<CourseSections> courseSectionsOptional = courseSectionsRepository.findById(courseRequest.getCourseSectionId());
        if (!courseSectionsOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Section Not Found");
        }
        CourseSections courseSections = courseSectionsOptional.get();

        Optional<List<Course>> optionalCourseListByCourseId = courseRepository.findCourseByCourseSectionId(courseRequest.getCourseSectionId());
        if (!optionalCourseListByCourseId.isPresent()) {
            courseId = getCurrentDate + "-" + courseRequest.getCourseSectionId() + "-" + 1;
        } else {
            List<Course> courseListByCourseId = optionalCourseListByCourseId.get();
            courseId = getCurrentDate + "-" + courseRequest.getCourseSectionId() + "-" + (courseListByCourseId.size() + 1);
        }

        String destinationImagePath = "src/main/resources/images/" + file.getOriginalFilename();
        File img = new File(destinationImagePath);
        if (authUtil.getRole().equals("ADMIN")) {
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
            course.setCourseClassTimeSchedule(courseRequest.getCourseClassTimeSchedule());
            course.setCourseInstructorId(courseRequest.getCourseInstructorId());
            course.setCourseInstructorName(instructors.getName());
            course.setCourseInstructorPhoneNumber(instructors.getPhoneNo());
            course.setCourseInstructorQualification(instructors.getQualificationInfo().getQualification());
            course.setCoursePriceInTk(courseRequest.getCoursePriceInTk());
            course.setCoursePriceInOffer(courseRequest.getCoursePriceInOffer());
            course.setImageDetails(imageDetails);
            courseRepository.save(course);


        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access deny");
        }

        return new ResponseEntity(new CourseIdentityResponse(courseId), HttpStatus.OK);
    }

    public ResponseEntity<CourseResponse> getCourse(Pageable pageable) {
        Page<Course> coursesPageable = courseRepository.findAll(pageable);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setPage(coursesPageable.getNumber());
        courseResponse.setSize(coursesPageable.getSize());
        courseResponse.setTotalElements(coursesPageable.getTotalElements());
        courseResponse.setTotalPages(coursesPageable.getTotalPages());
        List<Course> courses = courseRepository.findAll();

        courseResponse.setItems(courses);
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

        String destinationImagePath = "src/main/resources/images/" + file.getOriginalFilename();
        File img = new File(destinationImagePath);

        if (optionalCourse.isPresent()) {

            if (authUtil.getRole().equals("ADMIN")) {
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
                    Course course = optionalCourse.get();
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
                    course.setCourseClassTimeSchedule(courseUpdateRequest.getCourseClassTimeSchedule());
                    course.setCourseInstructorId(courseUpdateRequest.getCourseInstructorId());
                    course.setCourseInstructorName(instructors.getName());
                    course.setCourseInstructorPhoneNumber(instructors.getPhoneNo());
                    course.setCourseInstructorQualification(instructors.getQualificationInfo().getQualification());
                    course.setCoursePriceInTk(courseUpdateRequest.getCoursePriceInTk());
                    course.setCoursePriceInOffer(courseUpdateRequest.getCoursePriceInOffer());
                    Files.delete(Paths.get(course.getImageDetails().getImageUrl()));
                    course.getImageDetails().setImageUrl(img.getAbsolutePath());
                    courseRepository.save(course);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
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
            Course course = courseOptional.get();
            try {
                Files.delete(Paths.get(course.getImageDetails().getImageUrl()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            courseRepository.deleteById(courseId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access deny");
        }
    }


    public ResponseEntity<List<Course>> getCourseBySectionId(String courseSectionId) {

        Optional<List<Course>> optionalCourseList = courseRepository.findCourseByCourseSectionId(courseSectionId);
        if (!optionalCourseList.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courses Not Found");
        }

        return new ResponseEntity(optionalCourseList.get(), HttpStatus.OK);

    }
}
