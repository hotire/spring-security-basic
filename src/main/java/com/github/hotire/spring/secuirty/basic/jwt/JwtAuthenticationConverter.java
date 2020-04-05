package com.github.hotire.spring.secuirty.basic.jwt;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

import static com.github.hotire.spring.secuirty.basic.jwt.Role.ROLE;
import static java.util.stream.Collectors.toSet;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(@Nonnull Jwt jwt) {
        return new JwtAuthenticationToken(jwt,  Optional.ofNullable(jwt.<Collection<String>>getClaim(ROLE))
                                                        .map(it -> it.stream()
                                                                     .map(Role::lookup)
                                                                     .flatMap(role -> role.getAuthorities().stream())
                                                                     .collect(toSet()))
                                                        .orElse(Role.NONE.getAuthorities()));
    }
}
