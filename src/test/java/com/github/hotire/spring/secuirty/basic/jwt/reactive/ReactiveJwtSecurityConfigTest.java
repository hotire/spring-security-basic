package com.github.hotire.spring.secuirty.basic.jwt.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.assertj.core.api.Assertions.assertThat;

class ReactiveJwtSecurityConfigTest {

    @Test
    void filter() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        // given
        final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnzyis1ZjfNB0bBgKFMSvvkTtwlvBsaJq7S5wA+kzeVOVpVWwkWdVha4s38XM/pa/yr47av7+z3VTmvDRyAHcaT92whREFpLv9cj5lTeJSibyr/Mrm/YtjCZVWgaOYIhwrXwKLqPr/11inWsAkfIytvHWTxZYEcXLgAXFuUuaS3uF9gEiNQwzGTU1v0FqkqTBr4B8nW3HCN47XUu0t8Y0e+lf4s4OxQawWD79J9/5d3Ry0vbV3Am1FtGJiJvOwRsIfVChDpYStTcHTCMqtvWbV6L11BWkpzGXSW4Hv43qa+GSYOD2QU68Mb59oSk2OB+BtOLpJofmbGEGgvmwyCI9MwIDAQAB";
        final ReactiveJwtSecurityConfig config = new ReactiveJwtSecurityConfig();

        // when
        final SecurityWebFilterChain result = config.filter(ServerHttpSecurity.http(), publicKey);

        // then
        assertThat(result).isNotNull();
    }
}