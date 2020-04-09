package com.github.hotire.spring.secuirty.basic.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
@EnableWebSecurity
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
            .jwtAuthenticationConverter(jwtConverter())
            .and()
            .and()
            .authorizeRequests()
            .mvcMatchers("/jwt**").hasAnyRole(Role.USER.getRole())
            .anyRequest()
            .hasAnyRole(Role.NONE.getRole());
    }

    protected Converter<Jwt, ? extends AbstractAuthenticationToken> jwtConverter() {
        return new JwtAuthenticationConverter();
    }

}
