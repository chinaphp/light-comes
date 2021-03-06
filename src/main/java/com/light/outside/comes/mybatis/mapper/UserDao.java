package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.admin.UsersModel;
import com.light.outside.comes.qbkl.model.UserModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by b3st9u on 16/10/16.
 */
public interface UserDao {
    @Select("select id,real_name,head_img,user_name,`password`,`status` from comes_admin_users where user_name=#{user_name} and `password`=#{password}")
    public UsersModel queryUserByPwd(@Param("user_name") String user_name, @Param("password") String password);

    @Insert("insert into comes_admin_users(real_name,user_name,password,status,create_time)values(#{real_name},#{user_name},#{password},#{status},#{create_time})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long addUser(UsersModel userModel);

    @Update("update comes_admin_users set real_name=#{real_name},password=#{password},update_time=#{update_time} where id=#{id}")
    public void updateUser(UsersModel userModel);

    @Select("select * from comes_admin_users where user_name=#{user_name} limit 1")
    public UsersModel getUserByName(@Param("user_name") String user_name);


    @Select("select * from comes_admin_users where id=#{id}")
    public UsersModel getUserById(@Param("id") long id);

    @Select("select * from comes_admin_users limit  #{start},#{size}")
    public List<UsersModel> getUsers(@Param("start")int start,@Param("size")int size);

    @Select("delete  from comes_admin_users where id=#{id}")
    public void deletUser(@Param("id") long id);

    @Select("select count(1) from comes_admin_users")
    public int userTotal();
}
