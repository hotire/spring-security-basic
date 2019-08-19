package com.github.hotire.spring.secuirty.basic.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Account {

  @Id @GeneratedValue
  private Integer id;

  @Column(unique = true)
  private String username;

  private String password;

  private String role;

  public Account encodePassword() {
    this.password = "{noop}" + this.password;
    return this;
  }
}
