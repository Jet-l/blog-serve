package com.example.blogserve.controller;

import cn.hutool.core.map.MapUtil;
import com.example.blogserve.model.dto.ArticleDTO;
import com.example.blogserve.model.entity.ArticlePO;
import com.example.blogserve.model.vo.ArticleVO;
import com.example.blogserve.model.vo.PageVO;
import com.example.blogserve.model.vo.Results;
import com.example.blogserve.model.vo.TimelineVO;
import com.example.blogserve.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController

public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public Results<PageVO> getArticles(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        return Results.ok(articleService.getArticles(page, limit));
    }

    @PostMapping("/addArticle")
    public Results<ArticlePO> add(@RequestBody @Valid ArticleDTO articleDTO) {
        String id = articleService.addArticle(articleDTO);
        return Results.ok(MapUtil.of("id",id));
    }

    @GetMapping("/article/{id}")
    public Results<ArticleVO> getArticle(@PathVariable String id){
        ArticleVO articleVO = articleService.findById(id);
        return Results.ok(articleVO);
    }

    @DeleteMapping("/article/{id}")
    public Results deleteArticle(@PathVariable String id){
        articleService.deleteArticle(id);
        return Results.ok("删除成功",null);
    }

    @PutMapping("/article/{id}")
    public Results<Map<String,Object>> putArticle(@RequestBody ArticleDTO articleDTO,
                                                     @PathVariable String id){
        articleService.updateArticle(articleDTO,id);
        return Results.ok("更新成功",MapUtil.of("id",id));

    }

    @GetMapping("/timeline")
    public Results<List<TimelineVO>> getTimeline(){
        List<TimelineVO> timelineVOS = articleService.timeline();
        return Results.ok(timelineVOS);
    }

}
