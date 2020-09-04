package live.elearners.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@AllArgsConstructor
@EnableJpaAuditing()
public class AuditorAwareImpl implements AuditorAware<String> {

    private final AuthUtil authUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        if (authUtil.getLoggedUserId() == null) {
            return Optional.of("unknown");
        } else {
            return Optional.of(authUtil.getLoggedUserId());
        }
    }
}
