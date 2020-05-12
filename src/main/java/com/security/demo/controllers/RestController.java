package com.security.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api")
public class RestController {

  @GetMapping("resource1")
  public String returnResource1() {
    return "this is resource 111111";
  }

  @GetMapping("resource2")
  public String returnResource2() {
    return "this is resource 2222222";
  }

}
