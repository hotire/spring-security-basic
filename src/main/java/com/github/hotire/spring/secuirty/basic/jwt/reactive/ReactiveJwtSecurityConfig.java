package com.github.hotire.spring.secuirty.basic.jwt.reactive;

import com.github.hotire.spring.secuirty.basic.jwt.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableReactiveMethodSecurity
public class ReactiveJwtSecurityConfig {
    @Bean
    public SecurityWebFilterChain filter(final ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.csrf().disable();
        serverHttpSecurity.anonymous().authorities(Role.NONE.getAuthority());

        return serverHttpSecurity.build();
    }
}
