package com.example.blogserve.service;

import cn.hutool.core.convert.*;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogserve.dao.ArticleMapper;
import com.example.blogserve.model.comm.BlogSetting;
import com.example.blogserve.model.entity.ArticlePO;
import com.example.blogserve.model.vo.BlogInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private Setting setting;

    public BlogInfoVO getBlogInfo(){
        BlogSetting blogSetting = BlogSetting.fromSetting(setting);
        BlogInfoVO blogInfoVO = BlogInfoVO.fromBlogSetting(blogSetting);
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("sum(views) as total_views");
        List<Map<String,Object>> maps = articleMapper.selectMaps(wrapper);
        int totalViews = 0;
        if (!maps.isEmpty()){
            totalViews = Convert.toInt(maps.get(0).get("total_views"),0);

        }
        Integer articleCount = articleMapper.selectCount(null);
        blogInfoVO.setTotalViews(totalViews);
        blogInfoVO.setArticleCount(articleCount);
        return blogInfoVO;
    }
}
