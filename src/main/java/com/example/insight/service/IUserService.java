package com.example.insight.service;

import com.example.insight.common.Result;
import com.example.insight.controller.dto.UserDTO;
import com.example.insight.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Insight
 * @since 2023-10-28
 */
public interface IUserService extends IService<User> {

    UserDTO login(User user);

    Result register(User user);
}
