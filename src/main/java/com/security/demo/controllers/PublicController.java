package com.security.demo.controllers;

import com.security.demo.models.User;
import com.security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping()
public class PublicController {
  UserService userService;

  @Autowired
  public PublicController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("register")
  public String register(Model model) {
    model.addAttribute("user", new User());
    return "register";
  }

  @PostMapping("register")
  public String saveUser(@Valid @ModelAttribute User user, Model model, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("user", user);
      model.addAttribute("error", "Provide valid input for username and password");
      return "register";
    } else if (userService.isUserNameUnique(user.getUsername())) {
      model.addAttribute("user", user);
      model.addAttribute("error", "Username already in use");
      return "register";
    }
    userService.saveUser(user);
    return "redirect:/login";
  }

  @GetMapping("login")
  public String login() {
    return "login";
  }
}
