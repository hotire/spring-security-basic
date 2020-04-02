package com.github.hotire.spring.secuirty.basic.jwt.reactive;

import com.github.hotire.spring.secuirty.basic.jwt.JwtAuthenticationConverter;
import com.github.hotire.spring.secuirty.basic.jwt.RSAJwtDecoder;
import com.github.hotire.spring.secuirty.basic.jwt.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@EnableReactiveMethodSecurity
public class ReactiveJwtSecurityConfig {
    @Bean
    public SecurityWebFilterChain filter(@Nonnull final ServerHttpSecurity security, @Nonnull  @Value("${jwt.publish-key}") String publicKey) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        security.csrf().disable();
        security.anonymous().authorities(Role.NONE.getAuthority());

        return security.oauth2ResourceServer()
                       .jwt()
                       .publicKey(RSAJwtDecoder.withPublicKey(publicKey).getRsaPublicKey())
                       .jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverter(new JwtAuthenticationConverter()))
                       .and()
                       .and()
                       .authorizeExchange()
                       .anyExchange()
                       .hasAnyRole(Role.NONE.getRole())
                       .and()
                       .build();
    }
}
