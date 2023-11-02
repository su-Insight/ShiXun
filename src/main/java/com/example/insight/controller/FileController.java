package com.example.insight.controller;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.insight.common.Constants;
import com.example.insight.common.Result;
import com.example.insight.entity.File;
import com.example.insight.entity.User;
import com.example.insight.service.FileService;
import com.example.insight.service.IUserService;
import com.example.insight.utils.NumberUtil;
import com.example.insight.utils.OssUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/10/30 下午10:02
 * @Version: 1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private static final Log LOG = Log.get();

    @Resource
    OssUtils ossUtils;

    @Resource
    IUserService userService;

    @Resource
    FileService fileService;

    /*
     * @Author Insight
     * @Date 2023/10/31 下午12:50
     * @Description 上传文件
     * @Param [file]
     * @Return com.example.insight.common.Result
     * @Since version 1.0
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file, File fileInfo) throws IOException {
        try{
            String filename = file.getOriginalFilename();
            String filePath = ossUtils.uploadFile(filename, file.getInputStream());
            File newFile = new File(fileInfo.getName(), file.getContentType(), NumberUtil.getNoMoreThanTwoDigits((double) file.getSize() /1024), filePath, fileInfo.getDes());
            fileService.save(newFile);
            return Result.success();
        }catch (Exception e){
            LOG.error(e);
            return Result.error(Constants.CODE_600, "系统错误，请重新上传");
        }
    }

    @PostMapping
    private Result save(@RequestBody File file){
        try{
            fileService.updateById(file);
            return Result.success();
        }catch (Exception e){
            LOG.error(e);
            return Result.error(Constants.CODE_600, file.getEnable().equals("1") ? "启用失败，请重试" : "禁用失败，请重试");
        }
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(fileService.removeByIds(ids));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(fileService.removeById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String content) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", content);
        queryWrapper.orderByDesc("id");
        return Result.success(fileService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}
