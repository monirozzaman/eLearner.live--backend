package live.elearners.client.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginClientRequest {

    private String username;
    private String password;

}
