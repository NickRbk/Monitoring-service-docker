package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.TargetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TargetUserRepository extends JpaRepository<TargetUser, Long> {
    List<TargetUser> getAllByCustomerId(Long customerId);

    Optional<TargetUser> findByIdAndCustomerId(Long targetUserId, Long customerId);

    @Query(value = "SELECT u FROM TargetUser u WHERE u.socialMedia.twitterProfile.twitterUser.screenName=:screenName")
    List<TargetUser> getTargetUsersByScreenName(@Param("screenName") String screenName);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE target_users SET social_media_id=:smId WHERE id=:targetUserId", nativeQuery = true)
    void setSocialMediaRef(@Param("smId") Long smId, @Param("targetUserId") Long targetUserId);
}
