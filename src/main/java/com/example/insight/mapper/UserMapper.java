package com.example.insight.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.insight.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// @Mapper
public interface UserMapper extends BaseMapper<User> {

    // @Select("select * from user")
    // List<User> findAll();
    //
    // @Insert("insert into user(username,password,nickname,email,phone,address) VALUES (#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    // int insert(User user);
    //
    //
    // int update(User user);
    //
    // @Delete("delete from user where id = #{id}")
    // Integer deleteById(@Param("id") Integer id);
    //
    // @Select("select count(*) from user")
    // Integer selectTotal();
    //
    // @Select("select * from user limit #{pageNum}, #{pageSize}")
    // List<User> selectPage(Integer pageNum, Integer pageSize);
    //
    // @Select("select * from user where username like concat('%', #{username}, '%') limit #{pageNum}, #{pageSize}")
    // List<User> selectPageByUserName(Integer pageNum, Integer pageSize, String username);
}
