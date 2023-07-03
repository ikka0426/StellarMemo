package com.example.stellarmemo.dao;

import com.example.stellarmemo.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminDAO {
    int getAdminNumberByAccount(@Param("account") String account);

    Admin getByPassword(@Param("account") String account,
                       @Param("password") String password);

    Admin getByAccount(@Param("account") String account);

    String getPasswordByAccount(@Param("account") String account);

    void createAdmin(Admin admin);

    void deleteAdmin(@Param("account") String account);

    void updatePassword(@Param("account") String account,
                        @Param("NewPassword") String NewPassword);

    void setPermission(@Param("account") String account,
                       @Param("examine") String examine,
                       @Param("delete") String delete,
                       @Param("manage") String manageAdmin);

    String isPermittedExamine(@Param("account") String account);

    String isPermittedDelete(@Param("account") String account);

    String isPermittedManageAdmin(@Param("account") String account);

    List<Admin> getAdminData(@Param("offset") int offset,
                            @Param("pageSize") int pageSize);

    int getAdminNum();

}
