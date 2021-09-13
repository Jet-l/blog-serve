package com.example.blogserve.controller;

import com.example.blogserve.exception.BlogException;
import com.example.blogserve.model.enums.ErrorInfoEnum;
import com.example.blogserve.model.vo.BlogInfoVO;
import com.example.blogserve.model.vo.Results;
import com.example.blogserve.service.CommService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class CommController {

    @Autowired
    private CommService commService;

    @GetMapping("/ping")
    public Results ping() {
        //throw new BlogException(ErrorInfoEnum.MISSING_PARAMETERS);
        return Results.ok("欢迎访问Blog API", null);
    }


    @GetMapping("/info")
    public Results<BlogInfoVO> info(){
        BlogInfoVO blogInfoVO = commService.getBlogInfo();
        return Results.ok(blogInfoVO);
    }

}
