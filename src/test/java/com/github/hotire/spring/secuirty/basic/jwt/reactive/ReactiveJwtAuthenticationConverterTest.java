package com.github.hotire.spring.secuirty.basic.jwt.reactive;

import com.github.hotire.spring.secuirty.basic.jwt.JwtAuthenticationConverter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}