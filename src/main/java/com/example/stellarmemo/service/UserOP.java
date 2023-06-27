package com.example.stellarmemo.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.stellarmemo.dao.UserDao;
import com.example.stellarmemo.pojo.IDSet;
import com.example.stellarmemo.pojo.User;
import com.example.stellarmemo.pojo.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


@Service
public class UserOP {

  @Autowired UserDao userDao;
  private final MailSender mailSender;
  private final RedisTemplate<String, String> redisTemplate;

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public UserOP(RedisTemplate<String, String> redisTemplate, MailSender mailSender) {
    this.redisTemplate = redisTemplate;
    this.mailSender = mailSender;
  }

  public WebResult userLogin(String account, String password, HttpServletRequest request, HttpServletResponse response) {
    WebResult webResult = new WebResult();
    password = bCryptPasswordEncoder.encode(password);
    String userId = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie: cookies) {
        if (cookie.getName().equalsIgnoreCase("userCookie")) {
          userId = cookie.getValue();
        }
      }
      if (userId != null) {
        webResult.error("已登录" + userId);
        System.out.println(webResult.getMessage());
        return webResult;
      }
    }
    try {
      int num = userDao.getUserNumberByAccount(account);
      if (num == 0) {
        webResult.setMessage("账号不存在");
        webResult.setCode(-2);
        System.out.println(webResult.getMessage());
      } else {
        User user = userDao.getByPassword(account, password);
        if (user == null) {
          webResult.error("登陆失败");
        } else {
          webResult.success("登陆成功");
          String userJson = JSONUtil.toJsonStr(user);
          Cookie cookie = new Cookie("userCookie", user.getUser_id());
          cookie.setMaxAge(7 * 24 * 60 * 60);
          cookie.setPath("/");
          response.addCookie(cookie);
          redisTemplate.opsForValue().set("user/" + user.getUser_id(), userJson, 7, TimeUnit.DAYS);
          webResult.setData(user);
        }
        System.out.println(account + webResult.getMessage());
      }
    } catch (Exception e) {
      webResult.error("访问数据库出错");
      System.out.println(webResult.getMessage());
    }
    return webResult;
  }

  public WebResult getCode(String email) {
    WebResult webResult = new WebResult();
    try {
      String code = mailSender.contextLoads(email);
      redisTemplate.opsForValue().set(email, code, 10, TimeUnit.MINUTES);
      webResult.success("邮件发送成功");
      System.out.println(webResult.getMessage());
    } catch (Exception e) {
      webResult.error("获取验证码失败");
      System.out.println(e.getMessage());
    }
    return webResult;
  }

  public WebResult userRegister(String account, String password, String email, String code) {
    WebResult webResult = new WebResult();
    try {
      int num = userDao.getUserNumberByAccount(account);
      if (num == 0) {
        String savedCode = redisTemplate.opsForValue().get(email);
//        System.out.println(savedCode);
//        System.out.println(code);
        if (savedCode == null || !savedCode.equals(code)) {
          webResult.error("验证码错误");
          System.out.println(webResult.getMessage());
        } else {
          redisTemplate.delete(email);
          password = bCryptPasswordEncoder.encode(password);
          System.out.println(password.length());
          System.out.println("新帐号注册");
          User user = new User(IDSet.getShortUuid(), account, password, account);
          userDao.createUser(user);
          webResult.success("注册成功");
        }
      } else {
        webResult.error("账号" + account + "已被注册");
        System.out.println(webResult.getMessage());
      }
    } catch (Exception e) {
      webResult.error("访问数据出错");
      System.out.println(e.getMessage());
    }
    return webResult;
  }

  public WebResult changePassword(String account, String password, String code, String email) {
    WebResult webResult = new WebResult();
    try {
      String savedCode = redisTemplate.opsForValue().get(email);
      if (savedCode == null || !savedCode.equals(code)) {
        webResult.error("验证码错误");
        System.out.println(webResult.getMessage());
      } else {
        redisTemplate.delete(email);
        password = bCryptPasswordEncoder.encode(password);
        userDao.updatePassword(account, password);
        webResult.success("修改成功");
      }
    } catch (Exception e) {
      webResult.error("修改失败");
      System.out.println(webResult.getMessage());
    }
    return webResult;
  }

  public WebResult userLogout(HttpServletRequest request, HttpServletResponse response) {
    WebResult webResult = new WebResult();
    String userId = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie: cookies) {
        if (cookie.getName().equalsIgnoreCase("userCookie")) {
          userId = cookie.getValue();
        }
      }
      if (userId != null) {
        Cookie cookie = new Cookie("userCookie", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        redisTemplate.delete("user/" + userId);
        webResult.success("已登出" + userId);
        System.out.println(webResult.getMessage());
      } else {
        webResult.error("未登录");
      }
    } else {
      webResult.error("未登录");
    }
    return webResult;
  }

  @Value("${wx.appid}")
  private String appId;

  @Value("${wx.secret}")
  private String secret;

  public WebResult wxLogin(String code) {
    WebResult webResult = new WebResult();
    String authUrl = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";
    authUrl = authUrl + "&appid=" + appId + "&secret=" + secret + "&js_code=" + code;
    String result = HttpUtil.get(authUrl);
    System.out.println(code);
    JSONObject jsonObject = JSONUtil.parseObj(result);
    String openId = jsonObject.getStr("openid");
    webResult.setData(openId);
    return webResult;
  }

}
