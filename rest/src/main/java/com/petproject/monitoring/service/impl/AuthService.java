package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.security.constants.SecurityConstants;
import com.petproject.monitoring.service.IAuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import static com.petproject.monitoring.security.constants.JWTConstants.CUSTOMER_ID;

@Service
public class AuthService implements IAuthService, SecurityConstants  {
    @Override
    public Long getIdFromToken(String token) {
        Claims body = Jwts.parser().setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();

        return Long.parseLong(body.get(CUSTOMER_ID).toString());
    }
}
