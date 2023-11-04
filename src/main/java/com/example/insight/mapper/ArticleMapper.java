package com.example.insight.mapper;

import com.example.insight.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章 Mapper 接口
 * </p>
 *
 * @author Insight
 * @since 2023-11-03
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
