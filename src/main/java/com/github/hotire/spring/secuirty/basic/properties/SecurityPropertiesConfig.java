package com.github.hotire.spring.secuirty.basic.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

import com.github.hotire.spring.secuirty.basic.jwt.JwtAuthenticationConverter;
import com.github.hotire.spring.secuirty.basic.jwt.RSAJwtDecoder;
import com.github.hotire.spring.secuirty.basic.jwt.Role;

import lombok.RequiredArgsConstructor;

@ConditionalOnProperty(prefix = "hotire.security.properties", value = "enabled", matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class SecurityPropertiesConfig extends WebSecurityConfigurerAdapter {

    private final SecurityProperties properties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.anonymous().authorities(Role.NONE.getAuthority());

        final ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.oauth2ResourceServer()
                                                                                                               .jwt()
                                                                                                               .decoder(RSAJwtDecoder.withPublicKey(properties.getJwtPublicKey()))
                                                                                                               .jwtAuthenticationConverter(new JwtAuthenticationConverter())
                                                                                                               .and()
                                                                                                               .and()
                                                                                                               .authorizeRequests();
        properties.getPathRoles()
                  .forEach(pathRole -> registry.mvcMatchers(pathRole.getHttpMethod(), pathRole.getPathPatterns().toArray(new String[0]))
                                               .hasAnyRole(pathRole.getRoles().stream().map(Role::getRole).toArray(String[]::new)));

        registry.anyRequest()
                .hasAnyRole(properties.getAnyRequestRoles().stream().map(Role::getRole).toArray(String[]::new));
    }
}
