package com.example.blogserve.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogserve.model.entity.ArticlePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticlePO> {
}
