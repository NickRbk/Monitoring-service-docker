package com.petproject.monitoring.web.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TwitterUserResDTO {
    private Long id;
    private String userName;
    private String alias;
    private String profileURL;
    private String location;
    private String description;
    private int followersCount;
    private int friendsCount;
    private int favouritesCount;
    private int statusesCount;
    private String profileImageURL;
}
