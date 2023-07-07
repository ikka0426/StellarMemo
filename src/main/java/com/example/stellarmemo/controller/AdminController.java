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

  @RequestMapping("/checkLoginStatus")
  public WebResult checkLoginStatus(HttpServletRequest request) {
    return adminOP.checkLoginStatus(request);
  }

  @RequestMapping("/getAccount")
  public WebResult getAccount(HttpServletRequest request) {
    return adminOP.getAccount(request);
  }

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

  @RequestMapping("/deleteAdmin")
  public WebResult deleteAdmin(@RequestParam("account") String account, HttpServletRequest request) {
    return adminOP.deleteAdmin(account, request);
  }

  //获取管理员权限
  @RequestMapping("/setPermission")
  public WebResult adminSetPermission(@RequestParam("account") String account,
                                 @RequestParam("examine") String examine,
                                 @RequestParam("delete") String delete,
                                 @RequestParam("manage") String manage,
                                 HttpServletRequest request) {
    return adminOP.setPermission(account, examine, delete, manage, request);
  }

  @RequestMapping("/isPermittedManage")
  public WebResult isPermittedManage(HttpServletRequest request) {
    return adminOP.isPermittedManage(request);
  }

  @RequestMapping("/isPermittedExamine")
  public WebResult isPermittedExamine(HttpServletRequest request) {
    return adminOP.isPermittedExamine(request);
  }

  @RequestMapping("/isPermittedDelete")
  public WebResult isPermittedDelete(HttpServletRequest request) {
    return adminOP.isPermittedDelete(request);
  }
  @RequestMapping("/getSetupTime")
  public WebResult getSetupTime() {
    return adminOP.getSetupTime();
  }

  @RequestMapping("/getAdminData")
  public WebResult getAdminData(@RequestParam("offset") int offset,
                                @RequestParam("pageSize") int pageSize) {
    return adminOP.getAdminData(offset, pageSize);
  }

  @RequestMapping("/getAdminNum")
  public WebResult getAdminNum() {
    return adminOP.getAdminNum();
  }

}
