package com.github.hotire.spring.secuirty.basic.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.publish-key}")
    private String publishKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer()
            .jwt()
            .decoder(RSAJwtDecoder.withPublicKey(publishKey));
    }
}
