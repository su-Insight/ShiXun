package com.example.insight.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.annotation.Alias;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author Insight
 * @since 2023-11-02
 */
@Getter
@Setter
@ApiModel(value = "Role对象", description = "角色")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @Alias("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名称")
    @Alias("名称")
    private String name;

    @ApiModelProperty("描述")
    @Alias("描述")
    private String description;

    @ApiModelProperty("唯一标识")
    @Alias("唯一标识")
    private String flag;


}
