package com.example.insight.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/10/31 下午11:01
 * @Version: 1.0
 */
@Getter
@Setter
@ApiModel(value = "User对象", description = "")
@ToString
@NoArgsConstructor
public class File {
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件名称")
    @Alias("文件名称")
    private String name;

    @ApiModelProperty("文件类型")
    @Alias("文件类型")
    private String type;

    @ApiModelProperty("文件大小(kb)")
    @Alias("文件大小(kb)")
    private String size;

    @ApiModelProperty("下载链接")
    @Alias("下载链接")
    private String url;

    @ApiModelProperty("文档描述")
    @Alias("文档描述")
    private String des;

    @ApiModelProperty("是否删除")
    @Alias("是否删除")
    private String isDelete;

    @ApiModelProperty("是否禁用链接")
    @Alias("是否禁用链接")
    private String enable;

    public File(String name, String type, String size, String url, String des) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.url = url;
        this.des = des;
    }
}
