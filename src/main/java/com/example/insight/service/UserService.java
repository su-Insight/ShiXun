package com.example.insight.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.insight.entity.User;
import com.example.insight.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    public UserMapper userMapper;
    public boolean saveUser(User user){
        // if (user.getId() == null){
        //     return save(user);  // mybatis-plus提供的方法，表示插入数据
        // }else {
        //     return updateById(user);
        // }
        return saveOrUpdate(user);
    }
}
