package com.example.stellarmemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Random;

@Component("myMailSender")
public class MailSender {

  @Autowired
  JavaMailSenderImpl mailSender;

  public String contextLoads(String mailAddress) throws MessagingException {
    System.out.println("test");
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    String code = "";
    Random random = new Random();
    int[] tmp = new int[3];
    for (int i = 0; i < 6; ++i) {
      int num = random.nextInt(10) + 48;
      int lower = random.nextInt(26) + 65;
      int upper = random.nextInt(26) + 97;
      tmp[0] = num;
      tmp[1] = lower;
      tmp[2] = upper;
      code += (char)tmp[random.nextInt(3)];
    }
    System.out.println("验证码：" + code);
    helper.setSubject("您的验证码为：" + code);
    helper.setText("您好！您的验证码为：" + "<h2>" + code + "</h2>");
    helper.setTo(mailAddress);
    helper.setFrom("stellarmemo@qq.com");
    mailSender.send(mimeMessage);
    System.out.println("邮件发送成功");
    return code;
  }

}
