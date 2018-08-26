package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.service.ITwitterUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TwitterUserService implements ITwitterUserService {
    private TwitterUserRepository twitterUserRepository;
    private TargetUserRepository targetUserRepository;

    @Override
    public void disableTwitterUserAsTargetIfNeeded(TargetUser targetUser) {
        Optional.ofNullable(targetUser.getSocialMedia().getTwitterProfile().getTwitterUser())
                .ifPresent(twitterUser -> {
                    String screenName = twitterUser.getScreenName();
                    List<TargetUser> targetUsersByScreenName = targetUserRepository.getTargetUsersByScreenName(screenName);
                    if(targetUsersByScreenName.size() == 0) {
                        twitterUserRepository.disableTwitterUserAsTarget(screenName);
                    }
                });
    }
}
