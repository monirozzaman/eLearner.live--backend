package live.elearners.dto.request;

import live.elearners.domain.model.Offers;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseRequest {
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
    private String youtubeEmbeddedLink;
    private List<CourseClassTimeScheduleRequest> courseClassTimeScheduleRequests;
    private String courseInstructorId;
    private String coursePriceInTk;
    private Offers offer;


}
