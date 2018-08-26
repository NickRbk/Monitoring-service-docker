package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TwitterUserRepository extends JpaRepository<TwitterUser, Long> {
    Optional<TwitterUser> findByScreenName(String screenName);

    @Query(value = "SELECT * FROM twitter_users WHERE is_target=true", nativeQuery = true)
    List<TwitterUser> findAllTargetTwitterUsers();

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE twitter_users SET is_target=false WHERE screen_name=:screenName", nativeQuery = true)
    void disableTwitterUserAsTarget(@Param("screenName") String screenName);
}
