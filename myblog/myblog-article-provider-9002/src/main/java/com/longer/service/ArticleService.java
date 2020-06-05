package com.longer.service;

import com.longer.entities.Article;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    /**
     * 插入文章信息
     * @param content String
     * @param author String 账号
     */
    Map<String,Object> insertArticle(String title, String content, String author, String textContent, String coverImage, int state) throws IOException;

    /**
     * 查询文章、用于社区全部文章列表、查询文章、
     * @param title String
     * @param content String
     * @param time int
     * @param state int
     * @param curPage int
     * @param showCount int
     * @auth longer
     * @date 2020/4/24
     * */
    Map<String,Object> selectArticle(int id, String title, String content, int time, int browse, int state, int curPage, int showCount);

    /**
     * 查询文章、用于个人文章列表、查询文章、草稿箱
     *
     * @param title   String
     * @param author  String
     * @param time    int
     * @param state   int
     * @auth longer
     * @date 2020/4/28
     */
    Map<String,Object> selectArticleByAccountOrTitle(String title, String author, int time, int browse, int state, int curPage, int showCount);

    /**
     * 文章的修改
     * @param title String
     * @param content String
     * @param textContent String
     * @param coverImage String
     * @param id int
     * @param state int
     * @author longer
     * @date 2020/4/26
     * */
    Map<String, Object>  updateArticle(String title, String content, String textContent, String coverImage, int id, String author, int state);

    /**
     * 文章的下架
     * @param author       String
     * @param id          int
     * @author longer
     * @date 2020/4/27
     */
    Map<String, Object> updateArticleStateByAuthorAndId(String author, int id, int state);
    /**
     * 文章的删除
     * @param author       String
     * @param id          int
     * @author longer
     * @date 2020/4/27
     */
    Map<String, Object> deleteArticleStateByAuthorAndId(String author, int id);

    /**
     * 文章浏览数+1
     * @param id int
     * @author lobger
     * @date 2020/4/30
     * */
    Map<String, Object> updateArticleBrowseAddById(int id);

}
