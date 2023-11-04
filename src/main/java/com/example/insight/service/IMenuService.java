package com.example.insight.service;

import com.example.insight.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author Insight
 * @since 2023-11-02
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String content);
}
