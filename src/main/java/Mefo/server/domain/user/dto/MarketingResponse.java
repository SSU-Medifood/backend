package Mefo.server.domain.user.dto;

import Mefo.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarketingResponse {
    private boolean marketing;

    public static MarketingResponse from(User user){
        return MarketingResponse.builder()
                .marketing(user.isMarketing())
                .build();
    }
}
