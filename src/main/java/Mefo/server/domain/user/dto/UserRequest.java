package Mefo.server.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequest {
    private boolean pushAlarm;
    private boolean marketing;
}
