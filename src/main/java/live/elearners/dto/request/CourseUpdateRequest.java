package live.elearners.dto.request;

import live.elearners.domain.model.CourseClassTimeSchedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseUpdateRequest {

    private Boolean isPublish;

    private String createBy;

    private String courseSectionId;

    private String courseName;

    private String courseGoal;

    private String courseMaxNumberOfLearner;

    private String courseOrientationDate;

    private String courseStartingDate;

    private String courseFinishingDate;

    private String courseTotalDurationInDays;

    private String courseNumberOfClasses;

    private String courseClassDuration;

    private CourseClassTimeSchedule courseClassTimeSchedule;

    private String courseInstructorId;

    private String courseInstructorName;

    private String courseInstructorQualification;

    private String courseInstructorPhoneNumber;

    private String coursePriceInTk;

    private String coursePriceInOffer;

}
