package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {
}
