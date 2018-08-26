package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.service.IAuthService;
import com.petproject.monitoring.service.ITwitterProfileService;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.web.dto.request.SocialAliasReqDTO;
import com.petproject.monitoring.web.dto.request.TargetUserReqDTO;
import com.petproject.monitoring.web.dto.response.TargetUserResDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.petproject.monitoring.security.constants.SecurityConstants.HEADER_STRING;

@Validated
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class TargetUserController {
    private ITargetUserService targetUserService;
    private ITwitterProfileService twitterProfileService;
    private IAuthService authService;

    @GetMapping
    public List<TargetUserResDTO> getTargetUsers(@RequestHeader(HEADER_STRING) String token) {
        return targetUserService.getUsersResDTO(authService.getIdFromToken(token));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public long addTargetUser(@RequestBody @NotNull @Valid TargetUserReqDTO targetUserReqDTO,
                        @RequestHeader(HEADER_STRING) String token) {
        return targetUserService.add(authService.getIdFromToken(token), targetUserReqDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{targetUserId}")
    public void updateTargetUser(@PathVariable Long targetUserId,
                           @RequestBody @NotNull @Valid TargetUserReqDTO targetUserReqDTO,
                           @RequestHeader(HEADER_STRING) String token) {
        targetUserService.update(authService.getIdFromToken(token), targetUserId, targetUserReqDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{targetUserId}")
    public void deleteTargetUser(@PathVariable Long targetUserId,
                           @RequestHeader(HEADER_STRING) String token) {
        targetUserService.delete(authService.getIdFromToken(token), targetUserId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("{targetUserId}/media")
    public void addSocialMediaResources(@PathVariable Long targetUserId,
                                        @RequestBody @NotNull @Valid SocialAliasReqDTO smDTO,
                                        @RequestHeader(HEADER_STRING) String token) {
        twitterProfileService.add(authService.getIdFromToken(token), targetUserId, smDTO);
    }
}
