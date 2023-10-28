package com.example.insight.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.example.insight.service.IUserService;
import com.example.insight.entity.User;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Insight
 * @since 2023-10-28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping
    public List<User> findAll(){
        return userService.list();
    }

     @GetMapping("/{id}")
     public User findOne(@PathVariable Integer id){
         return userService.getById(id);
     }

    @PostMapping
    public boolean save(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
    }

    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String content,
                                @RequestParam(defaultValue = "") String category) {
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
        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        System.out.println(111);
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");

        // 一次性写出list内的对象到Excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式设置
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        System.out.println("导出用户信息完成！");
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public void imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);
    }
}

