package Mefo.server.global.login.service;

import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.repository.UserRepository;
import Mefo.server.domain.user.service.UserService;
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
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email){
        User user = userService.getUserByEmail(email);
        return new CustomUserDetails(user);
    }
}
