package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.web.dto.request.TargetUserReqDTO;
import com.petproject.monitoring.web.dto.response.TargetUserResDTO;

import java.util.List;

public interface ITargetUserService {
    List<TargetUser> getUsersByCustomerId(Long customerId);
    List<TargetUserResDTO> getUsersResDTO(Long customerId);
    long add(Long customerId, TargetUserReqDTO targetUserReqDTO);
    void update(Long customerId, Long targetUserId, TargetUserReqDTO targetUserReqDTO);
    void delete(Long customerId, Long targetUserId);
    void delete(TargetUser targetUser);
    List<Long> getTargetIdList(Long customerId);
}
