package com.petproject.monitoring.web.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaResDTO {
    private Long id;
    private TwitterProfileResDTO twitter;
}
