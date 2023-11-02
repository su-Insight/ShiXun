package com.example.insight.controller.dto;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/10/29 下午4:47
 * @Version: 1.0
 */
@Data
@ToString
public class UserDTO {
    private Integer id;
    @Alias("用户名")
    private String username;
    @Alias("昵称")
    private String nickname;
    @Alias("邮箱")
    private String email;
    @Alias("手机号")
    private String phone;
    @Alias("地址")
    private String address;
    @Alias("头像")
    private String avatarUrl;
    private String token;
}
