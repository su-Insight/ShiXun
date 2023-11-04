package com.example.insight.mapper;

import com.example.insight.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author Insight
 * @since 2023-11-02
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from role where flag = #{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}
