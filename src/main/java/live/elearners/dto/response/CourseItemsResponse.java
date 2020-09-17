package live.elearners.dto.response;

import live.elearners.domain.model.CourseClassTimeSchedule;
import live.elearners.domain.model.CourseReviewer;
import live.elearners.domain.model.ImageDetails;
import live.elearners.domain.model.Offers;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CourseItemsResponse {

    private String courseId;

    private String courseBasicDescription;

    private String courseWhyDo;

    private Boolean isPublish;

    private String createBy;

    private String courseSectionId;

    private String courseSectionName;

    private String courseSectionDetails;

    private String courseName;

    private String courseGoal;

    private String courseMaxNumberOfLearner;

    private String courseOrientationDate;

    private String courseStartingDate;

    private String courseFinishingDate;

    private String courseTotalDurationInDays;

    private String courseNumberOfClasses;

    private String courseClassDuration;

    private String youtubeEmbeddedLink;

    private CourseClassTimeSchedule courseClassTimeSchedule;

    private String courseInstructorId;

    private String courseInstructorName;

    private String courseInstructorQualification;

    private String courseInstructorPhoneNumber;

    private String coursePriceInTk;

    private String coursePriceInTkWithOffer;


    private List<RegisteredLearnerResponse> registeredLearners;


    private List<CourseReviewer> courseReviewers;


    private ImageDetails imageDetails;


    private Offers Offer;
}
