package ru.geekbrains.lesson7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

  @GetMapping("/login")
  public String loginForm() {
    return "login";
  }
}
