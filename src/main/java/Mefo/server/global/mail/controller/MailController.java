package Mefo.server.global.mail.controller;

import Mefo.server.global.mail.dto.AuthCodeRequest;
import Mefo.server.global.mail.dto.AuthCodeResponse;
import Mefo.server.global.mail.service.MailService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/mail")
@RequiredArgsConstructor
@Tag(name="메일 전송")
public class MailController {
    private final MailService mailService;

    //인증 코드 전송하기
    @PostMapping("/login/mail")
    @Operation(summary = "이메일 인증")
    public ApiResponse<AuthCodeResponse> authCodeRequest(@RequestBody AuthCodeRequest authCodeRequest) throws MessagingException {
        String authCode = mailService.sendSimpleMessage(authCodeRequest.getEmail());
        return new ApiResponse<>(200, AuthCodeResponse.from(authCodeRequest.getEmail(), authCode));
    }
}
