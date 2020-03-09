package com.github.hotire.spring.secuirty.basic.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

@RequiredArgsConstructor
public class JwtDecoderDecorator implements JwtDecoder {

    @Getter(AccessLevel.PROTECTED)
    private final JwtDecoder delegate;

    @Override
    public Jwt decode(String token) throws JwtException {
        return getDelegate().decode(token);
    }
}
