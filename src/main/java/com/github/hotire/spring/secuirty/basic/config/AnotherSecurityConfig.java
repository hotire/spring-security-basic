package com.github.hotire.spring.secuirty.basic.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//@Order(99)
//@Configuration
public class AnotherSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/index")
        .authorizeRequests()
        .anyRequest()
        .permitAll();
  }
}
