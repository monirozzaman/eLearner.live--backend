package live.elearners.services;

import live.elearners.config.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ImagesService {

    private final FileStorageService fileStorageService;


}
