package com.petproject.monitoring.security.constants;

public interface SecurityConstants {
	String SECRET = "SecretKeyToGenJWTs";
	long EXPIRATION_TIME = 1_800_000; // 30 min
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
	String HEADER_EXPIRATION_STRING = "Expires";
	String SIGN_UP_URL = "/auth/sign-up";
}
