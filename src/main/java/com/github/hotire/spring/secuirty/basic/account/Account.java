package com.github.hotire.spring.secuirty.basic.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Account {

  @Id @GeneratedValue
  private Integer id;

  @Column(unique = true)
  private String username;

  private String password;

  private String role;
}
