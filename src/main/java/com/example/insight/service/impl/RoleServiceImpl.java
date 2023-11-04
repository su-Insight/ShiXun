package com.example.insight.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.insight.entity.Menu;
import com.example.insight.entity.Role;
import com.example.insight.entity.RoleMenu;
import com.example.insight.mapper.RoleMapper;
import com.example.insight.mapper.RoleMenuMapper;
import com.example.insight.service.IMenuService;
import com.example.insight.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author Insight
 * @since 2023-11-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    IMenuService menuService;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        // 删除角色绑定关系
        roleMenuMapper.deletebyRoleId(roleId);
        // 把传来的菜单id数组传递到当前角色id上去
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for(Integer menuId : menuIds){
            Menu menu = menuService.getById(menuId);
            if(menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())){  // 二级菜单并且传来的menuId中没有父级的id
                // 上传父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
