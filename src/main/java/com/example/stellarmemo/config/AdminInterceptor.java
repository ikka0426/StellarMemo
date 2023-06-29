package com.example.stellarmemo.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String origin = request.getHeader("Origin");
    if (origin != null && origin.equals("http://localhost:8085")) {
      return true;
    } else if (origin != null && origin.equals("http://localhost:8080") && request.getRequestURI().startsWith("/admin")) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return false;
    }
    return true;
  }

}
