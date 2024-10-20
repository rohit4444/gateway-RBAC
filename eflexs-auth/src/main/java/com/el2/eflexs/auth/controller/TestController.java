package com.el2.eflexs.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//this controller for Authorization code flow
@RestController
//@CrossOrigin(origins = {"*"})
public class TestController {
  @GetMapping("/home")
  public String home() {
  	System.out.println("hi eFlexs auth code flow");
      return "home";
  }
}