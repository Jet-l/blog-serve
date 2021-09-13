package com.example.blogserve;


import cn.hutool.core.util.*;
import com.example.blogserve.dao.ArticleMapper;
import com.example.blogserve.model.entity.ArticlePO;
import com.example.blogserve.service.ArticleService;
import lombok.extern.slf4j.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;


import java.time.*;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BlogServeApplication.class})
public class ArticleServiceTest {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;


    @Test
    public void solveTime() {
        List<ArticlePO> articles = articleMapper.selectList(null);
        for (int i = 0; i < articles.size(); i++) {
            ArticlePO po = articles.get(i);
            LocalDateTime dateTime = LocalDateTime.now().plusYears(RandomUtil.randomInt(-5, 5));
            long epochMilli = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            po.setGmtCreate(epochMilli);
            po.setGmtUpdate(epochMilli);
            articleMapper.updateById(po);
        }
    }

    @Test
    public void timeline() {
        articleService.timeline();
    }
}