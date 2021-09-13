package com.example.blogserve.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.setting.Setting;
import com.example.blogserve.exception.BlogException;
import com.example.blogserve.model.dto.UserDTO;
import com.example.blogserve.model.enums.ErrorInfoEnum;
import com.example.blogserve.model.enums.UserRoleEnum;
import com.example.blogserve.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserService {
    @Autowired
    private Setting setting;

    // 校验
    public String checkUsernamePassword(UserDTO userDTO){
        String username = setting.getStr("username");
        String password = setting.getStr("password");
        if (Objects.equals(username,userDTO.getUsername())&&
                Objects.equals(password, MD5.create().digestHex(userDTO.getPassword()))){
            return JwtUtils.createToken(username, CollUtil.newArrayList(UserRoleEnum.ADMIN.getValue()));
        }else {
            throw new BlogException(ErrorInfoEnum.USERNAME_PASSWORD_ERROR);
        }
    }
}
