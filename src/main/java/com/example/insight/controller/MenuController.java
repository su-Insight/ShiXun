package com.example.insight.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.insight.common.Constants;
import com.example.insight.common.Result;
import com.example.insight.entity.Dict;
import com.example.insight.entity.Role;
import com.example.insight.mapper.DictMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.example.insight.service.IMenuService;
import com.example.insight.entity.Menu;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author Insight
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    DictMapper dictMapper;

    @Resource
    private IMenuService menuService;

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String content){
        return Result.success(menuService.findMenus(content));
    }

    @GetMapping("/ids")
    public Result findAllIds(){
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

     @GetMapping("/{id}")
     public Menu findOne(@PathVariable Integer id){
         return menuService.getById(id);
     }

    @PostMapping
    public Result save(@RequestBody Menu menu){
        if(menuService.saveOrUpdate(menu)){
             return Result.success();
        }else{
             return Result.error(Constants.CODE_600, "保存失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        if(menuService.removeById(id)){
        return Result.success();
        }else{
        return Result.error(Constants.CODE_600, "删除失败");
        }
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(menuService.removeByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage( @RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String content) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", content);
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }
}

