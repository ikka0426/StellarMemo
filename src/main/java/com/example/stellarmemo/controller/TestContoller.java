package com.example.stellarmemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/hello")
public class TestContoller {
  @RequestMapping(value = "/test")
  public String test() {
    return "index";
  }
}
