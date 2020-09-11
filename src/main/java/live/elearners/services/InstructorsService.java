package live.elearners.services;

import live.elearners.config.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class InstructorsService {
    private final AuthUtil authUtil;

}
