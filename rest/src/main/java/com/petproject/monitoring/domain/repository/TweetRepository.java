package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Page<Tweet> findAllByTargetUserIdIn(List<Long> targetIdList, Pageable pageable);
}
