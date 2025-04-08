package Mefo.server.domain.user.service;

import Mefo.server.domain.user.dto.EmailCheckRequest;
import Mefo.server.domain.user.dto.EmailCheckResponse;
import Mefo.server.domain.user.dto.JoinRequest;
import Mefo.server.domain.user.dto.LoginRequest;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.repository.UserRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //이메일 중복 확인하기
    public EmailCheckResponse checkEmailDuplicate(EmailCheckRequest emailCheckRequest){
        return EmailCheckResponse.from(userRepository.findByEmail(emailCheckRequest.getEmail()).isPresent());
    }

    //회원가입 하기
    @Transactional
    public void join(JoinRequest joinRequest){
        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));
        userRepository.save(joinRequest.toEntity());
    }

    //로그인 하기
    public User login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        if(!user.getPassword().equals(loginRequest.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_PASSWORD_DOESNT_MATCH);
        }
        return user;
    }

    //현재 로그인 한 계정(이메일)
    public User getLoginUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        return user;
    }
}
