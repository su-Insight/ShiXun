package com.example.insight.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * /
 * </p>
 *
 * @author Insight
 * @since 2023-10-28
 */
@Getter
@Setter
@ApiModel(value = "User对象", description = "")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      @Alias("用户名")
      private String username;

      @ApiModelProperty("密码")
      @Alias("密码")
      private String password;

      @ApiModelProperty("昵称")
      @Alias("昵称")
      private String nickname;

      @ApiModelProperty("邮箱")
      @Alias("邮箱")
      private String email;

      @ApiModelProperty("电话")
      @Alias("电话")
      private String phone;

      @ApiModelProperty("地址")
      @Alias("地址")
      private String address;

      @ApiModelProperty("创建时间")
      @Alias("用户名")
      private LocalDateTime createTime;

      @ApiModelProperty("头像url")
      @Alias("头像url")
      private String avatarUrl;


}
