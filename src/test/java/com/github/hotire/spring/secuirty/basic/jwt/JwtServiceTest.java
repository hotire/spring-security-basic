package com.github.hotire.spring.secuirty.basic.jwt;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

class JwtServiceTest {

    @Test
    void jwts() throws InvalidKeySpecException, NoSuchAlgorithmException {
        final JwtService service = new JwtService();

        final String result = service.jwts();

        System.out.println(result);
    }

    @Test
    void privateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        final JwtService service = new JwtService();

        final PrivateKey result = service.privateKey();

        System.out.println(result);
    }

    @Test
    void decodePrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        final JwtService service = new JwtService();

        final PrivateKey result = service.decodePrivateKey(service.key.replaceAll("(\r\n|\r|\n|\n\r)", ""));

        System.out.println(result);
    }
}