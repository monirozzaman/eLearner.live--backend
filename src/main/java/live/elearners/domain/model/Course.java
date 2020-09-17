package live.elearners.domain.model;

import live.elearners.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Audited
@Table(name = "course")
public class Course extends Auditable<String> {

    @Id
    @Column(name = "courseId")
    private String courseId;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_schedule")
    private CourseClassTimeSchedule courseClassTimeSchedule;

    private String courseInstructorId;

    private String courseInstructorName;

    private String courseInstructorQualification;

    private String courseInstructorPhoneNumber;

    private String coursePriceInTk;

    @OneToMany(targetEntity = RegisteredLearner.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "f_key", referencedColumnName = "courseId")
    private List<RegisteredLearner> registeredLearners;

    @OneToMany(targetEntity = CourseReviewer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "f_key", referencedColumnName = "courseId")
    private List<CourseReviewer> courseReviewers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image")
    private ImageDetails imageDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Offer")
    private Offers Offer;
}
