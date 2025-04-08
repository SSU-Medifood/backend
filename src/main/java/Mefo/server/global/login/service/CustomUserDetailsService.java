package Mefo.server.global.login.service;

import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.repository.UserRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import Mefo.server.global.login.dto.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        return new CustomUserDetails(user);
    }
}
