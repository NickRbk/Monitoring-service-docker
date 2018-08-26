package com.petproject.monitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "INVALID SORTING KEY")
public class InvalidParameterException extends RuntimeException {
}
