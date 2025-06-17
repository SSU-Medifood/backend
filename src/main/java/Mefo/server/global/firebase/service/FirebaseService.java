package Mefo.server.global.firebase.service;

import Mefo.server.domain.user.entity.User;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import Mefo.server.global.firebase.entity.FirebaseToken;
import Mefo.server.global.firebase.repository.FirebaseRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseService {
    private final FirebaseRepository firebaseRepository;

    //푸시 알람 보내기
    public void sendPushNotification(User user, String title, String body) throws FirebaseMessagingException {
        FirebaseToken fcmToken = firebaseRepository.findByUserId(user.getId())
                .orElseThrow(()-> new BusinessException(ErrorCode.TOKEN_NOT_PROVIDED));

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(fcmToken.getFcmToken())
                .setNotification(notification)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
    }

    //토큰 저장 또는 업데이트 하기
    @Transactional
    public FirebaseToken saveOrUpdateToken(User user, String fcmToken) {
        Optional<FirebaseToken> fcmtoken = firebaseRepository.findByUserId(user.getId());
        FirebaseToken token;
        if (fcmtoken.isPresent()) {
            token = fcmtoken.get();
            token.setFcmToken(fcmToken);
            firebaseRepository.save(token);
        } else {
            token = new FirebaseToken(user, fcmToken);
            firebaseRepository.save(token);
        }
        return token;
    }

    @Transactional
    public void deleteToken(User user){
        FirebaseToken token = firebaseRepository.findByUserId(user.getId())
                .orElseThrow(()-> new BusinessException(ErrorCode.TOKEN_DOESNT_EXIST));
        firebaseRepository.delete(token);
    }
}
