package com.petproject.monitoring.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.petproject.monitoring.domain.model.Customer;
import com.petproject.monitoring.security.constants.JWTConstants;
import com.petproject.monitoring.security.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
public class JWTAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter
        implements SecurityConstants, JWTConstants {

    private AuthenticationManager authenticationManager;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        Customer credentials = new ObjectMapper().readValue(req.getInputStream(), Customer.class);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail(),
                        credentials.getPassword(),
                        new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        Claims claims = Jwts.claims();
        claims.put(EMAIL, user.getUsername());
        claims.put(CUSTOMER_ID, user.getCustomer().getId());
        claims.put(CUSTOMER, user.getCustomer());
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.addHeader(HEADER_EXPIRATION_STRING, Long.toString(expirationDate.getTime()));
    }
}
