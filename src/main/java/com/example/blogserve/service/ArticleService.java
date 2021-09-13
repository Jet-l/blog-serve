package com.example.blogserve.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogserve.dao.ArticleMapper;
import com.example.blogserve.exception.BlogException;
import com.example.blogserve.model.dto.ArticleDTO;
import com.example.blogserve.model.entity.ArticlePO;
import com.example.blogserve.model.vo.ArticleVO;
import com.example.blogserve.model.vo.PageVO;
import com.example.blogserve.model.vo.TimelineVO;
import com.example.blogserve.utils.BlogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.blogserve.model.enums.ErrorInfoEnum.INVALID_ID;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public PageVO<ArticleVO> getArticles(int page, int limit) {
        QueryWrapper<ArticlePO> qw = new QueryWrapper<>();
        qw.select(ArticlePO.class, i -> !"content".equals(i.getColumn()));
        Page<ArticlePO> res = articleMapper.selectPage(new Page<>(page, limit), qw);
        List<ArticleVO> articleVOS = res.getRecords().stream()
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = PageVO.<ArticleVO>builder()
                .records(articleVOS.isEmpty() ? new ArrayList<>() : articleVOS)
                .total(res.getTotal())
                .current(res.getCurrent())
                .size(res.getSize())
                .build();
        return pageVO;
    }

    public String addArticle(ArticleDTO articleDTO) {
        ArticlePO po = articleDTO.toArticlePO(false);
        String id = IdUtil.objectId();
        po.setId(id);
        articleMapper.insert(po);
        return id;
    }

    public ArticleVO findById(String id) {
        ArticlePO po = articleMapper.selectById(id);
        if (Objects.isNull(po)) {
            throw new BlogException(INVALID_ID);
        }
        po.setViews(po.getViews() + 1);
        articleMapper.updateById(po);
        return ArticleVO.fromArticlePO(po);
    }

    public void deleteArticle(String id) {
        int i = articleMapper.deleteById(id);
        if (i <= 0) {
            throw new BlogException(INVALID_ID);
        }
    }

    public void updateArticle(ArticleDTO articleDTO, String id) {
        ArticlePO po = articleMapper.selectById(id);
        if (Objects.isNull(po)) {
            throw new BlogException(INVALID_ID);
        }
        ArticlePO articlePO = articleDTO.toArticlePO(true);
        articlePO.setId(id);
        articleMapper.updateById(articlePO);
    }

    public List<TimelineVO> timeline(){
        ArrayList<TimelineVO> res = new ArrayList<>();
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("id","title","gmt_create");
        List<Map<String,Object>> maps = articleMapper.selectMaps(wrapper);
        maps.stream().map(map -> TimelineVO.Item.builder()
                .id((String) map.get("id"))
                .gmtCreate(BlogUtils.formatDatetime((Long) map.get("gmt_create")))
                .title((String) map.get("title"))
                .build())
                .collect(Collectors.groupingBy(item -> {
                    String[] arr = item.getGmtCreate().split("-");
                    String year = arr[0];
                    return year;
                })).forEach((k,v)-> res.add(TimelineVO.builder().year(k).items(v).build()));
        res.sort(Comparator.comparing(TimelineVO::getYear).reversed());
        return  res;
    }
}

