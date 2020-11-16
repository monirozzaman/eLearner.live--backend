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

    @Column(name = "specialOfferInPercentage")
    private String specialOfferInPercentage;

    @Column(name = "specialOfferReason")
    private String specialOfferReason;

    @Column(name = "specialOfferStatDate")
    private String specialOfferStatDate;

    @Column(name = "specialOfferEndDate")
    private String specialOfferEndDate;
}
 
