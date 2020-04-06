package com.github.hotire.spring.secuirty.basic.config;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public AccessDecisionManager accessDecisionManager() {
    final RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

    final DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy);

    final WebExpressionVoter voter = new WebExpressionVoter();
    voter.setExpressionHandler(handler);

    return new AffirmativeBased(ImmutableList.of(voter));
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers("/account").permitAll()
        .mvcMatchers("/account/hello").hasRole("USER")
        .mvcMatchers("/admin").hasRole("ADMIN")
        .anyRequest().authenticated()
        .accessDecisionManager(accessDecisionManager())
        .and()
      .formLogin()
        .and()
      .httpBasic();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
