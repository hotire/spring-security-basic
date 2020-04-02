package com.github.hotire.spring.secuirty.basic.jwt.reactive;

import com.github.hotire.spring.secuirty.basic.jwt.JwtAuthenticationConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Objects;

@RequiredArgsConstructor
public class ReactiveJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Getter
    private final JwtAuthenticationConverter delegate;

    @Override
    public Mono<AbstractAuthenticationToken> convert(@Nonnull Jwt jwt) {
        return Mono.just(Objects.requireNonNull(getDelegate().convert(jwt)));
    }
}
