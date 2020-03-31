package com.github.hotire.spring.secuirty.basic.jwt.reactive;

import com.github.hotire.spring.secuirty.basic.jwt.JwtAuthenticationConverter;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReactiveJwtAuthenticationConverterTest {

    @Test
    void getDelegate() {
        // given
        final JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        final ReactiveJwtAuthenticationConverter reactiveJwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter(converter);

        // when
        final JwtAuthenticationConverter result = reactiveJwtAuthenticationConverter.getDelegate();

        // then
        assertThat(result).isEqualTo(converter);
    }

    @Test
    void convert() {
        // given
        final Jwt jwt = mock(Jwt.class);
        final JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        final ReactiveJwtAuthenticationConverter reactiveJwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter(converter);

        // when
        when(jwt.getClaim("role")).thenReturn("USER");
        final Mono<AbstractAuthenticationToken> result = reactiveJwtAuthenticationConverter.convert(jwt);

        // then
        assertThat(result).isNotNull();
        StepVerifier.create(result).consumeNextWith(token -> {
            AssertionsForClassTypes.assertThat(token).isInstanceOf(JwtAuthenticationToken.class);
            AssertionsForClassTypes.assertThat(token).isNotNull();
            AssertionsForInterfaceTypes.assertThat(token.getAuthorities()).isNotEmpty();
        }).verifyComplete();
    }

}