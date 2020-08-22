package live.elearners.dto.request;

import live.elearners.domain.model.CourseClassTimeSchedule;
import live.elearners.domain.model.CourseReviewer;
import live.elearners.domain.model.RegisteredLearner;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
public class CourseUpdateRequest {

    private Boolean isPublish;

    private String createBy;

    private String courseType;

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

    private List<RegisteredLearner> registeredLearners;

    private List<CourseReviewer> courseReviewers;


}
