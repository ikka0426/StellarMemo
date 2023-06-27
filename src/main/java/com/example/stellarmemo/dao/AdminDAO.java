package com.example.stellarmemo.dao;

import com.example.stellarmemo.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminDAO {
    int getAdminNumberByAccount(@Param("account") String account);

    Admin getByPassword(@Param("account") String account,
                       @Param("password") String password);

    void createAdmin(Admin admin);

    void updatePassword(@Param("account") String account,
                        @Param("NewPassword") String NewPassword);

    void setPermission(@Param("account") String account,
                       @Param("examine") int examine,
                       @Param("delete") int delete);
}
