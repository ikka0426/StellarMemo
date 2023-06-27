package com.example.stellarmemo.service;

import cn.hutool.json.JSONUtil;
import com.example.stellarmemo.dao.AdminDAO;
import com.example.stellarmemo.pojo.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

  public WebResult login(String account, String password, HttpServletRequest request, HttpServletResponse response) {
    WebResult webResult = new WebResult();
    password = bCryptPasswordEncoder.encode(password);
    String adminId = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie: cookies) {
        if (cookie.getName().equalsIgnoreCase("adminCookie")) {
          adminId = cookie.getValue();
        }
      }
      if (adminId != null) {
        webResult.error("已登录管理员：" + adminId);
        return webResult;
      }
    }
    try {
      int num = adminDAO.getAdminNumberByAccount(account);
      if (num == 0) {
        webResult.error("管理员不存在");
        webResult.setCode(-2);
      } else {
        Admin admin = adminDAO.getByPassword(account, password);
        if (admin == null) {
          webResult.error("管理员密码错误");
        } else {
          Cookie cookie = new Cookie("adminCookie", admin.getAdminID());
          cookie.setMaxAge(24 * 60 * 60);
          cookie.setPath("/");
          response.addCookie(cookie);
          String adminJson = JSONUtil.toJsonStr(admin);
          redisTemplate.opsForValue().set("admin/" + admin.getAdminID(), adminJson, 1, TimeUnit.DAYS);
          webResult.setData(admin);
          webResult.success("管理员登陆成功");
        }
      }
    } catch (Exception e) {
      webResult.error("登陆失败");
      System.out.println(e.getMessage());
    }
    return webResult;
  }

}
