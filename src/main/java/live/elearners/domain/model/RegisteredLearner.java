package live.elearners.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long id;
    @Column(unique = true)
    private String learnerId;
    private String paymentDateAndTime;
    private String paymentVerifyDateAndTime;
    private boolean isPaymentVerified;
    private String paymentMethod;
    private String paymentTrxId;
    private String paid;
    private String due;
    private String commitmentDuePaidDate;


}
