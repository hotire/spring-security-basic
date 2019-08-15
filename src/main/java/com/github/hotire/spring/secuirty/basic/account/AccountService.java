package com.github.hotire.spring.secuirty.basic.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

  private final AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Account account = accountRepository.findByUsername()
      .orElseThrow(() -> new UsernameNotFoundException(username));

    return User.builder()
      .username(account.getUsername())
      .password(account.getPassword())
      .roles(account.getRole())
      .build();
  }
}
