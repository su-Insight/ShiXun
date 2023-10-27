package com.example.insight.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.insight.entity.User;
import com.example.insight.mapper.UserMapper;
import com.example.insight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*
     * @Author Insight
     * @Date 2023/10/26 下午3:27
     * @Description 查找所有用户
     * @Param []
     * @Return java.util.List<com.example.insight.entity.User>
     * @Since version 1.0
     */
    @GetMapping
    public List<User> index(){
        return userService.list();
    }

    /*
     * @Author Insight
     * @Date 2023/10/26 下午3:27
     * @Description 添加或动态更新用户信息
     * @Param [user]
     * @Return java.lang.Integer
     * @Since version 1.0
     */
    @PostMapping
    public boolean save(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    /*
     * @Author Insight
     * @Date 2023/10/26 下午3:33
     * @Description This is description of method
     * @Param [id]  ...:8088/user/5  删除id=5用户
     * @Return java.lang.Integer
     * @Since version 1.0
     */
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

    /*
     * @Author Insight
     * @Date 2023/10/27 下午6:07
     * @Description 批量删除
     * @Param [ids]
     * @Return boolean
     * @Since version 1.0
     */
    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
    }
    /*
     * @Author Insight
     * @Date 2023/10/26 下午3:57
     * @Description 手写分页查询
     * @Param [pageNum, pageSize]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Since version 1.0
     */
    // @GetMapping("/page-handle")
    // public Map<String, Object> findPageHandle(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username){
    //     pageNum = (pageNum - 1) * pageSize;
    //     Integer total = userMapper.selectTotal();
    //     // 直接分页查询
    //     // List<User> users =  userMapper.selectPage(pageNum, pageSize);
    //     // 通过用户名模糊查询进行分页查询
    //     List<User> users =  userMapper.selectPageByUserName(pageNum, pageSize, username);
    //     Map<String, Object> res = new HashMap<>();
    //     res.put("data", users);
    //     res.put("total", total);
    //     return res;
    // }

    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String content,
                                @RequestParam(defaultValue = "") String category) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (category.equals("all")){
            queryWrapper.like("username", content);
            queryWrapper.or().like("nickname", content);
            queryWrapper.or().like("email", content);
            queryWrapper.or().like("phone", content);
            queryWrapper.or().like("address", content);
        }else {
            queryWrapper.like(category, content);
        }
        queryWrapper.orderByDesc("id");
        return userService.page(page, queryWrapper);
    }
}
