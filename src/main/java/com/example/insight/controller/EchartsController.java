package com.example.insight.controller;

import cn.hutool.core.collection.CollUtil;
import com.example.insight.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @GetMapping("/test")
    public Result get(){
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Search Engine", "Direct", "Email", "Union Ads", "Video Ads"));
        map.put("y", CollUtil.newArrayList(1,5,6,9,8));
        return Result.success(map);
    }
}
