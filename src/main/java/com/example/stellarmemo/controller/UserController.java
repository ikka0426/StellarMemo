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
  public WebResult login(@RequestBody HashMap<String, String> map) {
    return userOP.userLogin(map.get("account"), map.get("password"));
  }

  @RequestMapping("/register")
  public WebResult register(@RequestBody HashMap<String, String> map) {
    return userOP.userRegister(map.get("account"), map.get("password"));
  }

  @RequestMapping("/changePassword")
  public WebResult changePassword(@RequestBody HashMap<String, String> map) {
    return userOP.changePassword(map.get("account"), map.get("password"));
  }

}
