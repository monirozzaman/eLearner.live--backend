package live.elearners.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class CourseReviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;
    private String designation;
    private String email;
    private String phoneNumber;
    private String review;
    private String star;
    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Course courseReviewer;
}
