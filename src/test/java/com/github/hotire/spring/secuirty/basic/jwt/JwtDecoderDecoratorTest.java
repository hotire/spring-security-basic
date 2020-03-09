package com.github.hotire.spring.secuirty.basic.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtDecoderDecoratorTest {

    @Test
    void decode() {
        // given
        final Jwt expected = mock(Jwt.class);
        final String token = "";
        final JwtDecoder jwtDecoder = mock(JwtDecoder.class);
        final JwtDecoderDecorator jwtDecoderDecorator = new JwtDecoderDecorator(jwtDecoder);

        // when
        when(jwtDecoder.decode(token)).thenReturn(expected);
        final Jwt result = jwtDecoderDecorator.decode(token);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getDelegate() {
        // given
        final JwtDecoder expected = mock(JwtDecoder.class);
        final JwtDecoderDecorator jwtDecoderDecorator = new JwtDecoderDecorator(expected);

        // when
        final JwtDecoder result = jwtDecoderDecorator.getDelegate();

        // then
        assertThat(result).isEqualTo(expected);
    }
}