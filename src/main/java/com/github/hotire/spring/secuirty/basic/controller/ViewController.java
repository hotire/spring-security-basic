package com.github.hotire.spring.secuirty.basic.controller;

import java.security.Principal;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

  @GetMapping
  public String index(Model model, Principal principal) {
    final String message = "Hello" + Optional.ofNullable(principal)
      .map(Principal::getName)
      .orElse("Spring Security");
    model.addAttribute("message", message);
    return "index";
  }
}
