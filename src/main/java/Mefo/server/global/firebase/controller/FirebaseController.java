package Mefo.server.global.firebase.controller;

import Mefo.server.domain.user.dto.DeviceRequest;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.global.firebase.dto.TokenRequest;
import Mefo.server.global.firebase.entity.FirebaseToken;
import Mefo.server.global.firebase.service.FirebaseService;
import Mefo.server.global.login.dto.TokenResponse;
import Mefo.server.global.response.ApiResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@Tag(name="fcm 토큰")
public class FirebaseController {
    private final FirebaseService firebaseService;
    private final UserService userService;

//    //token 저장하기
//    @Transactional
//    @PostMapping("/save")
//    @Operation(summary = "토큰 저장 또는 교체 요청하기")
//    public ApiResponse<TokenResponse> saveOrUpdateToken(Authentication authentication, @RequestBody DeviceRequest deviceRequest, @RequestBody TokenRequest tokenRequest){
//        User user = userService.getLoginUser(authentication.getName());
//        FirebaseToken fcmToken = firebaseService.saveOrUpdateToken(user, deviceRequest.getDevice(), tokenRequest.getFcmtoken());
//        return new ApiResponse<>(201, TokenResponse.from(user.getEmail(), deviceRequest.getDevice(), fcmToken.getFcmToken()));
//    }

    @Transactional
    @DeleteMapping("/delete")
    @Operation(summary = "토큰 삭제 요청하기")
    public ApiResponse<String> deleteToken(Authentication authentication, @RequestBody DeviceRequest deviceRequest){
        User user = userService.getLoginUser(authentication.getName());
        firebaseService.deleteToken(user, deviceRequest.getDevice());
        return new ApiResponse<>(204, null);
    }

    //테스트용 push 보내기
    @PostMapping("/push")
    @Operation(summary = "테스트용 푸시 알림 요청하기")
    public ApiResponse<String> sendNotification(Authentication authentication) throws FirebaseMessagingException {
        User user = userService.getLoginUser(authentication.getName());
        firebaseService.sendPushNotification(user, "test", "success");
        return new ApiResponse<>(200, "success");
    }
}
