package live.elearners.services;

import live.elearners.dto.response.CourseIdentityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    public ResponseEntity<CourseIdentityResponse> createCourse() {
        return new ResponseEntity(new CourseIdentityResponse("hjgh"), HttpStatus.OK);
    }
}
