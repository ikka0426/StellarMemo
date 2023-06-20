package com.example.stellarmemo.service;

import com.example.stellarmemo.dao.UserDao;
import com.example.stellarmemo.pojo.IDSet;
import com.example.stellarmemo.pojo.User;
import com.example.stellarmemo.pojo.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserOP {

  @Autowired UserDao userDao;

  public WebResult UserLogin(String account, String password) {
    WebResult webResult = new WebResult();
    try {
      int num = userDao.getUserNumberByAccount(account);
      if (num == 0) {
        System.out.println("账号不存在");

      } else {

      }
    } catch (Exception e) {
      webResult.error("访问数据库出错");
      System.out.println(webResult.GetMessage);
    }
    return webResult;
  }


}
