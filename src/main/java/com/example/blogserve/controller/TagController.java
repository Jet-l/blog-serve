package com.example.blogserve.controller;



import com.example.blogserve.model.vo.ArticleVO;
import com.example.blogserve.model.vo.PageVO;
import com.example.blogserve.model.vo.Results;
import com.example.blogserve.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController

public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public Results<Set<String>> getTags(){
        return Results.ok(tagService.getAllTags());
    }

    @GetMapping("/tag/{name}")
    public Results<PageVO<ArticleVO>> getArticle(@PathVariable("name") String tagName,
                                                 @RequestParam(required = false,defaultValue = "1") Integer page,
                                                 @RequestParam(required = false,defaultValue = "5") Integer limit){
        PageVO<ArticleVO> pageVO = tagService.getArticleByTag(tagName,page,limit);
        return Results.ok(pageVO);
    }

}
