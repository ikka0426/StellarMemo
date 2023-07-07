package com.example.stellarmemo.service;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.example.stellarmemo.dao.AdminDAO;
import com.example.stellarmemo.dao.NoteDao;
import com.example.stellarmemo.pojo.Admin;
import com.example.stellarmemo.pojo.IDSet;
import com.example.stellarmemo.pojo.User;
import com.example.stellarmemo.pojo.WebResult;
import com.example.stellarmemo.config.RedisConfig;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AdminOP {
  @Autowired AdminDAO adminDAO;
  private final RedisTemplate<String, String> redisTemplate;

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public AdminOP(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  private String isLogin(HttpServletRequest request) {
    String adminId = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie: cookies) {
        if (cookie.getName().equalsIgnoreCase("adminCookie")) {
          adminId = cookie.getValue();
        }
      }
    }
    return adminId;
  }

  public WebResult checkLoginStatus(HttpServletRequest request) {
    WebResult webResult = new WebResult();
    try {
      webResult.setData(isLogin(request) != null);
      webResult.success("获取成功");
    } catch (Exception e) {
      webResult.error("获取失败");
    }
    return webResult;
  }

  public WebResult getAccount(HttpServletRequest request) {
    WebResult webResult = new WebResult();
    try {
      webResult.setData(JSONUtil.parseObj(redisTemplate.opsForValue().get("admin/" + isLogin(request))).getStr("account"));
      webResult.success("成功");
    } catch (Exception e) {
      webResult.error("失败");
    }
    return webResult;
  }

  public WebResult login(String account, String password, HttpServletRequest request, HttpServletResponse response) {
    WebResult webResult = new WebResult();
    String adminId = isLogin(request);
    if (adminId != null) {
      webResult.error("已登录");
      return webResult;
    }
    try {
      int num = adminDAO.getAdminNumberByAccount(account);
      if (num == 0) {
        webResult.error("管理员不存在");
        webResult.setCode(-2);
        System.out.println(webResult.getMessage());
      } else {
        String savedPassword = adminDAO.getPasswordByAccount(account);
        if (bCryptPasswordEncoder.matches(password, savedPassword)) {
          Admin admin = adminDAO.getByAccount(account);
          Cookie cookie = new Cookie("adminCookie", admin.getAdminID());
          cookie.setMaxAge(24 * 60 * 60);
          cookie.setPath("/");
          response.addCookie(cookie);
          String adminJson = JSONUtil.toJsonStr(admin);
          System.out.println(adminJson);
          redisTemplate.opsForValue().set("admin/" + admin.getAdminID(), adminJson, 1, TimeUnit.DAYS);
          webResult.setData(admin);
          webResult.success("管理员登陆成功");
          System.out.println(webResult.getMessage());
        } else {
          webResult.error("管理员密码错误");
          System.out.println(webResult.getMessage());
        }
      }
    } catch (Exception e) {
      webResult.error("登陆失败");
      System.out.println(e.getMessage());
    }
    return webResult;
  }

  public WebResult register(String account, String password, String name, HttpServletRequest request) {
    WebResult webResult = new WebResult();
    password = bCryptPasswordEncoder.encode(password);
    String adminId = isLogin(request);
    if (adminId == null) {
      webResult.error("未登录");
      return webResult;
    }
    String adminAccount = JSONUtil.parseObj(redisTemplate.opsForValue().get("admin/" + adminId)).getStr("account");
    if ("1".equals(adminDAO.isPermittedManageAdmin(adminAccount))) {
      try {
        int num = adminDAO.getAdminNumberByAccount(account);
        if (num == 0) {
          System.out.println("新管理员注册");
          Admin admin = new Admin(IDSet.getShortUuid(), name, account, password);
          adminDAO.createAdmin(admin);
          webResult.success("注册成功");
        }
        else {
          webResult.error("管理员账号" + account + "已被注册");
          System.out.println(webResult.getMessage());
        }
      } catch (Exception e) {
        webResult.error("访问数据出错");
        System.out.println(e.getMessage());
      }
    } else {
      webResult.error("无权限");
    }
    return webResult;
  }

  public WebResult deleteAdmin(String account, HttpServletRequest request) {
    WebResult webResult = isPermittedManage(request);
    if ((boolean) webResult.getData()) {
      try {
        adminDAO.deleteAdmin(account);
        webResult.success("删除成功");
      } catch (Exception e) {
        webResult.error("删除失败");
      }
    }
    return webResult;
  }

  public WebResult setPermission(String account, String examine, String delete, String manage, HttpServletRequest request){
    WebResult webResult = isPermittedManage(request);
    if ((boolean) webResult.getData()) {
      try {
        adminDAO.setPermission(account, examine, delete, manage);
        webResult.success("修改成功");
      } catch (Exception e) {
        webResult.error("增加权限失败");
        System.out.println(e.getMessage());
      }
    } else {
      webResult.error("无权限");
    }
    return webResult;
  }

  public WebResult logout(HttpServletRequest request, HttpServletResponse response) {
    WebResult webResult = new WebResult();
    String adminId = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie: cookies) {
        if (cookie.getName().equalsIgnoreCase("adminCookie")) {
          adminId = cookie.getValue();
        }
      }
      if (adminId != null) {
        Cookie cookie = new Cookie("adminCookie", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        redisTemplate.delete("admin/" + adminId);
        webResult.success("已登出管理员" + adminId);
      } else {
        webResult.error("未登录");
      }
    } else {
      webResult.error("未登录");
    }
    System.out.println(webResult.getMessage());
    return webResult;
  }

  public WebResult isPermittedManage(HttpServletRequest request) {
    WebResult webResult = new WebResult();
    String adminId = isLogin(request);
    if (adminId == null) {
      webResult.error("未登录");
      return webResult;
    }
    String adminAccount = JSONUtil.parseObj(redisTemplate.opsForValue().get("admin/" + adminId)).getStr("account");
    webResult.success();
    webResult.setData("1".equals(adminDAO.isPermittedManageAdmin(adminAccount)));
    return webResult;
  }

  public WebResult isPermittedExamine(HttpServletRequest request) {
    WebResult webResult = new WebResult();
    String adminId = isLogin(request);
    if (adminId == null) {
      webResult.error("未登录");
      return webResult;
    }
    String adminAccount = JSONUtil.parseObj(redisTemplate.opsForValue().get("admin/" + adminId)).getStr("account");
    webResult.success();
    webResult.setData("1".equals(adminDAO.isPermittedExamine(adminAccount)));
    return webResult;
  }

  public WebResult isPermittedDelete(HttpServletRequest request) {
    WebResult webResult = new WebResult();
    String adminId = isLogin(request);
    if (adminId == null) {
      webResult.error("未登录");
      return webResult;
    }
    String adminAccount = JSONUtil.parseObj(redisTemplate.opsForValue().get("admin/" + adminId)).getStr("account");
    webResult.success();
    webResult.setData("1".equals(adminDAO.isPermittedDelete(adminAccount)));
    return webResult;
  }

  private final long startTime = System.currentTimeMillis();

  public WebResult getSetupTime() {
    WebResult webResult = new WebResult();
    webResult.setData(startTime);
    webResult.success();
    return webResult;
  }

  public WebResult getAdminData(int offset, int pageSize) {
    WebResult webResult = new WebResult();
    List<Admin> adminList = adminDAO.getAdminData(offset, pageSize);
    List<JSONObject> retList = new ArrayList<>();
    for (Admin admin: adminList) {
      JSONObject json = JSONUtil.parseObj(admin);
      json.put("examine", "1".equals(adminDAO.isPermittedExamine(admin.getAccount())));
      json.put("delete", "1".equals(adminDAO.isPermittedDelete(admin.getAccount())));
      json.put("manage", "1".equals(adminDAO.isPermittedManageAdmin(admin.getAccount())));
      retList.add(json);
    }
    webResult.setData(retList);
    webResult.success();
    return webResult;
  }

  public WebResult getAdminNum() {
    WebResult webResult = new WebResult();
    webResult.setData(adminDAO.getAdminNum());
    webResult.success();
    return webResult;
  }

}
