package com.security.demo.controllers;

import com.security.demo.models.User;
import com.security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {
  private UserService userService;

  @Autowired
  public AdminController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("index")
  public String getAdminIndex() {
    return "adminindex";
  }

  @GetMapping("get-users")
  @ResponseBody
  public List<User> getAllUsers() {
    return userService.findAllUsers();
  }
}
