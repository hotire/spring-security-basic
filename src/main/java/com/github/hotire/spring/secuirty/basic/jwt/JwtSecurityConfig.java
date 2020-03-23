package com.github.hotire.spring.secuirty.basic.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.publish-key}")
    private String publishKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        
        http.oauth2ResourceServer()
            .jwt()
            .decoder(RSAJwtDecoder.withPublicKey(publishKey))
            .jwtAuthenticationConverter(new JwtAuthenticationConverter())
            .and()
            .and()
            .authorizeRequests()
            .anyRequest()
            .hasAnyRole(Role.NONE.getRole());
    }
}
