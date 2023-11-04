package com.example.insight.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/11/2 下午3:16
 * @Version: 1.0
 */
@TableName("dict")
@Data
public class Dict {
    private String name;
    private String value;
    private String type;
}
