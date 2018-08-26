package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.service.IAuthService;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.web.dto.response.TweetsPageResDTO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.petproject.monitoring.security.constants.SecurityConstants.HEADER_STRING;

@Validated
@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
public class SocialMediaController {
    private ITweetService tweetService;
    private IAuthService authService;

    @GetMapping()
    public TweetsPageResDTO getTweets(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam(value = "orderBy", required = false) String key,
                                            @RequestParam(value = "d", defaultValue = "desc", required = false) String direction,
                                            @RequestHeader(HEADER_STRING) String token) {

        return tweetService.getTweets(authService.getIdFromToken(token), key, direction, page, size);
    }

    @GetMapping("/{alias}")
    public boolean checkTwitterAlias(@PathVariable String alias) {
        return tweetService.checkTwitterAlias(alias);
    }
}
