package com.example.insight.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.insight.entity.File;
import com.example.insight.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/11/1 上午11:19
 * @Version: 1.0
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {
}
