package com.example.insight.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/11/2 下午3:56
 * @Version: 1.0
 */
@TableName("privileges")
@Data
public class RoleMenu {
    private Integer roleId;
    private Integer menuId;
}
