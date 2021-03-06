package com.github.hotire.spring.secuirty.basic.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

  private final AccountRepository accountRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Account account = accountRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException(username));

    return User.builder()
      .username(account.getUsername())
      .password(account.getPassword())
      .roles(account.getRole())
      .build();
  }

  public Account create(Account account) {
    account.encodePassword(passwordEncoder);
    return accountRepository.save(account);
  }
}
