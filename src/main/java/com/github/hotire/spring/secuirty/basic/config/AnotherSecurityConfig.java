package com.github.hotire.spring.secuirty.basic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author : hotire
 */
@Configuration
public class AnotherSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/index")
        .authorizeRequests()
        .anyRequest()
        .permitAll();
  }
}
