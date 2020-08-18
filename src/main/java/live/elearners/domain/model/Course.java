package live.elearners.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Course {

    @Id
    @Column(name = "courseId")
    private String courseId;

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

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private CourseClassTimeSchedule courseClassTimeSchedule;

    private String courseInstructorId;

    private String courseInstructorName;

    private String courseInstructorQualification;

    private String courseInstructorPhoneNumber;

    private String coursePriceInTk;

    private String coursePriceInOffer;

    @OneToMany(mappedBy = "registeredLearner")
    private List<RegisteredLearner> registeredLearners;

    @OneToMany(mappedBy = "courseReviewer")
    private List<CourseReviewer> courseReviewers;
}
