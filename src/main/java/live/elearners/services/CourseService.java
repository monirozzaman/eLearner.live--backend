package live.elearners.services;

import live.elearners.config.AuthUtil;
import live.elearners.config.FileStorageService;
import live.elearners.domain.model.*;
import live.elearners.domain.model.days.*;
import live.elearners.domain.repository.CourseRepository;
import live.elearners.domain.repository.CourseSectionsRepository;
import live.elearners.domain.repository.InstructorsRepository;
import live.elearners.domain.repository.LearnersRepository;
import live.elearners.dto.request.CourseClassTimeScheduleRequest;
import live.elearners.dto.request.CoursePublishRequest;
import live.elearners.dto.request.CourseRequest;
import live.elearners.dto.request.ReviewRequest;
import live.elearners.dto.response.CourseIdentityResponse;
import live.elearners.dto.response.CourseItemsResponse;
import live.elearners.dto.response.CourseResponse;
import live.elearners.dto.response.RegisteredLearnerResponse;
import live.elearners.exception.ResourseNotFoundException;
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
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().endsWith("ROLE_ADMIN")) {
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
            CourseClassTimeSchedule courseClassTimeSchedule = new CourseClassTimeSchedule();
            for (CourseClassTimeScheduleRequest courseClassTimeScheduleRequest : courseRequest.getCourseClassTimeScheduleRequests()) {
                List<Saturday> saturdays = new ArrayList<>();
                List<Sunday> sundays = new ArrayList<>();
                List<Monday> mondays = new ArrayList<>();
                List<Thursday> thursdays = new ArrayList<>();
                List<Wednesday> wednesdays = new ArrayList<>();
                List<Tuesday> tuesdays = new ArrayList<>();
                List<Friday> fridays = new ArrayList<>();
                switch (courseClassTimeScheduleRequest.getDay()) {
                    case "Saturday":
                        Saturday saturday = new Saturday();
                        saturday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                        saturday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                        saturdays.add(saturday);
                        break;
                    case "Sunday":
                        Sunday sunday = new Sunday();
                        sunday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                        sunday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                        sundays.add(sunday);
                        break;
                    case "Monday":
                        Monday monday = new Monday();
                        monday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                        monday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                        mondays.add(monday);
                        break;
                    case "Tuesday":
                        Tuesday tuesday = new Tuesday();
                        tuesday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                        tuesday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                        tuesdays.add(tuesday);
                        break;
                    case "Wednesday":
                        Wednesday wednesday = new Wednesday();
                        wednesday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                        wednesday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                        wednesdays.add(wednesday);
                        break;
                    case "Thursday":
                        Thursday thursday = new Thursday();
                        thursday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                        thursday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                        thursdays.add(thursday);
                        break;
                    case "Friday":
                        Friday friday = new Friday();
                        friday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                        friday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                        fridays.add(friday);
                        break;
                }

                courseClassTimeSchedule.setSaturdays(saturdays);
                courseClassTimeSchedule.setSundays(sundays);
                courseClassTimeSchedule.setMondays(mondays);
                courseClassTimeSchedule.setThursdays(thursdays);
                courseClassTimeSchedule.setWednesdays(wednesdays);
                courseClassTimeSchedule.setTuesdays(tuesdays);
                courseClassTimeSchedule.setFridays(fridays);
            }
            Course course = new Course();
            course.setCourseId(courseId);
            course.setCreateBy(authUtil.getLoggedUserId());
            course.setIsPublish(false);
            course.setCourseGoal(courseRequest.getCourseGoal());
            course.setCourseSectionId(courseRequest.getCourseSectionId());
            course.setCourseSectionName(courseSections.getSectionName());
            course.setCourseSectionDetails(courseSections.getSectionDetails());
            course.setCourseName(courseRequest.getCourseName());
            course.setCourseBasicDescription(courseRequest.getCourseBasicDescription());
            course.setCourseWhyDo(courseRequest.getCourseWhyDo());
            course.setCourseTotalDurationInDays(courseRequest.getCourseTotalDurationInDays());
            course.setCourseMaxNumberOfLearner(courseRequest.getCourseMaxNumberOfLearner());
            course.setCourseOrientationDate(courseRequest.getCourseOrientationDate());
            course.setCourseStartingDate(courseRequest.getCourseStartingDate());
            course.setCourseFinishingDate(courseRequest.getCourseFinishingDate());
            course.setCourseTotalDurationInDays(courseRequest.getCourseTotalDurationInDays());
            course.setCourseNumberOfClasses(courseRequest.getCourseNumberOfClasses());
            course.setCourseClassDuration(courseRequest.getCourseClassDuration());
            course.setYoutubeEmbeddedLink(courseRequest.getYoutubeEmbeddedLink());
            course.setCourseClassTimeSchedule(courseClassTimeSchedule);
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
        Page<Course> coursesPageable = courseRepository.findAll(pageable);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setPage(coursesPageable.getNumber());
        courseResponse.setSize(coursesPageable.getSize());
        courseResponse.setTotalElements(coursesPageable.getTotalElements());
        courseResponse.setTotalPages(coursesPageable.getTotalPages());
        List<CourseItemsResponse> courseItemsResponsesList = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            CourseItemsResponse courseItemsResponse = new CourseItemsResponse();

            courseItemsResponse.setCourseId(course.getCourseId());
            courseItemsResponse.setIsPublish(course.getIsPublish());
            courseItemsResponse.setCourseBasicDescription(course.getCourseBasicDescription());
            courseItemsResponse.setCourseWhyDo(course.getCourseWhyDo());
            courseItemsResponse.setCreateBy(course.getCreateBy());
            courseItemsResponse.setCourseSectionId(course.getCourseSectionId());
            courseItemsResponse.setCourseSectionName(course.getCourseSectionName());
            courseItemsResponse.setCourseSectionDetails(course.getCourseSectionDetails());
            courseItemsResponse.setCourseSectionDetails(course.getCourseSectionDetails());
            courseItemsResponse.setCourseName(course.getCourseName());
            courseItemsResponse.setCourseGoal(course.getCourseGoal());
            courseItemsResponse.setCourseMaxNumberOfLearner(course.getCourseMaxNumberOfLearner());
            courseItemsResponse.setCourseOrientationDate(course.getCourseOrientationDate());
            courseItemsResponse.setCourseStartingDate(course.getCourseStartingDate());
            courseItemsResponse.setCourseFinishingDate(course.getCourseFinishingDate());
            courseItemsResponse.setCourseTotalDurationInDays(course.getCourseTotalDurationInDays());
            courseItemsResponse.setCourseNumberOfClasses(course.getCourseNumberOfClasses());
            courseItemsResponse.setCourseClassDuration(course.getCourseClassDuration());
            courseItemsResponse.setYoutubeEmbeddedLink(course.getYoutubeEmbeddedLink());
            courseItemsResponse.setCourseClassTimeSchedule(course.getCourseClassTimeSchedule());

            courseItemsResponse.setCourseInstructorId(course.getCourseInstructorId());
            courseItemsResponse.setCourseInstructorName(course.getCourseInstructorName());
            courseItemsResponse.setCourseInstructorQualification(course.getCourseInstructorQualification());
            courseItemsResponse.setCourseInstructorPhoneNumber(course.getCourseInstructorPhoneNumber());
            courseItemsResponse.setCoursePriceInTk(course.getCoursePriceInTk());
            List<RegisteredLearnerResponse> registeredLearnerList = new ArrayList<>();
            for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                RegisteredLearnerResponse registeredLearnerResponse = new RegisteredLearnerResponse();
                registeredLearnerResponse.setLearnerId(registeredLearner.getLearnerId());
                Optional<Learners> learnersOptional = learnersRepository.findById(registeredLearner.getLearnerId());
                if (!learnersOptional.isPresent()) {
                    throw new ResourseNotFoundException("Instructor Not found");
                }
                Learners learners = learnersOptional.get();
                registeredLearnerResponse.setLearnerName(learners.getName());
                registeredLearnerResponse.setLearnerPhoneNO(learners.getPhoneNo());
                registeredLearnerResponse.setLearnerEmail(learners.getEmail());
                registeredLearnerResponse.setPaymentVerifyDateAndTime(registeredLearner.getPaymentVerifyDateAndTime());
                registeredLearnerResponse.setPaymentVerified(registeredLearner.isPaymentVerified());
                registeredLearnerResponse.setPaymentMethod(registeredLearner.getLearnerId());
                registeredLearnerResponse.setPaymentTrxId(registeredLearner.getLearnerId());
                registeredLearnerResponse.setPaid(registeredLearner.getLearnerId());
                registeredLearnerResponse.setDue(registeredLearner.getLearnerId());
                registeredLearnerResponse.setCommitmentDuePaidDate(registeredLearner.getLearnerId());
                registeredLearnerList.add(registeredLearnerResponse);
            }


            courseItemsResponse.setRegisteredLearners(registeredLearnerList);
            courseItemsResponse.setCourseReviewers(course.getCourseReviewers());
            courseItemsResponse.setImageDetails(course.getImageDetails());
            courseItemsResponse.setOffer(course.getOffer());
            courseItemsResponse.setCoursePriceInTk(course.getCoursePriceInTk());

            courseItemsResponsesList.add(courseItemsResponse);
        }
        courseResponse.setItems(courseItemsResponsesList);
        return new ResponseEntity(courseResponse, HttpStatus.OK);

    }

    public ResponseEntity<CourseItemsResponse> getCourseById(String courseId) {

        CourseItemsResponse courseItemsResponse = new CourseItemsResponse();
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (!optionalCourse.isPresent()) {

        }
        Course course = optionalCourse.get();
        courseItemsResponse.setCourseId(course.getCourseId());
        courseItemsResponse.setIsPublish(course.getIsPublish());
        courseItemsResponse.setCourseBasicDescription(course.getCourseBasicDescription());
        courseItemsResponse.setCourseWhyDo(course.getCourseWhyDo());
        courseItemsResponse.setCreateBy(course.getCreateBy());
        courseItemsResponse.setCourseSectionId(course.getCourseSectionId());
        courseItemsResponse.setCourseSectionName(course.getCourseSectionName());
        courseItemsResponse.setCourseSectionDetails(course.getCourseSectionDetails());
        courseItemsResponse.setCourseSectionDetails(course.getCourseSectionDetails());
        courseItemsResponse.setCourseName(course.getCourseName());
        courseItemsResponse.setCourseGoal(course.getCourseGoal());
        courseItemsResponse.setCourseMaxNumberOfLearner(course.getCourseMaxNumberOfLearner());
        courseItemsResponse.setCourseOrientationDate(course.getCourseOrientationDate());
        courseItemsResponse.setCourseStartingDate(course.getCourseStartingDate());
        courseItemsResponse.setCourseFinishingDate(course.getCourseFinishingDate());
        courseItemsResponse.setCourseTotalDurationInDays(course.getCourseTotalDurationInDays());
        courseItemsResponse.setCourseNumberOfClasses(course.getCourseNumberOfClasses());
        courseItemsResponse.setCourseClassDuration(course.getCourseClassDuration());
        courseItemsResponse.setYoutubeEmbeddedLink(course.getYoutubeEmbeddedLink());
        courseItemsResponse.setCourseClassTimeSchedule(course.getCourseClassTimeSchedule());
        courseItemsResponse.setCourseInstructorId(course.getCourseInstructorId());
        courseItemsResponse.setCourseInstructorName(course.getCourseInstructorName());
        courseItemsResponse.setCourseInstructorQualification(course.getCourseInstructorQualification());
        courseItemsResponse.setCourseInstructorPhoneNumber(course.getCourseInstructorPhoneNumber());
        courseItemsResponse.setCoursePriceInTk(course.getCoursePriceInTk());
        List<RegisteredLearnerResponse> registeredLearnerList = new ArrayList<>();
        for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
            RegisteredLearnerResponse registeredLearnerResponse = new RegisteredLearnerResponse();
            registeredLearnerResponse.setLearnerId(registeredLearner.getLearnerId());
            Optional<Learners> learnersOptional = learnersRepository.findById(registeredLearner.getLearnerId());
            if (!learnersOptional.isPresent()) {
                throw new ResourseNotFoundException("Instructor Not found");
            }
            Learners learners = learnersOptional.get();
            registeredLearnerResponse.setLearnerName(learners.getName());
            registeredLearnerResponse.setLearnerPhoneNO(learners.getPhoneNo());
            registeredLearnerResponse.setLearnerEmail(learners.getEmail());
            registeredLearnerResponse.setPaymentVerifyDateAndTime(registeredLearner.getPaymentVerifyDateAndTime());
            registeredLearnerResponse.setPaymentVerified(registeredLearner.isPaymentVerified());
            registeredLearnerResponse.setPaymentMethod(registeredLearner.getLearnerId());
            registeredLearnerResponse.setPaymentTrxId(registeredLearner.getLearnerId());
            registeredLearnerResponse.setPaid(registeredLearner.getLearnerId());
            registeredLearnerResponse.setDue(registeredLearner.getLearnerId());
            registeredLearnerResponse.setCommitmentDuePaidDate(registeredLearner.getLearnerId());
            registeredLearnerList.add(registeredLearnerResponse);
        }


        courseItemsResponse.setRegisteredLearners(registeredLearnerList);
        courseItemsResponse.setCourseReviewers(course.getCourseReviewers());
        courseItemsResponse.setImageDetails(course.getImageDetails());
        courseItemsResponse.setOffer(course.getOffer());
        courseItemsResponse.setCoursePriceInTk(course.getCoursePriceInTk());
        double courseFeeWithCurrentOffer = Double.valueOf(course.getCoursePriceInTk()) -(Double.valueOf(course.getCoursePriceInTk())*Double.valueOf(course.getOffer().getSpecialOfferInPercentage()))/100;
        courseItemsResponse.setCoursePriceInTkWithOffer(String.valueOf(courseFeeWithCurrentOffer));
        return new ResponseEntity(courseItemsResponse, HttpStatus.OK);

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

                CourseClassTimeSchedule courseClassTimeSchedule = new CourseClassTimeSchedule();
                for (CourseClassTimeScheduleRequest courseClassTimeScheduleRequest : courseUpdateRequest.getCourseClassTimeScheduleRequests()) {
                    List<Saturday> saturdays = new ArrayList<>();
                    List<Sunday> sundays = new ArrayList<>();
                    List<Monday> mondays = new ArrayList<>();
                    List<Thursday> thursdays = new ArrayList<>();
                    List<Wednesday> wednesdays = new ArrayList<>();
                    List<Tuesday> tuesdays = new ArrayList<>();
                    List<Friday> fridays = new ArrayList<>();
                    switch (courseClassTimeScheduleRequest.getDay()) {
                        case "Saturday":
                            Saturday saturday = new Saturday();
                            saturday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                            saturday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                            saturdays.add(saturday);
                            break;
                        case "Sunday":
                            Sunday sunday = new Sunday();
                            sunday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                            sunday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                            sundays.add(sunday);
                            break;
                        case "Monday":
                            Monday monday = new Monday();
                            monday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                            monday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                            mondays.add(monday);
                            break;
                        case "Tuesday":
                            Tuesday tuesday = new Tuesday();
                            tuesday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                            tuesday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                            tuesdays.add(tuesday);
                            break;
                        case "Wednesday":
                            Wednesday wednesday = new Wednesday();
                            wednesday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                            wednesday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                            wednesdays.add(wednesday);
                            break;
                        case "Thursday":
                            Thursday thursday = new Thursday();
                            thursday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                            thursday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                            thursdays.add(thursday);
                            break;
                        case "Friday":
                            Friday friday = new Friday();
                            friday.setStartTime(courseClassTimeScheduleRequest.getStart_time());
                            friday.setEndTime(courseClassTimeScheduleRequest.getEnd_time());
                            fridays.add(friday);
                            break;
                    }

                    courseClassTimeSchedule.setSaturdays(saturdays);
                    courseClassTimeSchedule.setSundays(sundays);
                    courseClassTimeSchedule.setMondays(mondays);
                    courseClassTimeSchedule.setThursdays(thursdays);
                    courseClassTimeSchedule.setWednesdays(wednesdays);
                    courseClassTimeSchedule.setTuesdays(tuesdays);
                    courseClassTimeSchedule.setFridays(fridays);
                }
                course.setCreateBy(authUtil.getLoggedUserId());
                course.setIsPublish(false);
                course.setCourseSectionId(courseUpdateRequest.getCourseSectionId());
                course.setCourseSectionName(courseSections.getSectionName());
                course.setCourseSectionDetails(courseSections.getSectionDetails());
                course.setCourseName(courseUpdateRequest.getCourseName());
                course.setCourseBasicDescription(courseUpdateRequest.getCourseBasicDescription());
                course.setCourseWhyDo(courseUpdateRequest.getCourseWhyDo());
                course.setCourseGoal(courseUpdateRequest.getCourseGoal());
                course.setCourseMaxNumberOfLearner(courseUpdateRequest.getCourseMaxNumberOfLearner());
                course.setCourseOrientationDate(courseUpdateRequest.getCourseOrientationDate());
                course.setCourseStartingDate(courseUpdateRequest.getCourseStartingDate());
                course.setCourseFinishingDate(courseUpdateRequest.getCourseFinishingDate());
                course.setCourseTotalDurationInDays(courseUpdateRequest.getCourseTotalDurationInDays());
                course.setCourseNumberOfClasses(courseUpdateRequest.getCourseNumberOfClasses());
                course.setCourseClassDuration(courseUpdateRequest.getCourseClassDuration());
                course.setYoutubeEmbeddedLink(courseUpdateRequest.getYoutubeEmbeddedLink());
                course.setCourseClassTimeSchedule(courseClassTimeSchedule);
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
        if (authUtil.getRole().equals("ADMIN") || authUtil.getRole().equals("ROLE_ADMIN")) {
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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access deny for logged role");

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


    public ResponseEntity<List<CourseResponse>> getCourseBySectionId(String courseSectionId, Pageable pageable) {


        Page<Course> coursesPageable = courseRepository.findAll(pageable);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setPage(coursesPageable.getNumber());
        courseResponse.setSize(coursesPageable.getSize());
        courseResponse.setTotalElements(coursesPageable.getTotalElements());
        courseResponse.setTotalPages(coursesPageable.getTotalPages());
        List<CourseItemsResponse> courseItemsResponsesList = new ArrayList<>();
        Optional<List<Course>> optionalCourseList = courseRepository.findCourseByCourseSectionId(courseSectionId);
        if (!optionalCourseList.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courses Not Found");
        }
        for (Course course : optionalCourseList.get()) {
            CourseItemsResponse courseItemsResponse = new CourseItemsResponse();

            courseItemsResponse.setCourseId(course.getCourseId());
            courseItemsResponse.setIsPublish(course.getIsPublish());
            courseItemsResponse.setCourseBasicDescription(course.getCourseBasicDescription());
            courseItemsResponse.setCourseWhyDo(course.getCourseWhyDo());
            courseItemsResponse.setCreateBy(course.getCreateBy());
            courseItemsResponse.setCourseSectionId(course.getCourseSectionId());
            courseItemsResponse.setCourseSectionName(course.getCourseSectionName());
            courseItemsResponse.setCourseSectionDetails(course.getCourseSectionDetails());
            courseItemsResponse.setCourseSectionDetails(course.getCourseSectionDetails());
            courseItemsResponse.setCourseName(course.getCourseName());
            courseItemsResponse.setCourseGoal(course.getCourseGoal());
            courseItemsResponse.setCourseMaxNumberOfLearner(course.getCourseMaxNumberOfLearner());
            courseItemsResponse.setCourseOrientationDate(course.getCourseOrientationDate());
            courseItemsResponse.setCourseStartingDate(course.getCourseStartingDate());
            courseItemsResponse.setCourseFinishingDate(course.getCourseFinishingDate());
            courseItemsResponse.setCourseTotalDurationInDays(course.getCourseTotalDurationInDays());
            courseItemsResponse.setCourseNumberOfClasses(course.getCourseNumberOfClasses());
            courseItemsResponse.setCourseClassDuration(course.getCourseClassDuration());
            courseItemsResponse.setYoutubeEmbeddedLink(course.getYoutubeEmbeddedLink());
            courseItemsResponse.setCourseClassTimeSchedule(course.getCourseClassTimeSchedule());

            courseItemsResponse.setCourseInstructorId(course.getCourseInstructorId());
            courseItemsResponse.setCourseInstructorName(course.getCourseInstructorName());
            courseItemsResponse.setCourseInstructorQualification(course.getCourseInstructorQualification());
            courseItemsResponse.setCourseInstructorPhoneNumber(course.getCourseInstructorPhoneNumber());
            courseItemsResponse.setCoursePriceInTk(course.getCoursePriceInTk());
            List<RegisteredLearnerResponse> registeredLearnerList = new ArrayList<>();
            for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                RegisteredLearnerResponse registeredLearnerResponse = new RegisteredLearnerResponse();
                registeredLearnerResponse.setLearnerId(registeredLearner.getLearnerId());
                Optional<Learners> learnersOptional = learnersRepository.findById(registeredLearner.getLearnerId());
                if (!learnersOptional.isPresent()) {
                    throw new ResourseNotFoundException("Instructor Not found");
                }
                Learners learners = learnersOptional.get();
                registeredLearnerResponse.setLearnerName(learners.getName());
                registeredLearnerResponse.setLearnerPhoneNO(learners.getPhoneNo());
                registeredLearnerResponse.setLearnerEmail(learners.getEmail());
                registeredLearnerResponse.setPaymentVerifyDateAndTime(registeredLearner.getPaymentVerifyDateAndTime());
                registeredLearnerResponse.setPaymentVerified(registeredLearner.isPaymentVerified());
                registeredLearnerResponse.setPaymentMethod(registeredLearner.getLearnerId());
                registeredLearnerResponse.setPaymentTrxId(registeredLearner.getLearnerId());
                registeredLearnerResponse.setPaid(registeredLearner.getLearnerId());
                registeredLearnerResponse.setDue(registeredLearner.getLearnerId());
                registeredLearnerResponse.setCommitmentDuePaidDate(registeredLearner.getLearnerId());
                registeredLearnerList.add(registeredLearnerResponse);
            }


            courseItemsResponse.setRegisteredLearners(registeredLearnerList);
            courseItemsResponse.setCourseReviewers(course.getCourseReviewers());
            courseItemsResponse.setImageDetails(course.getImageDetails());
            courseItemsResponse.setOffer(course.getOffer());

            courseItemsResponse.setCoursePriceInTk(course.getCoursePriceInTk());

            double courseFeeWithCurrentOffer = Double.valueOf(course.getCoursePriceInTk()) -(Double.valueOf(course.getCoursePriceInTk())*Double.valueOf(course.getOffer().getSpecialOfferInPercentage()))/100;
            courseItemsResponse.setCoursePriceInTkWithOffer(String.valueOf(courseFeeWithCurrentOffer));
            courseItemsResponsesList.add(courseItemsResponse);
        }
        courseResponse.setItems(courseItemsResponsesList);
        return new ResponseEntity(courseResponse, HttpStatus.OK);


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
        List<CourseItemsResponse> courseItemsResponsesList = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (course.getIsPublish()) {
                CourseItemsResponse courseItemsResponse = new CourseItemsResponse();

                courseItemsResponse.setCourseId(course.getCourseId());
                courseItemsResponse.setIsPublish(course.getIsPublish());
                courseItemsResponse.setCourseBasicDescription(course.getCourseBasicDescription());
                courseItemsResponse.setCourseWhyDo(course.getCourseWhyDo());
                courseItemsResponse.setCreateBy(course.getCreateBy());
                courseItemsResponse.setCourseSectionId(course.getCourseSectionId());
                courseItemsResponse.setCourseSectionName(course.getCourseSectionName());
                courseItemsResponse.setCourseSectionDetails(course.getCourseSectionDetails());
                courseItemsResponse.setCourseSectionDetails(course.getCourseSectionDetails());
                courseItemsResponse.setCourseName(course.getCourseName());
                courseItemsResponse.setCourseGoal(course.getCourseGoal());
                courseItemsResponse.setCourseMaxNumberOfLearner(course.getCourseMaxNumberOfLearner());
                courseItemsResponse.setCourseOrientationDate(course.getCourseOrientationDate());
                courseItemsResponse.setCourseStartingDate(course.getCourseStartingDate());
                courseItemsResponse.setCourseFinishingDate(course.getCourseFinishingDate());
                courseItemsResponse.setCourseTotalDurationInDays(course.getCourseTotalDurationInDays());
                courseItemsResponse.setCourseNumberOfClasses(course.getCourseNumberOfClasses());
                courseItemsResponse.setCourseClassDuration(course.getCourseClassDuration());
                courseItemsResponse.setYoutubeEmbeddedLink(course.getYoutubeEmbeddedLink());
                courseItemsResponse.setCourseClassTimeSchedule(course.getCourseClassTimeSchedule());
                courseItemsResponse.setCourseInstructorId(course.getCourseInstructorId());
                courseItemsResponse.setCourseInstructorName(course.getCourseInstructorName());
                courseItemsResponse.setCourseInstructorQualification(course.getCourseInstructorQualification());
                courseItemsResponse.setCourseInstructorPhoneNumber(course.getCourseInstructorPhoneNumber());
                courseItemsResponse.setCoursePriceInTk(course.getCoursePriceInTk());
                List<RegisteredLearnerResponse> registeredLearnerList = new ArrayList<>();
                for (RegisteredLearner registeredLearner : course.getRegisteredLearners()) {
                    RegisteredLearnerResponse registeredLearnerResponse = new RegisteredLearnerResponse();
                    registeredLearnerResponse.setLearnerId(registeredLearner.getLearnerId());
                    Optional<Learners> learnersOptional = learnersRepository.findById(registeredLearner.getLearnerId());
                    if (!learnersOptional.isPresent()) {
                        throw new ResourseNotFoundException("Instructor Not found");
                    }
                    Learners learners = learnersOptional.get();
                    registeredLearnerResponse.setLearnerName(learners.getName());
                    registeredLearnerResponse.setLearnerPhoneNO(learners.getPhoneNo());
                    registeredLearnerResponse.setLearnerEmail(learners.getEmail());
                    registeredLearnerResponse.setPaymentVerifyDateAndTime(registeredLearner.getPaymentVerifyDateAndTime());
                    registeredLearnerResponse.setPaymentVerified(registeredLearner.isPaymentVerified());
                    registeredLearnerResponse.setPaymentMethod(registeredLearner.getLearnerId());
                    registeredLearnerResponse.setPaymentTrxId(registeredLearner.getLearnerId());
                    registeredLearnerResponse.setPaid(registeredLearner.getLearnerId());
                    registeredLearnerResponse.setDue(registeredLearner.getLearnerId());
                    registeredLearnerResponse.setCommitmentDuePaidDate(registeredLearner.getLearnerId());
                    registeredLearnerList.add(registeredLearnerResponse);
                }


                courseItemsResponse.setRegisteredLearners(registeredLearnerList);
                courseItemsResponse.setCourseReviewers(course.getCourseReviewers());
                courseItemsResponse.setImageDetails(course.getImageDetails());
                courseItemsResponse.setOffer(course.getOffer());
                courseItemsResponse.setCoursePriceInTk(course.getCoursePriceInTk());
                double courseFeeWithCurrentOffer = Double.valueOf(course.getCoursePriceInTk()) -(Double.valueOf(course.getCoursePriceInTk())*Double.valueOf(course.getOffer().getSpecialOfferInPercentage()))/100;
                courseItemsResponse.setCoursePriceInTkWithOffer(String.valueOf(courseFeeWithCurrentOffer));
                courseItemsResponsesList.add(courseItemsResponse);
            }
        }
        courseResponse.setItems(courseItemsResponsesList);
        return new ResponseEntity(courseResponse, HttpStatus.OK);

    }
}
