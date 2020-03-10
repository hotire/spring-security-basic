package com.github.hotire.spring.secuirty.basic.jwt;


import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        final String role = jwt.getClaim("role");
        // TODO Implement role
        return new JwtAuthenticationToken(jwt, Lists.newArrayList(new SimpleGrantedAuthority(role)));
    }
}
