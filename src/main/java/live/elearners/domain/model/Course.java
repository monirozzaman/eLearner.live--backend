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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_schedule")
    private CourseClassTimeSchedule courseClassTimeSchedule;

    private String courseInstructorId;

    private String courseInstructorName;

    private String courseInstructorQualification;

    private String courseInstructorPhoneNumber;

    private String coursePriceInTk;

    private String coursePriceInOffer;

    @OneToMany(targetEntity = RegisteredLearner.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "f_key", referencedColumnName = "courseId")
    private List<RegisteredLearner> registeredLearners;

    @OneToMany(targetEntity = CourseReviewer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "f_key", referencedColumnName = "courseId")
    private List<CourseReviewer> courseReviewers;
}
