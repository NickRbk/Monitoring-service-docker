package com.petproject.monitoring.domain.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "twitter_profiles")
public class TwitterProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_user_id")
    private Long targetUserId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "twitter_alias", referencedColumnName = "screen_name")
    private TwitterUser twitterUser;
}
