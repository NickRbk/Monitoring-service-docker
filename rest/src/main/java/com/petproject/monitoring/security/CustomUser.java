package com.petproject.monitoring.security;

import com.petproject.monitoring.domain.model.Customer;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

class CustomUser extends User {
    @Getter
    private final Customer customer;

    CustomUser(String username,
                      String password,
                      Collection<? extends GrantedAuthority> authorities,
                      Customer customer) {
        super(username, password, authorities);
        this.customer = customer;
    }
}
