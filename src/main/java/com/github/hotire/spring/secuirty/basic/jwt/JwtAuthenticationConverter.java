package com.github.hotire.spring.secuirty.basic.jwt;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.github.hotire.spring.secuirty.basic.jwt.Role.ROLE;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(@Nonnull Jwt jwt) {
        return new JwtAuthenticationToken(jwt,  Optional.ofNullable(jwt.getClaim(ROLE))
                                                        .map(Object::toString)
                                                        .map(Role::lookup)
                                                        .map(Role::getAuthorities)
                                                        .orElse(Role.NONE.getAuthorities()));
    }
}
