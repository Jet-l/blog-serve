package com.example.blogserve.controller;

import com.example.blogserve.model.vo.ArticleVO;
import com.example.blogserve.model.vo.CategoryVO;
import com.example.blogserve.model.vo.PageVO;
import com.example.blogserve.model.vo.Results;
import com.example.blogserve.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public Results<List<CategoryVO>> getCategories(){
        return Results.ok(categoryService.getAllCategories());
    }

    @GetMapping("/category/{name}")
    public Results<PageVO<ArticleVO>> getArticle(@PathVariable("name") String categoryName,
                                                 @RequestParam(required = false,defaultValue = "1") Integer page,
                                                 @RequestParam(required = false,defaultValue = "5") Integer limit){
        PageVO<ArticleVO> pageVO = categoryService.getArticleByCategory(categoryName,page,limit);
        return Results.ok(pageVO);
    }

}
