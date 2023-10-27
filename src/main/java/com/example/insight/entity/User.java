package com.example.insight.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.javafx.beans.IDProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// 无参构造
@NoArgsConstructor
// 有参构造
@AllArgsConstructor
// 注明数据库名，默认按照类名查找
@TableName(value = "user")
public class User {
    // 指明主键信息，value指明数据库字段名称，因此属性名和数据库字段名称可以不相同
    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;
    private String username;
    // 忽略密码字段，不展示在前端
    @JsonIgnore
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    // 指明非主键信息，value指明数据库字段名称
    @TableField(value = "avatar_url")
    // 驼峰是默认的，即private String avatarUrl无需配置TableField也可
    private String avatar;


}
