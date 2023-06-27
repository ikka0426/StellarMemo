package com.example.stellarmemo.controller;

import com.example.stellarmemo.pojo.WebResult;
import com.example.stellarmemo.service.UserOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  public WebResult login(@RequestBody HashMap<String, String> map, HttpServletRequest request, HttpServletResponse response) {
//    System.out.print(map);
    return userOP.userLogin(map.get("username"), map.get("password"), request, response);
  }

  @RequestMapping("/getCode")
  public WebResult getCode(@RequestBody HashMap<String, String> map) {
    return userOP.getCode(map.get("email"));
  }

  @RequestMapping("/register")
  public WebResult register(@RequestBody HashMap<String, String> map) {
//    System.out.print(map);
    return userOP.userRegister(map.get("username"), map.get("password"), map.get("email"), map.get("code"));
  }

  @RequestMapping("/changePassword")
  public WebResult changePassword(@RequestBody HashMap<String, String> map) {
    return userOP.changePassword(map.get("username"), map.get("password"), map.get("code"), map.get("email"));
  }

  @RequestMapping("/logout")
  public WebResult logout(HttpServletRequest request, HttpServletResponse response) {
    return userOP.userLogout(request, response);
  }

  @RequestMapping("/wxLogin")
  public WebResult wxLogin(@RequestBody HashMap<String, String> map) {
    return userOP.wxLogin(map.get("code"));
  }

}