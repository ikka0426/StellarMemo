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

    Admin getByAccount(@Param("account") String account);

    String getPasswordByAccount(@Param("account") String account);

    void createAdmin(Admin admin);

    void updatePassword(@Param("account") String account,
                        @Param("NewPassword") String NewPassword);

    void setPermission(@Param("account") String account,
                       @Param("examine") int examine,
                       @Param("delete") int delete,
                       @Param("manageAdmin") int manageAdmin);

    int isPermittedExamine(@Param("account") String account);

    int isPermittedDelete(@Param("account") String account);

    String isPermittedManageAdmin(@Param("account") String account);
}
