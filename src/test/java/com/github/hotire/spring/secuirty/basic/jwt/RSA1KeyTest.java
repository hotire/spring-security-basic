package com.github.hotire.spring.secuirty.basic.jwt;

import org.junit.jupiter.api.Test;

class RSA1KeyTest {

    @Test
    void get() {
        JwtService service = new JwtService();
        RSA1Key key = new RSA1Key();

        key.get(service.key.replaceAll("(\r\n|\r|\n|\n\r)", ""));
    }
}