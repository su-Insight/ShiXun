package com.example.insight.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.insight.common.Constants;
import com.example.insight.common.Result;
import com.example.insight.entity.Role;
import com.example.insight.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author Insight
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping
    public List<Role> findAll(){
        return roleService.list();
    }

     @GetMapping("/{id}")
     public Role findOne(@PathVariable Integer id){
         return roleService.getById(id);
     }

    @PostMapping
    public Result save(@RequestBody Role role){
        try{
            if(roleService.saveOrUpdate(role)){
                return Result.success();
            }else{
                return Result.error(Constants.CODE_600, "保存失败");
            }
        }catch (Exception e){
            return Result.error(Constants.CODE_600, "角色已存在");
        }

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        if(roleService.removeById(id)){
        return Result.success();
        }else{
        return Result.error(Constants.CODE_600, "删除失败");
        }
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(roleService.removeByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String content) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", content);
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
    /*
     * @Author Insight
     * @Date 2023/11/2 下午3:59
     * @Description 绑定角色和菜单的关系
     * @Param [roleId, menuIds]
     * @Return com.example.insight.common.Result
     * @Since version 1.0
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return Result.success(roleService.getRoleMenu(roleId));
    }
}

