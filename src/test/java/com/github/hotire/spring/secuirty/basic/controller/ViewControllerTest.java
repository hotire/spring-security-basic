package com.github.hotire.spring.secuirty.basic.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ViewController.class)
public class ViewControllerTest {

  @Autowired
  private MockMvc mockMvc;

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
}