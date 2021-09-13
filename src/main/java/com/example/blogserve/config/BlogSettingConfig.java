package com.example.blogserve.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import com.example.blogserve.model.comm.BlogSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Slf4j
public class BlogSettingConfig {
    @Value("${setting.path}")
    private String settingFilePath;


    @Bean
    public Setting setting(){
        File file = new File(settingFilePath);
        Setting setting;
        if(!file.exists()){
            setting = new Setting(file, CharsetUtil.CHARSET_UTF_8,false);
            setting.set("title","blog");
            setting.set("desc","asdfgfghhj");
            setting.set("covers","https://tvax1.sinaimg.cn/large/bfe05ea9ly1fxgunx09dtj21hc0u0q81.jpg");
            setting.set("avatar","https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");
            setting.set("nikname","sakura");
            setting.store(file.getAbsolutePath());

        }
        setting = new Setting(file,CharsetUtil.CHARSET_UTF_8,false);
        setting.autoLoad(true);
        //log.info(BlogSetting.fromSetting(setting).toString());
        return setting;
    }

}
