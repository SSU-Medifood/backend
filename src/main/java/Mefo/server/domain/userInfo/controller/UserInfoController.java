package Mefo.server.domain.userInfo.controller;

import Mefo.server.domain.userInfo.dto.UserInfoPatchRequest;
import Mefo.server.domain.userInfo.dto.UserInfoRequest;
import Mefo.server.domain.userInfo.dto.UserInfoResponse;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.repository.UserInfoRepository;
import Mefo.server.domain.userInfo.service.UserInfoService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo")
@RequiredArgsConstructor
@Tag(name="UserInfo")
public class UserInfoController {
    private final UserInfoService userInfoService;
    //유저 건강 정보 불러오기
    @GetMapping("/{userId}/get")
    @Operation(summary = "유저 건강 정보 확인하기")
    public ApiResponse<UserInfoResponse> getUserInfo(@PathVariable Long userId){
        UserInfo userInfo = userInfoService.findByUserId(userId);
        UserInfoResponse userInfoResponse = UserInfoResponse.from(userInfo);
        return new ApiResponse<>(200, userInfoResponse);
    }

    //유저 건강 정보 수정하기
    @Transactional
    @PatchMapping("/{userId}/patch")
    @Operation(summary = "유저 건강 정보 수정하기")
    public ApiResponse<UserInfoResponse> patchUserInfo(@PathVariable Long userId, @RequestBody UserInfoPatchRequest userInfoPatchRequest){
        UserInfo userInfo = userInfoService.patchUserInfo(userId, userInfoPatchRequest);
        return new ApiResponse<>(200, UserInfoResponse.from(userInfo));
    }
}
