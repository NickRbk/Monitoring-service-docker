package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.TwitterProfile;
import com.petproject.monitoring.domain.repository.SocialMediaRepository;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.service.ITwitterUserService;
import com.petproject.monitoring.web.dto.request.TargetUserReqDTO;
import com.petproject.monitoring.web.dto.response.TargetUserResDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TargetUserService implements ITargetUserService {

    private IEntityAdapterService entityAdapterService;
    private ITwitterUserService twitterUserService;
    private TargetUserRepository targetUserRepository;
    private SocialMediaRepository socialMediaRepository;
    private TwitterProfileRepository twitterProfileRepository;

    @Override
    public List<TargetUser> getUsersByCustomerId(Long customerId) {
        return targetUserRepository.getAllByCustomerId(customerId);
    }

    @Override
    public List<TargetUserResDTO> getUsersResDTO(Long customerId) {
        List<TargetUserResDTO> targetUserResDTOs = new ArrayList<>();
        List<TargetUser> targetUsers = targetUserRepository.getAllByCustomerId(customerId);
        targetUsers.forEach(targetUser -> {
            TargetUserResDTO targetUserResDTO = entityAdapterService.getTargetUserResDTOFromEntity(targetUser);
            targetUserResDTOs.add(targetUserResDTO);
        });
        return targetUserResDTOs;
    }

    @Override
    public List<Long> getTargetIdList(Long customerId) {
        List<TargetUser> targetUsers = getUsersByCustomerId(customerId);
        return targetUsers.stream()
                .map(u -> u.getSocialMedia().getTwitterProfile().getTwitterUser().getId())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public long add(Long customerId, TargetUserReqDTO targetUserReqDTO) {
        TargetUser targetUser = targetUserRepository.save(
                entityAdapterService.getTargetUserFromDTO(customerId, null, null, targetUserReqDTO)
        );
        TwitterProfile twitterProfile = twitterProfileRepository.save(
                TwitterProfile.builder().targetUserId(targetUser.getId()).build()
        );
        SocialMedia sm = socialMediaRepository.save(
                SocialMedia.builder()
                        .targetUserId(targetUser.getId())
                        .twitterProfile(twitterProfile)
                        .build());
        targetUserRepository.setSocialMediaRef(sm.getId(), targetUser.getId());

        return targetUser.getId();
    }

    @Override
    public void update(Long customerId, Long targetUserId, TargetUserReqDTO targetUserReqDTO) {
        Optional<TargetUser> user = targetUserRepository.findByIdAndCustomerId(targetUserId, customerId);
        if(user.isPresent()) {
            SocialMedia sm = user.get().getSocialMedia();
            targetUserRepository.save(
                    entityAdapterService.getTargetUserFromDTO(customerId, targetUserId, sm, targetUserReqDTO)
            );
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public void delete(Long customerId, Long targetUserId) {
        TargetUser targetUser = targetUserRepository.findByIdAndCustomerId(targetUserId, customerId)
                .orElseThrow(NotFoundException::new);
        deleteByTargetUser(targetUser);
    }

    @Override
    @Transactional
    public void delete(TargetUser targetUser) {
        deleteByTargetUser(targetUser);
    }

    private void deleteByTargetUser(TargetUser targetUser) {
        targetUserRepository.deleteById(targetUser.getId());
        twitterUserService.disableTwitterUserAsTargetIfNeeded(targetUser);
    }
}
