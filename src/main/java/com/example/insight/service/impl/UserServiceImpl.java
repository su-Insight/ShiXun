package com.example.insight.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.insight.common.Constants;
import com.example.insight.common.Result;
import com.example.insight.controller.dto.UserDTO;
import com.example.insight.entity.Menu;
import com.example.insight.entity.User;
import com.example.insight.exception.ServiceException;
import com.example.insight.mapper.RoleMapper;
import com.example.insight.mapper.RoleMenuMapper;
import com.example.insight.mapper.UserMapper;
import com.example.insight.service.IMenuService;
import com.example.insight.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.insight.utils.TokenUtils;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;
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

            // 用户身份处理
            String role = user.getRole();

            // 设置用户菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
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


    /*
     * @Author Insight
     * @Date 2023/11/2 下午9:11
     * @Description 获得当前角色的菜单列表
     * @Param [roleFlag]
     * @Return java.util.List<com.example.insight.entity.Menu>
     * @Since version 1.0
     */
    public List<Menu> getRoleMenus(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色所有的角色菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出所有系统菜单
        List<Menu> menus = menuService.findMenus("");
        // 最终筛选结果
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前角色菜单
        for(Menu menu : menus){
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // 移除children里面不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
