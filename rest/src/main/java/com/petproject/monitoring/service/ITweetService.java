package com.petproject.monitoring.service;

import com.petproject.monitoring.web.dto.response.TweetsPageResDTO;

public interface ITweetService {
    TweetsPageResDTO getTweets(Long customerId, String key, String direction, int page, int size);
    boolean checkTwitterAlias(String alias);
}
