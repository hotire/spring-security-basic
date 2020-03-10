package com.github.hotire.spring.secuirty.basic.jwt;

import com.google.common.collect.Lists;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    NONE("NONE")
    private final String role;
    private final List<SimpleGrantedAuthority> authorities;

    Role(final String role) {
        this.role = "ROLE_" + role;
        this.authorities = Lists.newArrayList(new SimpleGrantedAuthority(this.role));
    }
}
