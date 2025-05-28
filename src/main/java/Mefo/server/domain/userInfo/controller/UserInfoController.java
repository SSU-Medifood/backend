package Mefo.server.domain.userInfo.controller;

import Mefo.server.domain.userInfo.dto.UserInfoPatchRequest;
import Mefo.server.domain.userInfo.dto.UserInfoResponse;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.service.UserInfoService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo")
@RequiredArgsConstructor
@Tag(name="UserInfo")
public class UserInfoController {
    private final UserInfoService userInfoService;
    //유저 건강 정보 불러오기
    @GetMapping("/get")
    @Operation(summary = "유저 건강 정보 확인하기")
    public ApiResponse<UserInfoResponse> getUserInfo(Authentication authentication){
        UserInfo userInfo = userInfoService.findByLoginUser(authentication.getName());
        return new ApiResponse<>(200, UserInfoResponse.from(userInfo));
    }

    //유저 건강 정보 수정하기
    @Transactional
    @PatchMapping("/patch")
    @Operation(summary = "유저 건강 정보 수정하기")
    public ApiResponse<UserInfoResponse> patchUserInfo(Authentication authentication, @RequestBody UserInfoPatchRequest userInfoPatchRequest){
        UserInfo userInfo = userInfoService.patchUserInfo(authentication.getName(), userInfoPatchRequest);
        return new ApiResponse<>(200, UserInfoResponse.from(userInfo));
    }
}
