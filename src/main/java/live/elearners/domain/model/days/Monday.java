package live.elearners.domain.model.days;

import live.elearners.domain.model.CourseClassTimeSchedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Monday {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String startTime;
    private String endTime;
    @ManyToOne
    @JoinColumn(name = "dayId")
    private CourseClassTimeSchedule monday;
}
