package Mefo.server.global.login.filter;

import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.repository.UserRepository;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import Mefo.server.global.login.dto.CustomUserDetails;
import Mefo.server.global.login.dto.TokenResponse;
import Mefo.server.global.login.util.JwtUtil;
import Mefo.server.global.response.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestBody;
        try {
            requestBody = objectMapper.readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                   FilterChain chain, Authentication authentication) throws IOException {
        // username 추출
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        // role 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // JWTUtil에 token 생성 요청
        String token = jwtUtil.createJwt(username, role, 60*60*1000L);

        // JWT를 response에 담아서 응답 (header 부분에)
        // key : "Authorization"
        // value : "Bearer " (인증방식) + token
        //response.addHeader("Authorization", "Bearer " + token);
        //return new ApiResponse<>(200, token);

        response.setContentType("application/json;charset=UTF-8");

        // ApiResponse 객체 만들기
        ApiResponse<TokenResponse> apiResponse = new ApiResponse<>(200, TokenResponse.from(username, "Bearer " + token));

        // JSON으로 직렬화해서 응답 바디에 쓰기
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }

    // 로그인 실패 시
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {

        throw new BusinessException(ErrorCode.EMAIL_PASSWORD_DOESNT_MATCH);
    }
}
