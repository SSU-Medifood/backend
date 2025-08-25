package Mefo.server.global.firebase.dto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UUIDResponse {
    private String UUID;

    public static UUIDResponse from(String UUID){
        return UUIDResponse.builder()
                .UUID(UUID)
                .build();
    }

}