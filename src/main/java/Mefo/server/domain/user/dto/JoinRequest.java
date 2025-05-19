package Mefo.server.domain.user.dto;

import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.entity.UserRole;
import Mefo.server.domain.user.entity.UserState;
import Mefo.server.domain.userInfo.entity.UserDrink;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.entity.UserSex;
import Mefo.server.domain.userInfo.entity.UserSmoke;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class JoinRequest {
    private String email;
    private String password;

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

    public User toUserEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .userRole(UserRole.USER)
                .pushAlarm(true)
                .marketing(marketing)
                .storages(new ArrayList<>())
                .userState(UserState.ACTIVE)
                .build();
    }
    public UserInfo toUserInfoEntity(User user){
        return UserInfo.builder()
                .user(user)
                .name(name)
                .birth(birth)
                .userSex(userSex)
                .height(height)
                .weight(weight)
                .userSmoke(userSmoke)
                .userDrink(userDrink)
                .allergy(allergy)
                .userAllergyDrugList(new ArrayList<>())
                .userAllergyEtcList(new ArrayList<>())
                .userDiseaseList(new ArrayList<>())
                .build();
    }

    public void setPassword(String password){this.password = password;}
    public void setEmail(String email){this.email = email;}
}
