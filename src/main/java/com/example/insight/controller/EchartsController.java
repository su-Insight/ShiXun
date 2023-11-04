package com.example.insight.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.insight.common.Result;
import com.example.insight.config.AuthAccess;
import com.example.insight.entity.User;
import com.example.insight.mapper.UserMapper;
import com.example.insight.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Insight
 * @Description: 返回ECharts数据
 * @Date: 2023/11/2 上午9:38
 * @Version: 1.0
 */
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String REDIS_KEY = "REDIS_KEY_TEST";

    @AuthAccess
    @GetMapping("/findAllUser")
    public Result getAll(){

        // 从缓存获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(REDIS_KEY);
        List<User> users;
        if(StrUtil.isBlank(jsonStr)){
            users = userMapper.selectList(null);    // 数据库中取出数据
            // 缓存到redis
            stringRedisTemplate.opsForValue().set(REDIS_KEY, JSONUtil.toJsonStr(users));
        }else{
            // 如果存在缓存，从redis缓存中取出数据
            users = JSONUtil.toBean(jsonStr, new TypeReference<List<User>>() {
            }, true);
        }
        return Result.success();
    }

    @AuthAccess
    @GetMapping("/test")
    public Result get(){
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Windows", "Mac", "Linux", "Android", "Browser", "Others"));
        map.put("y", CollUtil.newArrayList(1,5,6,9,8,10));
        return Result.success(map);
    }
}
