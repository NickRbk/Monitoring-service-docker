package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.repository.TweetRepository;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.service.IEnumUtilityService;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.sort.SortDirection;
import com.petproject.monitoring.sort.TwitterSortKeys;
import com.petproject.monitoring.web.dto.response.TweetsPageResDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

@Service
@AllArgsConstructor
public class TweetService implements ITweetService {
    private Twitter twitter;
    private TweetRepository tweetRepository;
    private ITargetUserService targetUserService;
    private IEnumUtilityService enumUtilityService;
    private IEntityAdapterService entityAdapterService;

    @Override
    public TweetsPageResDTO getTweets(Long customerId, String key, String direction, int page, int size) {
        Page<Tweet> tweets = getTweetsFromDB(customerId, key, direction, page, size);
        return entityAdapterService.getTweetsPageResDTOFromPageableEntity(tweets);
    }

    @Override
    public boolean checkTwitterAlias(String alias) {
        try {
            twitter.timelines().getUserTimeline(alias);
            return true;
        } catch (TwitterException e) {
            return false;
        }
    }

    private Page<Tweet> getTweetsFromDB(Long customerId, String key, String direction, int page, int size) {
        List<Long> targetIdList = targetUserService.getTargetIdList(customerId);
        if(key != null) {
            String d = enumUtilityService.getParamIfValid(SortDirection.class, direction);
            String k = enumUtilityService.getParamIfValid(TwitterSortKeys.class, key);
            Sort sort = new Sort(Sort.Direction.valueOf(d.toUpperCase()), k);
            return tweetRepository.findAllByTargetUserIdIn(targetIdList, PageRequest.of(page, size, sort));
        } else {
            return tweetRepository.findAllByTargetUserIdIn(targetIdList, PageRequest.of(page, size));
        }
    }
}
