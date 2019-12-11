package com.github.hotire.spring.secuirty.basic.account;

import java.security.Principal;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/account")
@RestController
public class AccountController {

  @GetMapping
  public ModelAndView index(ModelAndView modelAndView, Principal principal) {
    final String message = "Hello" + Optional.ofNullable(principal)
      .map(Principal::getName)
      .orElse("Spring Security");
    modelAndView.addObject("message", message);
    modelAndView.setViewName("index");
    return modelAndView;
  }

  @GetMapping("/hello")
  public ModelAndView hello(ModelAndView modelAndView) {
    modelAndView.setViewName("hello");
    return modelAndView;
  }

  @GetMapping("/admin")
  public ModelAndView admin(ModelAndView modelAndView) {
    modelAndView.setViewName("admin");
    return modelAndView;
  }
}
