package com.example.stellarmemo.controller;

import com.example.stellarmemo.pojo.WebResult;
import com.example.stellarmemo.service.AdminOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8085")
public class AdminController {

  @Autowired private AdminOP adminOP;

  @RequestMapping("/login")
  public WebResult adminLogin(@RequestBody HashMap<String, String> map, HttpServletRequest request, HttpServletResponse response) {
    return adminOP.login(map.get("account"), map.get("password"), request, response);
  }

  @RequestMapping("/logout")
  public WebResult adminLogout(HttpServletRequest request, HttpServletResponse response) {
    return adminOP.logout(request, response);
  }

  @RequestMapping("/register")
  public WebResult adminRegister(@RequestBody HashMap<String, String> map, HttpServletRequest request) {
    return adminOP.register(map.get("account"), map.get("password"), map.get("adminName"), request);
  }

  //获取管理员权限
  @RequestMapping("/setPermission")
  public WebResult adminSetPermission(@RequestParam("account") String account,
                                 @RequestParam("examine") int examine,
                                 @RequestParam("delete") int delete,
                                 @RequestParam("manageAdmin") int manage,
                                 HttpServletRequest request) {
    return adminOP.setPermission(account, examine, delete, manage, request);
  }

}
