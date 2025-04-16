package Mefo.server.domain.user.controller;

import Mefo.server.domain.user.dto.EmailCheckRequest;
import Mefo.server.domain.user.dto.EmailCheckResponse;
import Mefo.server.domain.user.dto.EmailResponse;
import Mefo.server.domain.user.dto.PasswordRequest;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
        User loginUser = userService.getLoginUser(authentication.getName());
        return new ApiResponse<>(200, EmailResponse.from(loginUser));
    }

    //비밀번호 변경
    @PatchMapping("/login/password")
    @Operation(summary = "비밀번호 변경하기")
    public ApiResponse<String> changePassword(@RequestBody PasswordRequest passwordRequest){
        userService.changePassword(passwordRequest);
        return new ApiResponse<>(200, "success");
    }
}
