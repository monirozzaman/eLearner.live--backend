package live.elearners.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class RegisteredLearner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String learnerId;
    private boolean isPaymentVerified;
    private String paymentMethod;
    private String paid;
    private String due;
    private String commitmentDuePaidDate;


}
