package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.*;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.service.constants.SocialMediaConstants;
import com.petproject.monitoring.web.dto.request.CustomerReqDTO;
import com.petproject.monitoring.web.dto.response.*;
import com.petproject.monitoring.web.dto.request.TargetUserReqDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EntityAdapterService implements IEntityAdapterService, SocialMediaConstants {
    private TwitterUserRepository twitterUserRepository;

    @Override
    public Customer getCustomerFromDTO(Long customerId, CustomerReqDTO customerReqDTO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Customer.builder()
                .id(customerId)
                .email(customerReqDTO.getEmail())
                .firstName(customerReqDTO.getFirstName())
                .lastName(customerReqDTO.getLastName())
                .password(bCryptPasswordEncoder.encode(customerReqDTO.getPassword()))
                .phoneNumber(customerReqDTO.getPhoneNumber())
                .build();
    }

    @Override
    public TargetUser getTargetUserFromDTO(Long customerId, Long targetUserId, SocialMedia sm, TargetUserReqDTO targetUserReqDTO) {
        return TargetUser.builder()
                .id(targetUserId)
                .customerId(customerId)
                .firstName(targetUserReqDTO.getFirstName())
                .lastName(targetUserReqDTO.getLastName())
                .socialMedia(sm)
                .build();
    }

    @Override
    public CustomerResDTO getCustomerResDTOFromEntity(Customer customer) {
        return CustomerResDTO.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    @Override
    public TargetUserResDTO getTargetUserResDTOFromEntity(TargetUser targetUser) {
        return TargetUserResDTO.builder()
                .id(targetUser.getId())
                .firstName(targetUser.getFirstName())
                .lastName(targetUser.getLastName())
                .socialMedia(getSocialMediaResDTOFromEntity(targetUser.getSocialMedia()))
                .build();
    }

    @Override
    public TweetsPageResDTO getTweetsPageResDTOFromPageableEntity(Page<Tweet> tweets) {
        List<TweetResDTO> tweetResDTOs = new ArrayList<>();
        tweets.forEach(tweet -> tweetResDTOs.add(getTweetResDTOFromEntity(tweet)));
        return TweetsPageResDTO.builder()
                .content(tweetResDTOs)
                .totalPages(tweets.getTotalPages())
                .currentPage(tweets.getNumber())
                .totalElements(tweets.getTotalElements())
                .build();
    }

    private TweetResDTO getTweetResDTOFromEntity(Tweet tweet) {
        boolean isRetweeted = tweet.getOriginalAuthor() != null;
        return TweetResDTO.builder()
                .createdAt(tweet.getCreatedAtTwitter())
                .text(tweet.getText())
                .textUrl(tweet.getTextUrl())
                .favouritesCount(tweet.getFavouritesCount())
                .retweetCount(tweet.getRetweetCount())
                .originalAuthor(isRetweeted
                        ? getTwitterUserResDTOFromEntity(tweet.getOriginalAuthor())
                        : null)
                .targetUser(getTwitterUserResDTOFromEntity(tweet.getTargetUser()))
                .build();
    }

    private SocialMediaResDTO getSocialMediaResDTOFromEntity(SocialMedia socialMedia) {
        boolean isTwitterProfileExist = Optional.ofNullable(socialMedia.getTwitterProfile()).isPresent();
        return SocialMediaResDTO.builder()
                .id(socialMedia.getId())
                .twitter(isTwitterProfileExist
                        ? getTwitterProfileResDTO(socialMedia.getTwitterProfile())
                        : null)
                .build();
    }

    private TwitterProfileResDTO getTwitterProfileResDTO(TwitterProfile twitterProfile) {
        boolean isProfileExist = Optional.ofNullable(twitterProfile.getTwitterUser()).isPresent();
        return TwitterProfileResDTO.builder()
                .id(twitterProfile.getId())
                .profile(isProfileExist
                        ? getTwitterUserResDTOFromEntity(twitterProfile.getTwitterUser())
                        : null)
                .build();
    }

    private TwitterUserResDTO getTwitterUserResDTOFromEntity(TwitterUser twitterUser) {
        return TwitterUserResDTO.builder()
                .id(twitterUser.getId())
                .userName(twitterUser.getUserName())
                .alias(twitterUser.getScreenName())
                .profileURL(TWITTER_URL + twitterUser.getScreenName())
                .location(twitterUser.getLocation())
                .description(twitterUser.getDescription())
                .followersCount(twitterUser.getFollowersCount())
                .friendsCount(twitterUser.getFriendsCount())
                .favouritesCount(twitterUser.getFavouritesCount())
                .statusesCount(twitterUser.getStatusesCount())
                .profileImageURL(twitterUser.getProfileImageURL())
                .build();
    }

    @Override
    public TwitterUser getTwitterUserFromAPI(User u, boolean isTarget) {
        return TwitterUser.builder()
                .userName(u.getName())
                .screenName(u.getScreenName().toLowerCase())
                .location(u.getLocation())
                .description(u.getDescription())
                .followersCount(u.getFollowersCount())
                .friendsCount(u.getFriendsCount())
                .favouritesCount(u.getFavouritesCount())
                .statusesCount(u.getStatusesCount())
                .isTarget(isTarget)
                .profileImageURL(u.getOriginalProfileImageURL())
                .build();
    }

    @Override
    public Tweet getTweetFromAPI(Status status) {
        boolean isRetweeted = status.getRetweetedStatus() != null;
        return Tweet.builder()
                .id(status.getId())
                .createdAtTwitter(status.getCreatedAt())
                .text(isRetweeted
                        ? status.getRetweetedStatus().getText()
                        : status.getText())
                .textUrl(status.getURLEntities().length > 0
                        ? status.getURLEntities()[0].getURL()
                        : (isRetweeted && status.getRetweetedStatus().getURLEntities().length > 0)
                        ? status.getRetweetedStatus().getURLEntities()[0].getURL()
                        : null)
                .favouritesCount(isRetweeted
                        ? status.getRetweetedStatus().getFavoriteCount()
                        : status.getFavoriteCount())
                .retweetCount(isRetweeted
                        ? status.getRetweetedStatus().getRetweetCount()
                        : status.getRetweetCount())
                .originalAuthor(isRetweeted
                        ? getTwitterUser(status.getRetweetedStatus().getUser())
                        : null)
                .targetUser(getTwitterUser(status.getUser()))
                .build();
    }

    private TwitterUser getTwitterUser(User u) {
        Optional<TwitterUser> twitterUser = twitterUserRepository.findByScreenName(u.getScreenName().toLowerCase());
        return twitterUser
                .orElseGet(() -> twitterUserRepository.save(getTwitterUserFromAPI(u, false)));
    }
}
