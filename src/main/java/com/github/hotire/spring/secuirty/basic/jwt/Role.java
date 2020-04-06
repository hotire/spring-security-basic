package com.github.hotire.spring.secuirty.basic.jwt;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;


@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    NONE("NONE");

    private final String role;
    private final String authority;
    private final Set<SimpleGrantedAuthority> authorities;
    public static final String ROLE = "role";

    Role(final String role) {
        this.role = role;
        this.authority = "ROLE_" + role;
        this.authorities = Sets.newHashSet(new SimpleGrantedAuthority(this.authority));
    }

    private static final Map<String, Role> ROLE_MAP = Arrays.stream(Role.values()).collect(toMap(Role::getRole, Function.identity()));

    public static Role lookup(final String role) {
        return Optional.ofNullable(ROLE_MAP.get(role)).orElse(NONE);
    }
}
