package com.github.hotire.spring.secuirty.basic.jwt;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;


@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    NONE("NONE");

    private final String role;
    private final String authority;
    private final List<SimpleGrantedAuthority> authorities;
    public static final String ROLE = "role";

    Role(final String role) {
        this.role = role;
        this.authority = "ROLE_" + role;
        this.authorities = Lists.newArrayList(new SimpleGrantedAuthority(this.role));
    }

    private static final Map<String, Role> ROLE_MAP = Arrays.stream(Role.values()).collect(toMap(Role::getRole, Function.identity()));

    public static Role lookup(final String role) {
        return Optional.ofNullable(ROLE_MAP.get(role)).orElse(NONE);
    }
}
