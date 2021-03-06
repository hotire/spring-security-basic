package com.github.hotire.spring.secuirty.basic.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@Builder
public class Account {

  @Id @GeneratedValue
  private Integer id;

  @Column(unique = true)
  private String username;

  private String password;

  private String role;

  public Account encodePassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(this.password);
    return this;
  }
}
