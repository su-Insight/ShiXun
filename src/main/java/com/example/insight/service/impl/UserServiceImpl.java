package com.example.insight.service.impl;

import com.example.insight.entity.User;
import com.example.insight.mapper.UserMapper;
import com.example.insight.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Insight
 * @since 2023-10-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
