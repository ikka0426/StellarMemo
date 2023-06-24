package com.example.stellarmemo.pojo;

import java.util.Date;

public class User {
    private String user_id;
    private String user_name;
    private String account;
    private String password;
    private Date birth;
    private String gender;
    private String email;

    public User(){}

    public User(String user_id, String account, String password, String user_name, String email){
        this.user_id = user_id;
        this.user_name = user_name;
        this.account = account;
        this.password = password;
    }

    public String getUser_id(){return user_id;}
    public void setUser_id(){this.user_id = user_id;}

    public String getUser_name(){return user_name;}
    public void setUser_name(){this.user_name = user_name;}

    public String getAccount(){return account;}
    public void setAccount(){this.account = account;}

    public String getPassword(){return password;}
    public void setPassword(){this.password = password;}

    public Date getBirth(){return birth;}
    public void setBirth(){this.birth = birth;}

    public String getGender(){return gender;}
    public void setGender(){this.gender = gender;}
}
