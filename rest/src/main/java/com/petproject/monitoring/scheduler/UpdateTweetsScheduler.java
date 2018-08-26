package com.petproject.monitoring.scheduler;

import com.petproject.monitoring.scheduler.service.IScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateTweetsScheduler {
    private IScheduleService scheduleService;

    @Scheduled(cron = "${scheduler.cron.updater.twitter}")
    public void updateTweetsByAPIScheduler() {
        scheduleService.checkForNewTweets();
    }
}
