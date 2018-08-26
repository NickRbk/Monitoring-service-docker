package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.TargetUser;

public interface ITwitterUserService {
    void disableTwitterUserAsTargetIfNeeded(TargetUser targetUser);
}
