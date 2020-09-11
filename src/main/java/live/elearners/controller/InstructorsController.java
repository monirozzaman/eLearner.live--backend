package live.elearners.controller;

import live.elearners.services.AuthService;
import live.elearners.services.InstructorsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("courses")
@CrossOrigin("*")
public class InstructorsController {
    private final AuthService authService;
    private final InstructorsService instructorsService;


}
