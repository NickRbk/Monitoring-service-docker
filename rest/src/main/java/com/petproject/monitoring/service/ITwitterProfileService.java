package com.petproject.monitoring.service;

import com.petproject.monitoring.web.dto.request.SocialAliasReqDTO;

public interface ITwitterProfileService {
    void add(Long customerId, Long targetUserId, SocialAliasReqDTO saDTO);
}
