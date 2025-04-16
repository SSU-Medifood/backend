package Mefo.server.domain.userInfo.service;

import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.repository.UserInfoRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    //userId로 userInfo 찾기
    public UserInfo findByUserId(Long id){
        UserInfo userInfo = userInfoRepository.findByUserId(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        return userInfo;
    }
}
