package com.github.hotire.spring.secuirty.basic.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import static org.assertj.core.api.Assertions.assertThat;

class JwtSecurityConfigTest {

    @Test
    void jwtConverter() {
        // given
        final JwtSecurityConfig config = new JwtSecurityConfig();

        // when
        final Converter<?, ?> result = config.jwtConverter();

        // then
        assertThat(result).isInstanceOf(JwtAuthenticationConverter.class);
    }
}