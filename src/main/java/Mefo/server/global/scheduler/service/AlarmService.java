package Mefo.server.global.scheduler.service;

import Mefo.server.domain.alarm.entity.Alarm;
import Mefo.server.domain.alarm.repository.AlarmRepository;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.repository.UserRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import Mefo.server.global.scheduler.component.AlarmComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final AlarmComponent alarmComponent;
    private final UserRepository userRepository;
    @EventListener(ApplicationReadyEvent.class)
    public void initScheduledAlarms(){
        List<Alarm> alarms = alarmRepository.findAll();
        for(Alarm alarm : alarms){
            User user = userRepository.findById(alarm.getUser().getId())
                    .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
            alarmComponent.scheduleMediAlarm(user, alarm);
        }
    }
}
