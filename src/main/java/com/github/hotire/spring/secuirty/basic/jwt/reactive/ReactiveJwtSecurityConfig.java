package com.github.hotire.spring.secuirty.basic.jwt.reactive;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.github.hotire.spring.secuirty.basic.jwt.JwtAuthenticationConverter;
import com.github.hotire.spring.secuirty.basic.jwt.RSAJwtDecoder;
import com.github.hotire.spring.secuirty.basic.jwt.Role;

import reactor.core.publisher.Mono;

@ConditionalOnProperty(prefix = "hotire.security", value = "enabled", matchIfMissing = true)
//@EnableReactiveMethodSecurity
public class ReactiveJwtSecurityConfig {

    @Bean
    public SecurityWebFilterChain filter(@Nonnull final ServerHttpSecurity security, @Nonnull @Value("${jwt.publish-key}") final String publicKey) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        security.csrf().disable();
        security.anonymous().authorities(Role.NONE.getAuthority());

        return security.oauth2ResourceServer()
                       .jwt()
                       .publicKey(RSAJwtDecoder.withPublicKey(publicKey).getRsaPublicKey())
                       .jwtAuthenticationConverter(reactiveJwtConverter())
                       .and()
                       .and()
                       .authorizeExchange()
                       .pathMatchers("/jwt**").hasAnyRole(Role.USER.getRole())
                       .anyExchange()
                       .hasAnyRole(Role.NONE.getRole())
                       .and()
                       .build();
    }

    protected Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> reactiveJwtConverter() {
        return new ReactiveJwtAuthenticationConverter(new JwtAuthenticationConverter());
    }

}
