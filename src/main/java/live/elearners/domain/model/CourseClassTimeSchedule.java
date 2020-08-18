package live.elearners.domain.model;

import live.elearners.domain.model.days.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class CourseClassTimeSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dayId")
    private Long dayId;

    @OneToMany(mappedBy = "saturday")
    private List<Saturday> saturdays;

    @OneToMany(mappedBy = "sunday")
    private List<Sunday> sundays;

    @OneToMany(mappedBy = "monday")
    private List<Monday> mondays;

    @OneToMany(mappedBy = "tuesday")
    private List<Tuesday> tuesdays;

    @OneToMany(mappedBy = "wednesday")
    private List<Wednesday> wednesdays;

    @OneToMany(mappedBy = "thursday")
    private List<Thursday> thursdays;

    @OneToMany(mappedBy = "friday")
    private List<Friday> fridays;

    @OneToOne
    @MapsId
    private Course course;

}
