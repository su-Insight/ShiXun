package com.example.insight.mapper;

import com.example.insight.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Insight
 * @since 2023-10-28
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
