package com.example.insight.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.insight.entity.Menu;
import com.example.insight.mapper.MenuMapper;
import com.example.insight.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author Insight
 * @since 2023-11-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus(String content) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(content)){
            queryWrapper.like("name", content);
        }
        List<Menu> menus = list(queryWrapper);
        // 找到pid为null的一级菜单
        List<Menu> parentNodes = menus.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for(Menu menu : parentNodes){
            menu.setChildren(menus.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
