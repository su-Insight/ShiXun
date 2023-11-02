package com.example.insight.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.insight.common.Constants;
import com.example.insight.common.Result;
import com.example.insight.controller.dto.UserDTO;
import com.example.insight.entity.User;
import com.example.insight.exception.ServiceException;
import com.example.insight.mapper.UserMapper;
import com.example.insight.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.insight.utils.TokenUtils;
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
    private static final Log LOG = Log.get();

    @Override
    public UserDTO login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        UserDTO userDTO = new UserDTO();
        try{
            user = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if(user != null){
            BeanUtil.copyProperties(user, userDTO);
            // 设置token
            String token = TokenUtils.generateToken(user.getId().toString(), user.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public Result register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        try{
            user = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if(user != null){
            return Result.error(Constants.CODE_600, "该用户名已存在，请更换其他用户名");
        }else {
            return Result.success();
        }
    }
}
