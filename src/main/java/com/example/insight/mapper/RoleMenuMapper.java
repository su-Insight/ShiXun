package com.example.insight.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.insight.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/11/2 下午3:57
 * @Version: 1.0
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Delete("delete from privileges where role_id = #{roleId}")
    int deletebyRoleId(@Param("roleId") Integer roleId);


    @Select("select menu_id from privileges where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}
