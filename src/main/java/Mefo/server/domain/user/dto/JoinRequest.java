package Mefo.server.domain.user.dto;

import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.entity.UserRole;
import Mefo.server.domain.user.entity.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class JoinRequest {
    private String email;
    private String password;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .userRole(UserRole.USER)
                .pushAlarm(true)
                .marketing(true)
                .userState(UserState.ACTIVE)
                .build();
    }

    public void setPassword(String password){this.password = password;}
    public void setEmail(String email){this.email = email;}
}
