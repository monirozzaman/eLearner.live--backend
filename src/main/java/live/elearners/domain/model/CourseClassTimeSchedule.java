package live.elearners.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long dayId;

    @OneToMany(targetEntity = Saturday.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_key", referencedColumnName = "dayId")
    private List<Saturday> saturdays;

    @OneToMany(targetEntity = Sunday.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_key", referencedColumnName = "dayId")
    private List<Sunday> sundays;

    @OneToMany(targetEntity = Monday.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_key", referencedColumnName = "dayId")
    private List<Monday> mondays;

    @OneToMany(targetEntity = Tuesday.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_key", referencedColumnName = "dayId")
    private List<Tuesday> tuesdays;

    @OneToMany(targetEntity = Wednesday.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_key", referencedColumnName = "dayId")
    private List<Wednesday> wednesdays;

    @OneToMany(targetEntity = Thursday.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_key", referencedColumnName = "dayId")
    private List<Thursday> thursdays;

    @OneToMany(targetEntity = Friday.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_key", referencedColumnName = "dayId")
    private List<Friday> fridays;


}
