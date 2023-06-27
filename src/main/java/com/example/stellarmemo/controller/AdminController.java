package com.example.stellarmemo.controller;

import com.example.stellarmemo.pojo.WebResult;
import com.example.stellarmemo.service.AdminOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired private AdminOP adminOP;

  @RequestMapping("/login")
  public WebResult adminLogin(@RequestBody HashMap<String, String> map, HttpServletRequest request, HttpServletResponse response) {
    return adminOP.login(map.get("username"), map.get("password"), request, response);
  }

  @RequestMapping("/register")
  public WebResult adminRegister(@RequestBody HashMap<String, String> map, HttpServletRequest request) {
    return adminOP.register(map.get("username"), map.get("password"), request);
  }

}
