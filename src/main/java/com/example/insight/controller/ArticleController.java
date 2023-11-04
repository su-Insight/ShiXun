package com.example.insight.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.insight.common.Constants;
import com.example.insight.common.Result;
import com.example.insight.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.insight.service.IArticleService;
import com.example.insight.entity.Article;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author Insight
 * @since 2023-11-03
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @GetMapping
    public List<Article> findAll(){
        return articleService.list();
    }

     @GetMapping("/{id}")
     public Article findOne(@PathVariable Integer id){
         return articleService.getById(id);
     }

    @PostMapping
    public Result save(@RequestBody Article article){
        if(article.getId() == null){
            article.setTime(DateUtil.now());
            article.setUser(TokenUtils.getCurrentUser().getNickname());
        }
        articleService.saveOrUpdate(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        if(articleService.removeById(id)){
        return Result.success();
        }else{
        return Result.error(Constants.CODE_600, "删除失败");
        }
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(articleService.removeByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage( @RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String content) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(content)){
            queryWrapper.eq("name", content);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(articleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

