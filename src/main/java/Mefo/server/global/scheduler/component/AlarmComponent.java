package Mefo.server.global.scheduler.component;

import Mefo.server.domain.alarm.entity.Alarm;
import Mefo.server.domain.user.entity.User;
import Mefo.server.global.firebase.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class AlarmComponent {
    private final ThreadPoolTaskScheduler taskScheduler;
    private final FirebaseService firebaseService;
    private final Map<Long, ScheduledFuture<?>> reminderTasks = new ConcurrentHashMap<>();

    public void scheduleMediAlarm(User user, Alarm alarm){
        Runnable task = () -> {
            try{
                firebaseService.sendPushNotification(user, "약 복용 알림", alarm.getMedicine().getMediNickName()+" 드실 시간이에요!");
            } catch (Exception e){
                e.printStackTrace();
            }
        };
        // 첫 실행 시각 계산
        ZonedDateTime now = ZonedDateTime.now();
        LocalTime time = alarm.getAlarmTime();
        ZonedDateTime firstRun = now.withHour(time.getHour()).withMinute(time.getMinute()).withSecond(0);

        if (firstRun.isBefore(now)) {
            firstRun = firstRun.plusDays(1); // 오늘 시간이 지났다면 내일로
        }

        long initialDelay = Duration.between(now, firstRun).toMillis();
        long period = Duration.ofDays(1).toMillis(); // 매일 반복

        ScheduledFuture<?> future = taskScheduler.scheduleAtFixedRate(task, new Date(System.currentTimeMillis() + initialDelay), period);
        reminderTasks.put(alarm.getId(), future);
    }

    public void cancelMediAlarm(Long alarmId){
        ScheduledFuture<?> future = reminderTasks.get(alarmId);
        if(future != null){
            future.cancel(false);
            reminderTasks.remove(alarmId);
        }
    }
}
