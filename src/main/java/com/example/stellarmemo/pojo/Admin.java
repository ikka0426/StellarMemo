package com.example.stellarmemo.pojo;

public class Admin {
    String account;
    String password;
    String adminID;
    String adminName;

    public Admin(){}

    public Admin(String adminID, String adminName, String account, String password){
        this.adminID = adminID;
        this.adminName = adminName;
        this.account = account;
        this.password = password;
    }
    public String getAdminID(){
        return adminID;
    }
}
