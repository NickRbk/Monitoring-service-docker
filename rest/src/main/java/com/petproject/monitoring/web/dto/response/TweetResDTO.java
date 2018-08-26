package com.petproject.monitoring.web.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetResDTO {
    private Date createdAt;
    private String text;
    private String textUrl;
    private int favouritesCount;
    private int retweetCount;
    private TwitterUserResDTO originalAuthor;
    private TwitterUserResDTO targetUser;
}
