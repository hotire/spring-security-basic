package com.github.hotire.spring.secuirty.basic.account;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserFactory {

  public static UserDetails createUser(String username, String password) {
    return User.builder()
      .username(username)
      .password(password)
      .roles("USER")
      .build();
  }
}
