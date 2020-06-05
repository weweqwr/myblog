package com.longer.mapperDao;

import com.longer.entities.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDao {
    /**
     * 插入文章信息
     *
     * @param content    String
     * @param author     String 账号
     * @param createTime Stirng
     * @param state      int
     * @author longer
     * @date 2020/4/24
     */
    boolean insertArticle(@Param("title") String title, @Param("content") String content, @Param("author") String author, @Param("createTime") String createTime, @Param("textContent") String textContent, @Param("coverImage") String coverImage, @Param("state") int state);

    /**
     * 查询文章、用于社区全部文章列表、查询文章
     *
     * @param title   String
     * @param content String
     * @param time    int
     * @param state   int
     * @param curPage int
     * @param showCount int
     * @auth longer
     * @date 2020/4/24
     */
    List<Article> selectArticle(@Param("id") int id, @Param("title") String title, @Param("content") String content, @Param("time") int time, @Param("browse") int browse, @Param("state") int state, @Param("curPage") int curPage, @Param("showCount") int showCount);
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
    List<Article> selectArticleByAccountOrTitle(@Param("title") String title, @Param("author") String author, @Param("time") int time, @Param("browse") int browse, @Param("state") int state, @Param("curPage") int curPage, @Param("showCount") int showCount);
    /**
     * 文章的修改
     * @param title       String
     * @param content     String
     * @param textContent String
     * @param coverImage  String
     * @param id          int
     * @author longer
     * @date 2020/4/26
     */
    boolean updateArticle(@Param("title") String title, @Param("content") String content, @Param("textContent") String textContent, @Param("coverImage") String coverImage, @Param("id") int id, @Param("author") String author, @Param("state") int state);


    /**
     * 文章的下架
     * @param author  String
     * @param id      int
     * @param state   int
     * @author longer
     * @date 2020/4/27
     */
    boolean updateArticleStateByAuthorAndId(@Param("author") String author, @Param("id") int id, @Param("state") int state);
    /**
     * 文章的删除
     * @param author       String
     * @param id          int
     * @author longer
     * @date 2020/4/27
     */
    boolean deleteArticleStateByAuthorAndId(@Param("author") String author, @Param("id") int id);

    /**
     * 文章浏览数+1
     * 最近访客+1
     * @param id int
     * @author lobger
     * @date 2020/4/30
     * */
    boolean updateArticleBrowseAddById(@Param("id") int id);




}
