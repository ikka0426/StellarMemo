package com.example.stellarmemo.dao;

import com.example.stellarmemo.pojo.Admin;
import  com.example.stellarmemo.pojo.User;
import  org.apache.ibatis.annotations.Mapper;
import  org.apache.ibatis.annotations.Param;
import  org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserDao {

    int getUserNum();

    List<User> getUserData(@Param("offset") int offset,
                             @Param("pageSize") int pageSize);

    int getUserNumberByAccount(@Param("account") String account);

    User getByPassword(@Param("account") String account,
                       @Param("password") String password);

    void createUser(User user);

    void updatePassword(@Param("account") String account,
                        @Param("NewPassword") String NewPassword);
//    void configCount(@Param("OriginalPassword") String OriginalPassword,
//                     @Param("id") String id);

    void deleteUser(@Param("account") String account);

}
