package com.petproject.monitoring.service;

public interface IAuthService {
    Long getIdFromToken(String token);
}
