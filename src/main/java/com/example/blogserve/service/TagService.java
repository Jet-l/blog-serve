package com.example.blogserve.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogserve.dao.ArticleMapper;
import com.example.blogserve.model.entity.ArticlePO;
import com.example.blogserve.model.vo.ArticleVO;
import com.example.blogserve.model.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagService {
    @Autowired
    private ArticleMapper articleMapper;

    public Set<String> getAllTags(){
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("tags");
        List<ArticlePO> articlePOS = articleMapper.selectList(wrapper);
        Set<String> tags = articlePOS.stream()
                .map(ArticlePO::getTags)
                .flatMap(s-> Arrays.stream(s.split(",")))
                .collect(Collectors.toSet());
        log.info("tags : {}",tags);
        return tags.isEmpty()? new HashSet<>() : tags;
    }

    //根据标签查询
    public PageVO<ArticleVO> getArticleByTag(String tagName,Integer page,Integer limit){
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select(ArticlePO.class,i->!"content".equals(i.getColumn()))
                .like("tags",tagName);
        Page<ArticlePO> res = articleMapper.selectPage(new Page<>(page,limit),wrapper);
        List<ArticleVO> articleVOS = res.getRecords().stream().map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = PageVO.<ArticleVO>builder()
                .records(articleVOS.isEmpty()? new ArrayList<>() : articleVOS)
                .total(res.getTotal())
                .current(res.getCurrent())
                .size(res.getSize())
                .build();
        return pageVO;
    }

}
