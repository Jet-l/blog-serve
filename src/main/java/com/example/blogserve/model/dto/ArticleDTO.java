package com.example.blogserve.model.dto;

import cn.hutool.core.bean.BeanUtil;
import com.example.blogserve.model.entity.ArticlePO;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

@Data
public class ArticleDTO {
    @NotEmpty(message = "文章作者不能为空")

    private String author;
    @NotEmpty(message = "文章标题不能为空")

    private String title;
    @NotEmpty(message = "文章内容不能为空")

    private String content;
    @NotEmpty(message = "文章标签不能为空")

    private String tags;
    @Range(min = 0, max = 1, message = "文章类型必须为1或者0")

    private Integer type;
    @NotEmpty(message = "文章分类不能为空")

    private String category;
    @NotEmpty(message = "文章简介不能为空")

    private String tabloid;


    public ArticlePO toArticlePO(boolean isUpdate) {
        ArticlePO po = new Converter().convertToPO(this);
        po.setViews(isUpdate ? null : 0);
        po.setGmtCreate(isUpdate ? null : po.getGmtUpdate());
        return po;
    }

    private static class Converter implements IConverter<ArticleDTO, ArticlePO> {
        @Override
        public ArticlePO convertToPO(ArticleDTO articleDTO) {
            ArticlePO po = new ArticlePO();
            po.setGmtUpdate(System.currentTimeMillis());
            BeanUtil.copyProperties(articleDTO, po);
            return po;
        }
    }

}
