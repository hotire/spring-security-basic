package com.github.hotire.spring.secuirty.basic.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.public-key}")
    private String publicKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.anonymous().authorities(Role.NONE.getAuthority());

        http.oauth2ResourceServer()
            .jwt()
            .decoder(RSAJwtDecoder.withPublicKey(publicKey))
            .jwtAuthenticationConverter(new JwtAuthenticationConverter())
            .and()
            .and()
            .authorizeRequests()
            .mvcMatchers("/account**").hasAnyRole(Role.USER.getRole())
            .anyRequest()
            .hasAnyRole(Role.NONE.getRole());
    }
}
