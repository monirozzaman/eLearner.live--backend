package live.elearners.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseClassTimeScheduleRequest {
    private String day;
    private String start_time;
    private String end_time;
}
