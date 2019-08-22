package com.github.hotire.spring.secuirty.basic.account;

public class AccountFactory {

  public static Account create(String username, String password) {
    return Account.builder()
      .username(username)
      .password(password)
      .role("USER")
      .build();
  }
}
