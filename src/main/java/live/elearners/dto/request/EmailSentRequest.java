package live.elearners.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailSentRequest {

    private List<String> to;

    private String subject;

    private String body;
}
