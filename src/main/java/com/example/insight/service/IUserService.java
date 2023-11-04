package com.example.insight.service;

import com.example.insight.common.Result;
import com.example.insight.controller.dto.UserDTO;
import com.example.insight.entity.Menu;
import com.example.insight.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    List<Menu> getRoleMenus(String role);
}
