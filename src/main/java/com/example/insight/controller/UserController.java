package com.example.insight.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.log.Log;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.insight.common.Constants;
import com.example.insight.common.Result;
import com.example.insight.controller.dto.UserDTO;
import com.example.insight.utils.OssUtils;
import com.example.insight.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLIntegrityConstraintViolationException;
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
    private static final Log LOG = Log.get();

    @Autowired
    OssUtils ossUtils;

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.login(user));
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(user));
    }

    @GetMapping
    public Result findAll(){
        return Result.success(userService.list());
    }

     @GetMapping("/{id}")
     public Result findOne(@PathVariable Integer id){
         return Result.success(userService.getById(id));
     }

    @PostMapping
    public Result save(@RequestBody User user, HttpServletRequest request){
        try {
            System.out.println(user);
            userService.saveOrUpdate(user);
            // 保存成功后返回保存的新用户
            UserDTO userDTO = new UserDTO();
            BeanUtil.copyProperties(userService.getById(user.getId()), userDTO);
            userDTO.setToken(request.getHeader("token"));
            return Result.success(userDTO);
        }catch (Exception e){
            return Result.error(Constants.CODE_600, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
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
        User current = TokenUtils.getCurrentUser();
        System.out.println(current.getUsername());
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 自定义标题别名
        // writer.addHeaderAlias("username", "用户名");
        // writer.addHeaderAlias("password", "密码");
        // writer.addHeaderAlias("nickname", "昵称");
        // writer.addHeaderAlias("email", "邮箱");
        // writer.addHeaderAlias("phone", "电话");
        // writer.addHeaderAlias("address", "地址");
        // writer.addHeaderAlias("createTime", "创建时间");
        // writer.addHeaderAlias("avatarUrl", "头像");

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
    public Result imp(MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            List<User> list = reader.readAll(User.class);
            userService.saveBatch(list);
            return Result.success();
        }catch (Exception e){
            return Result.error(Constants.CODE_600, "存在重复用户名，请更改后重新上传");
        }
    }

    /*
     * @Author Insight
     * @Date 2023/10/31 下午12:50
     * @Description 上传用户头像
     * @Param [file]
     * @Return com.example.insight.common.Result
     * @Since version 1.0
     */
    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        try{
            String filename = file.getOriginalFilename();
            String filePath = ossUtils.upload(filename, file.getInputStream());
            // 更新用户信息
            User current = TokenUtils.getCurrentUser();
            current.setAvatarUrl(filePath);
            System.out.println(current);
            userService.updateById(current);
            UserDTO userDTO = new UserDTO();
            BeanUtil.copyProperties(current, userDTO);
            userDTO.setToken(request.getHeader("token"));
            return Result.success(userDTO);
        }catch (Exception e){
            LOG.error(e);
            return Result.error(Constants.CODE_600, "系统错误，请重新上传");
        }
    }
}

