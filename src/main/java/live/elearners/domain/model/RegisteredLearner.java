package live.elearners.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.elearners.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Audited
public class RegisteredLearner extends Auditable<String> {
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
