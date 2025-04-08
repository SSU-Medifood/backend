package Mefo.server.global.login.controller;

import Mefo.server.domain.user.dto.*;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.global.login.dto.TokenResponse;
import Mefo.server.global.login.util.JwtUtil;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name="회원가입/로그인")
public class LoginController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //이메일 중복 확인하기
    @PostMapping
    @Operation(summary = "이메일 중복 확인")
    public ApiResponse<EmailCheckResponse> checkDuplicateEmail(
            @RequestBody EmailCheckRequest emailCheckRequest){
        return new ApiResponse<>(200, userService.checkEmailDuplicate(emailCheckRequest));
    }

    @PostMapping("/join")
    @Operation(summary = "회원가입")
    public ApiResponse<EmailResponse> join(@RequestBody JoinRequest joinRequest){
        userService.join(joinRequest);
        return new ApiResponse<>(200, EmailResponse.from(joinRequest.getEmail()));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인하기")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest loginRequest){
        User user = userService.login(loginRequest);
        String token = jwtUtil.createJwt(user.getEmail(),"user",1000*60*60L);
        return new ApiResponse<>(200, TokenResponse.from(user.getEmail(), token));
    }
}
