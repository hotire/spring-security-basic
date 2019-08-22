package com.github.hotire.spring.secuirty.basic.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Test
  public void loadUserByUsername() {
    // Given
    final String username = "hotire";
    final String password = "123";
    AccountService accountService = new AccountService(accountRepository, PasswordEncoderFactories.createDelegatingPasswordEncoder());

    // When
    when(accountRepository.findByUsername(username))
      .thenReturn(Optional.of(AccountFactory.create(username, password)));

    final UserDetails userDetails = accountService.loadUserByUsername(username);

    // Then
    assertThat(userDetails.getUsername()).isEqualTo(username);
    assertThat(userDetails.getPassword()).isEqualTo(password);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsername_throwException() {
    // Given
    final String username = "hotire";
    AccountService accountService = new AccountService(accountRepository, PasswordEncoderFactories.createDelegatingPasswordEncoder());

    // When
    when(accountRepository.findByUsername(username))
      .thenReturn(Optional.ofNullable(null));
    accountService.loadUserByUsername(username);
  }
}