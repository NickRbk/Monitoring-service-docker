package com.petproject.monitoring.web.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TargetUserResDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private SocialMediaResDTO socialMedia;
}
