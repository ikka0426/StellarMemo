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

  @RequestMapping(value = "/test")
  public String test() {
    return "index";
  }

  @RequestMapping("/login")
  public WebResult login(@RequestBody HashMap<String, String> map) {
    return userOP.userLogin(map.get("username"), map.get("password"));
  }

  @RequestMapping("/register")
  public WebResult register(@RequestBody HashMap<String, String> map) {
//    System.out.print(map);
    return userOP.userRegister(map.get("username"), map.get("password"), map.get("email"));
  }

  @RequestMapping("/changePassword")
  public WebResult changePassword(@RequestBody HashMap<String, String> map) {
    return userOP.changePassword(map.get("username"), map.get("password"));
  }

//  @RequestMapping("/wxLogin")
//  public WebResult wxLogin(@RequestBody String code) {
//    return
//  }

}
