package com.petproject.monitoring.web.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TargetUserReqDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
