package Mefo.server.domain.userInfo.dto;

import Mefo.server.domain.userInfo.entity.UserDrink;
import Mefo.server.domain.userInfo.entity.UserSex;
import Mefo.server.domain.userInfo.entity.UserSmoke;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoRequest {
    private String name;
    private String birth;
    private boolean marketing;
    private UserSex userSex;
    private float height;
    private float weight;
    private UserSmoke userSmoke;
    private UserDrink userDrink;
    private boolean allergy;
    private List<Long> allergyDrugs;
    private List<Long> allergyEtcs;
    private List<Long> diseases;
}
