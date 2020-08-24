package com.github.hotire.spring.secuirty.basic.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    @Test
    void getToken() {
        // given
        final JwtService service = new JwtService();

        // when
        final String result = service.getToken(JwtService.KEY);

        // then
        Assertions.assertThat(result).isNotEmpty();
    }

}