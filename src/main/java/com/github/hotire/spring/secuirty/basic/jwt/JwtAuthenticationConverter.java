package com.github.hotire.spring.secuirty.basic.jwt;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        return new JwtAuthenticationToken(jwt,  Optional.ofNullable(jwt.getClaim("role"))
                                                        .map(Object::toString)
                                                        .map(Role::lookup)
                                                        .map(Role::getAuthorities)
                                                        .orElse(Role.NONE.getAuthorities()));
    }
}
