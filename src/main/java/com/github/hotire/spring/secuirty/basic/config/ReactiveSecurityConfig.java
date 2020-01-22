package com.github.hotire.spring.secuirty.basic.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class ReactiveSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                   .pathMatchers("/test").hasAnyRole()
                   .anyExchange().permitAll()
                   .and()
                   .build();
    }
}
