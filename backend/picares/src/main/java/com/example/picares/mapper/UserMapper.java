package com.example.picares.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.picares.model.dto.user.UserQueryDTO;
import com.example.picares.model.entity.User;
import com.example.picares.model.vo.GetUserVO;
import com.example.picares.model.vo.LoginUserVO;

public interface UserMapper {

    @Insert("insert user (userName,userAccount,userPassword) values(#{userName},#{userAccount},#{userPassword})")
    void register(User user);

    @Select("select * from user where userAccount=#{userAccount} and userPassword=#{userPassword} and isDelete=0")
    User login(String userAccount, String userPassword);

    @Select("select * from user where id=#{id} and isDelete=0")
    LoginUserVO selectById(Long id);

    void updateUser(User user);

    @Update("update user set isDelete=1 where id=#{id}")
    void deleteUser(Long id);

    List<GetUserVO> selectUser(UserQueryDTO userQueryDTO);

    int selectUserCount(UserQueryDTO userQueryDTO);
}
