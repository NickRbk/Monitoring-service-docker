package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.TwitterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TwitterProfileRepository extends JpaRepository<TwitterProfile, Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE twitter_profiles SET twitter_alias=:twAlias WHERE target_user_id=:userId", nativeQuery = true)
    void setAlias(@Param("twAlias") String twAlias, @Param("userId") Long userId);
}
