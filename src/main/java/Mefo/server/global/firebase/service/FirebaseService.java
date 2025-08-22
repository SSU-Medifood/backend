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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseService {
    private final FirebaseRepository firebaseRepository;

    //푸시 알람 보내기
    public void sendPushNotification(User user, String title, String body) throws FirebaseMessagingException {
        List<FirebaseToken> fcmTokens = firebaseRepository.findAllByUserId(user.getId());
        for(FirebaseToken firebaseToken : fcmTokens)
        {
            Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

            Message message = Message.builder()
                .setToken(firebaseToken.getFcmToken())
                .setNotification(notification)
                .build();

            String response = FirebaseMessaging.getInstance().send(message);
        }
    }

//    //토큰 저장 또는 업데이트 하기
//    @Transactional
//    public FirebaseToken saveOrUpdateToken(User user, String device, String fcmToken) {
//        Optional<FirebaseToken> fcmtoken = firebaseRepository.findByUserIdAndDevice(user.getId(), device);
//        FirebaseToken token;
//        if (fcmtoken.isPresent()) {
//            token = fcmtoken.get();
//            token.setFcmToken(fcmToken);
//            firebaseRepository.save(token);
//        } else {
//            token = new FirebaseToken(user, device, fcmToken);
//            firebaseRepository.save(token);
//        }
//        return token;
//    }

    //특정 기기 토큰 삭제하기
    @Transactional
    public void deleteToken(User user, String device){
        Optional<FirebaseToken> fcmToken = firebaseRepository.findByUserIdAndDevice(user.getId(), device);
        FirebaseToken token;
        if (fcmToken.isPresent()){
            token = fcmToken.get();
            firebaseRepository.delete(token);
        }
    }

    //모든 토큰 삭제하기
    @Transactional
    public void deleteAllToken(User user){
        List<FirebaseToken> fcmTokens = firebaseRepository.findAllByUserId(user.getId());
        firebaseRepository.deleteAll(fcmTokens);
    }
}
