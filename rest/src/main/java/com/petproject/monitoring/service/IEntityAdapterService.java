package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.*;
import com.petproject.monitoring.web.dto.request.CustomerReqDTO;
import com.petproject.monitoring.web.dto.response.CustomerResDTO;
import com.petproject.monitoring.web.dto.request.TargetUserReqDTO;
import com.petproject.monitoring.web.dto.response.TargetUserResDTO;
import com.petproject.monitoring.web.dto.response.TweetsPageResDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import twitter4j.Status;
import twitter4j.User;

public interface IEntityAdapterService {
    Customer getCustomerFromDTO(Long customerId, CustomerReqDTO customerReqDTO, BCryptPasswordEncoder bCryptPasswordEncoder);
    TargetUser getTargetUserFromDTO(Long customerId, Long targetUserId, SocialMedia sm, TargetUserReqDTO targetUserReqDTO);
    TwitterUser getTwitterUserFromAPI(User u, boolean isTarget);
    Tweet getTweetFromAPI(Status status);
    CustomerResDTO getCustomerResDTOFromEntity(Customer customer);
    TargetUserResDTO getTargetUserResDTOFromEntity(TargetUser targetUser);
    TweetsPageResDTO getTweetsPageResDTOFromPageableEntity(Page<Tweet> tweets);
}
