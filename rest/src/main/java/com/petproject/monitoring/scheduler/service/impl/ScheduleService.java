package com.petproject.monitoring.scheduler.service.impl;

import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.domain.repository.TweetRepository;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.scheduler.service.IScheduleService;
import com.petproject.monitoring.service.IEntityAdapterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService {

    private Twitter twitter;
    private IEntityAdapterService entityAdapterService;
    private TweetRepository tweetRepository;
    private TwitterUserRepository tuRepository;

    @Override
    public void checkForNewTweets() {
        List<Tweet> tweets = new ArrayList<>();
        List<TwitterUser> twitterTargetUsers = tuRepository.findAllTargetTwitterUsers();
        twitterTargetUsers.forEach(twitterUser -> {
            try {
                ResponseList<Status> userTimeline = twitter.timelines().getUserTimeline(twitterUser.getScreenName());
                userTimeline.forEach(status -> tweets.add(getTweet(status)));
            } catch (TwitterException e) {
                log.error(e.getErrorMessage());
            }
        });
        tweetRepository.saveAll(tweets);
    }

    private Tweet getTweet(Status status) {
        return entityAdapterService.getTweetFromAPI(status);
    }
}
