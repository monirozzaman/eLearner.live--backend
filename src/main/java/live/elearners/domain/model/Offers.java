package live.elearners.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Offers")
public class Offers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "isBasicOfferEnable")
    private boolean isBasicOfferEnable;

    @Column(name = "basicOfferInPercentage")
    private int basicOfferInPercentage;

    @Column(name = "basicOfferDetails")
    private String basicOfferDetails;

    @Column(name = "specialOfferInLowestPercentage")
    private int specialOfferInLowestPercentage;

    @Column(name = "specialOfferInHighestPercentage")
    private int specialOfferInHighestPercentage;

    @Column(name = "specialOfferIncrementPercentage")
    private int specialOfferIncrementPercentage;

    @Column(name = "specialOfferIncrementAfterTimeDuration")
    private int specialOfferIncrementAfterTimeDuration;

    @Column(name = "specialOfferDetails")
    private String specialOfferDetails;

    @Column(name = "specialOfferStatDate")
    private String specialOfferStatDate;

    @Column(name = "specialOfferEndDate")
    private String specialOfferEndDate;
}
 
