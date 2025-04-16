package Mefo.server.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PasswordRequest {
    private String email;
    private String password;
}
