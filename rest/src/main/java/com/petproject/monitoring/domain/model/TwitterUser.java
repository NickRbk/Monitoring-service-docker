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
@Table(name = "twitter_users")
public class TwitterUser extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_target")
    private boolean isTarget;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "screen_name")
    private String screenName;

    private String location;

    private String description;

    @Column(name = "followers_count")
    private Integer followersCount;

    @Column(name = "friends_count")
    private Integer friendsCount;

    @Column(name = "favourites_count")
    private Integer favouritesCount;

    @Column(name = "statuses_count")
    private Integer statusesCount;

    @Column(name = "profile_image_url")
    private String profileImageURL;
}
