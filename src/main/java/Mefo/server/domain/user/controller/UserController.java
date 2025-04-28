package Mefo.server.domain.user.controller;

import Mefo.server.domain.user.dto.*;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name="User")
public class UserController {
    private final UserService userService;

    //현재 로그인한 사용자
    @GetMapping("/user/loginCheck")
    @Operation(summary = "현재 로그인한 이메일 확인")
    public ApiResponse<EmailResponse> getLoginUser(Authentication authentication){
        User user = userService.getLoginUser(authentication.getName());
        return new ApiResponse<>(200, EmailResponse.from(user));
    }

    //비밀번호 변경
    @Transactional
    @PatchMapping("/login/password")
    @Operation(summary = "비밀번호 변경하기")
    public ApiResponse<String> patchPassword(@RequestBody PasswordRequest passwordRequest){
        userService.patchPassword(passwordRequest);
        return new ApiResponse<>(200, null);
    }

    //사용자 설정 불러오기
    @GetMapping("/user/get")
    @Operation(summary = "사용자 설정 불러오기")
    public ApiResponse<UserResponse> getUser(Authentication authentication){
        User user = userService.getLoginUser(authentication.getName());
        return new ApiResponse<>(200, UserResponse.from(user));
    }

    //사용자 설정 수정하기
    @Transactional
    @PatchMapping("/user/patch")
    @Operation(summary = "사용자 설정 수정하기")
    public ApiResponse<UserResponse> patchUser(Authentication authentication, @RequestBody UserRequest userRequest){
        User user = userService.patchUser(authentication.getName(), userRequest);
        return new ApiResponse<>(200, UserResponse.from(user));
    }

    //회원탈퇴
    @Transactional
    @DeleteMapping("/user/delete")
    @Operation(summary = "회원 탈퇴하기")
    public ApiResponse<String> deleteUser(Authentication authentication){
        userService.deleteUser(authentication.getName());
        return new ApiResponse<>(204,null);
    }
}
