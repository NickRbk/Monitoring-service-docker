package com.petproject.monitoring.web.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetsPageResDTO {
    List<TweetResDTO> content;
    private int totalPages;
    private int currentPage;
    private long totalElements;
}
