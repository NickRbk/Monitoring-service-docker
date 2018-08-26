package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.service.ITwitterProfileService;
import com.petproject.monitoring.service.ITwitterUserService;
import com.petproject.monitoring.web.dto.request.SocialAliasReqDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TwitterProfileService implements ITwitterProfileService {

    private Twitter twitter;
    private IEntityAdapterService entityAdapterService;
    private ITwitterUserService twitterUserService;
    private TargetUserRepository targetUserRepository;
    private TwitterProfileRepository twitterProfileRepository;
    private TwitterUserRepository twitterUserRepository;

    @Override
    @Transactional
    public void add(Long customerId, Long targetUserId, SocialAliasReqDTO smDTO) {
        TargetUser targetUser =
                targetUserRepository.findByIdAndCustomerId(targetUserId, customerId).orElseThrow(NotFoundException::new);
        saveOrUpdateTwitterUser(smDTO);
        twitterProfileRepository.setAlias(smDTO.getAlias().toLowerCase(), targetUserId);
        twitterUserService.disableTwitterUserAsTargetIfNeeded(targetUser);
    }

    private void saveOrUpdateTwitterUser(SocialAliasReqDTO smDTO) {
        Optional<TwitterUser> twitterUserOpt = twitterUserRepository.findByScreenName(smDTO.getAlias().toLowerCase());
        if(!twitterUserOpt.isPresent()) {
            try {
                User u = twitter.showUser(smDTO.getAlias());
                System.out.println("ERROR HERE");
                twitterUserRepository.save(entityAdapterService.getTwitterUserFromAPI(u, true));
            } catch (TwitterException e) {
                log.error(e.getErrorMessage());
            }
        } else if(!twitterUserOpt.get().isTarget()) {
            TwitterUser twitterUser = twitterUserOpt.get();
            twitterUser.setTarget(true);
            twitterUserRepository.save(twitterUser);
        }
    }
}
