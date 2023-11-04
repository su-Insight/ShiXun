package com.example.insight.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author Insight
 * @since 2023-11-03
 */
@Getter
@Setter
@ApiModel(value = "Article对象", description = "文章")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @Alias("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("标题")
    @Alias("标题")
    private String name;

    @ApiModelProperty("内容")
    @Alias("内容")
    private String content;

    @ApiModelProperty("作者")
    @Alias("作者")
    private String user;

    @ApiModelProperty("发布时间")
    @Alias("发布时间")
    private String time;


}
