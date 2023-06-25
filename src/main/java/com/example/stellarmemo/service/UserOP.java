package com.example.stellarmemo.service;

import com.example.stellarmemo.dao.UserDao;
import com.example.stellarmemo.pojo.IDSet;
import com.example.stellarmemo.pojo.User;
import com.example.stellarmemo.pojo.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UserOP {

  @Autowired UserDao userDao;

  public WebResult userLogin(String account, String password) {
    WebResult webResult = new WebResult();
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
        }
        System.out.println(account + webResult.getMessage());
      }
    } catch (Exception e) {
      webResult.error("访问数据库出错");
      System.out.println(webResult.getMessage());
    }
    return webResult;
  }

  public WebResult userRegister(String account, String password) {
    WebResult webResult = new WebResult();
    try {
      int num = userDao.getUserNumberByAccount(account);
      if (num == 0) {
        System.out.println("新帐号注册");
        User user = new User(IDSet.getShortUuid(), account, password, account);
        userDao.createUser(user);
        webResult.success("注册成功");
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

  public WebResult changePassword(String account, String password) {
    WebResult webResult = new WebResult();
    try {
      userDao.updatePassword(account, password);
      webResult.success("修改成功");
    } catch (Exception e) {
      webResult.error("修改失败");
      System.out.println(webResult.getMessage());
    }
    return webResult;
  }

  public WebResult submitNote(String imageSrc) {

    WebResult webResult = new WebResult();
      Date date = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
      String imageName = formatter.format(date);
    try {
      File target = new File("D:/IDEA/StellarMemo/src/main/resources/image/" + imageName + ".jpg");
      if (!target.exists()) {
        target.createNewFile();
      }
      File src = new File(imageSrc);

      FileInputStream fis = new FileInputStream(src);
      FileOutputStream fos = new FileOutputStream(target);

      int len = 0;
      byte[] data = new byte[20];
      while ((len = fis.read(data)) != -1) {
        fos.write(data, 0, len);
      }

      fis.close();
      fos.close();
    }catch (IOException e){
      System.out.println("上传失败");
      System.out.println(e.getMessage());
    }
    return webResult;
  }
}
