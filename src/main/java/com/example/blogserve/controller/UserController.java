package com.example.blogserve.controller;

import cn.hutool.core.map.MapUtil;
import com.example.blogserve.constants.JwtConstants;
import com.example.blogserve.model.dto.UserDTO;
import com.example.blogserve.model.vo.Results;
import com.example.blogserve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping("/login")
    public Results<Map<String,Object>> login(@RequestBody @Valid UserDTO userDTO){
        String token = userService.checkUsernamePassword(userDTO);
        return Results.ok("登录成功", MapUtil.of("token",token));
    }

    @PostMapping("/auth/logout")
    public Results<Object> logout(@RequestAttribute("token") String token){
        redisTemplate.opsForSet().add(JwtConstants.REDIS_KEY,token);
        return Results.ok("注销成功",null);
    }

}
