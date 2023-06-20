package com.example.stellarmemo.controller;

import com.example.stellarmemo.pojo.WebResult;
import com.example.stellarmemo.service.UserOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import  java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private UserOP userOP;

  @RequestMapping("/login")
  public WebResult login(@RequestBody HashMap<string, string> map) {
    return
  }

}
