package com.example.stellarmemo.controller;

import com.example.stellarmemo.service.AdminOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired private AdminOP adminOP;

}
