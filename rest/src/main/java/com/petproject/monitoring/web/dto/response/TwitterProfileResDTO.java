package com.petproject.monitoring.web.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TwitterProfileResDTO {
    private Long id;
    private TwitterUserResDTO profile;
}
