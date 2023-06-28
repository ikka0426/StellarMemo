package com.example.stellarmemo.controller;

import com.example.stellarmemo.pojo.WebResult;
import com.example.stellarmemo.service.AdminOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    return adminOP.login(map.get("account"), map.get("password"), request, response);
  }

  @RequestMapping("/register")
  public WebResult adminRegister(@RequestBody HashMap<String, String> map, HttpServletRequest request) {
    return adminOP.register(map.get("adminName"), map.get("password"), map.get("account"), request);
  }

  //获取管理员权限
  @RequestMapping("/register")
  public WebResult adminGetPermission(@RequestParam("account") String account,
                                 @RequestParam("examine") int examine,
                                 @RequestParam("delete") int delete,
                                 @RequestParam("manageAdmin") int manageAdmin,
                                 HttpServletRequest request){
    return adminOP.getPermission(account, examine, delete, manageAdmin,request);
  }

}
