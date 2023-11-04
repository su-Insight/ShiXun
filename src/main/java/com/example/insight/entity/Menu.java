package com.example.insight.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author Insight
 * @since 2023-11-02
 */
@Getter
@Setter
@ApiModel(value = "Menu对象", description = "菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @Alias("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("姓名")
    @Alias("姓名")
    private String name;

    @ApiModelProperty("路径")
    @Alias("路径")
    private String path;

    @ApiModelProperty("图标")
    @Alias("图标")
    private String icon;

    @ApiModelProperty("描述")
    @Alias("描述")
    private String description;

    @ApiModelProperty("pid")
    @Alias("pid")
    private Integer pid;

    @ApiModelProperty("页面路径")
    @Alias("页面路径")
    private String pagePath;

    @TableField(exist = false)
    private List<Menu> children;

}
