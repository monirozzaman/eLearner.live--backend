package live.elearners.domain.model;

import live.elearners.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Audited
@Table(name = "`classes`")
public class ClassDocuments extends Auditable<String> {
    @Id
    String classId;
    private String pptNote;
    private String videoNote;
    private String classRating;
}
