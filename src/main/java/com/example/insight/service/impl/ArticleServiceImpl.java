package com.example.insight.service.impl;

import com.example.insight.entity.Article;
import com.example.insight.mapper.ArticleMapper;
import com.example.insight.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author Insight
 * @since 2023-11-03
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
