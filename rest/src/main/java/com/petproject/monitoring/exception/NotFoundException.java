package com.petproject.monitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "ENTITY NOT FOUND")
public class NotFoundException extends RuntimeException {
}
