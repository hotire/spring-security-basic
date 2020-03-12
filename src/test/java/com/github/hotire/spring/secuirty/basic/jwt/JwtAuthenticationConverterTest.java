package com.github.hotire.spring.secuirty.basic.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtAuthenticationConverterTest {

    @Test
    void convert() {
        // given
        final Jwt jwt = mock(Jwt.class);
        final JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        // when
        when(jwt.getClaim("role")).thenReturn("USER");
        final AbstractAuthenticationToken result = converter.convert(jwt);

        // then
        assertThat(result).isInstanceOf(JwtAuthenticationToken.class);
        assertThat(result).isNotNull();
        assertThat(result.getAuthorities()).isNotEmpty();
    }
}