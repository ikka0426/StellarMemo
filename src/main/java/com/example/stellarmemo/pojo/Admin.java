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
    public void setAdminID() { this.adminID = adminID; }

    public String getAdminName() { return adminName; }
    public void setAdminName() { this.adminName = adminName; }

    public String getAccount() { return account; }
    public void setAccount() { this.account = account; }

    public String getPassword() { return password; }
    public void setPassword() { this.password = password; }


}
