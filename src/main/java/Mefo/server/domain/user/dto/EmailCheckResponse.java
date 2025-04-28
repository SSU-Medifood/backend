package Mefo.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailCheckResponse {
    private boolean available;

    public static EmailCheckResponse from(boolean check){
        boolean available;
        if(check){
            available = false;
        }
        else{
            available= true;
        }
        return EmailCheckResponse.builder()
                .available(available)
                .build();
    }
}
