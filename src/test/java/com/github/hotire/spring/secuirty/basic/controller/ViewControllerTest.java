package com.github.hotire.spring.secuirty.basic.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.hotire.spring.secuirty.basic.WithMockAdmin;
import com.github.hotire.spring.secuirty.basic.account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ViewController.class)
public class ViewControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @Test
  public void index() throws Exception {
    mockMvc.perform(get("/"))
      .andExpect(status().isOk())
      .andDo(print());
  }

  @Test
  public void hello_isUnauthorized() throws Exception {
    mockMvc.perform(get("/hello"))
      .andExpect(status().isUnauthorized())
      .andDo(print());
  }

  @Test
  public void hello_user() throws Exception {
    mockMvc.perform(get("/hello").with(user("hotire").roles("USER")))
      .andExpect(status().isOk())
      .andDo(print());
  }

  @WithMockAdmin
  @Test
  public void hello_admin() throws Exception {
    mockMvc.perform(get("/hello"))
      .andExpect(status().isOk())
      .andDo(print());
  }

  @Test
  public void hello() throws Exception {
    when(accountService.loadUserByUsername("hotire"))
      .thenReturn(
        User.builder()
          .username("hotire")
          .password("123")
          .roles("USER")
          .build()
      );

    UserDetails user = accountService.loadUserByUsername("hotire");
    System.out.println(user);
    mockMvc.perform(formLogin().user("hotire").password("123"))
      .andExpect(authenticated());
  }

}