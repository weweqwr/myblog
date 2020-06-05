package com.longer.service;

import com.longer.entities.Article;
import com.longer.fallback.ArticleServiceFallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@FeignClient(value = "MYBLOG-PROVIDER-ARTICLE/article/",fallbackFactory = ArticleServiceFallback.class)
public interface ArticleService {


    /**
     * 插入文章信息
     * @param article Article
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value = "insertArticle",method = RequestMethod.POST)
    public Map<String,Object> insertArticle(@RequestBody Article article);
    /**
     * 查询文章、用于社区全部文章列表、查询文章
     * @param title String
     * @param time int
     * @param state int
     * @param curPage int
     * @param showCount int
     * @auth longer
     * @date 2020/4/24
     * */
    @RequestMapping(value = "selectArticle",method = RequestMethod.GET)
    public Map<String, Object> selectArticle(@RequestParam(value = "id")int id,
                                             @RequestParam(value = "title")String title,
                                             @RequestParam(value = "time")int time,
                                             @RequestParam(value = "browse")int browse,
                                             @RequestParam(value = "state")int state,
                                             @RequestParam(value = "curPage")int curPage,
                                             @RequestParam(value = "showCount")int showCount);
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
    @RequestMapping(value = "selectArticleByAccountOrTitle",method = RequestMethod.GET)
    public Map<String, Object> selectArticleByAccountOrTitle(@RequestParam(value = "title")String title,
                                                             @RequestParam(value = "author")String author,
                                                             @RequestParam(value = "time")int time,
                                                             @RequestParam(value = "browse")int browse,
                                                             @RequestParam(value = "state")int state,
                                                             @RequestParam(value = "curPage")int curPage,
                                                             @RequestParam(value = "showCount")int showCount);
    /**
     * 文章的修改
     * @param article Article
     * @author longer
     * @date 2020/4/26
     * */
    @RequestMapping(value = "updateArticle",method = RequestMethod.POST)
    public Map<String, Object>  updateArticle(@RequestBody Article article);

    /**
     * 文章的下架
     * @param article       Article
     * @author longer
     * @date 2020/4/27
     */
    @RequestMapping(value = "updateArticleStateByAuthorAndId",method = RequestMethod.POST)
    public Map<String, Object> updateArticleStateByAuthorAndId(@RequestBody Article article);

    /**
     * 文章的删除
     * @param article       Article
     * @author longer
     * @date 2020/4/27
     */
    @RequestMapping(value = "deleteArticleStateByAuthorAndId",method = RequestMethod.POST)
    public Map<String, Object> deleteArticleStateByAuthorAndId(@RequestBody Article article);

    /**
     * 文章浏览数+1
     * @param article Article
     * @author lobger
     * @date 2020/4/30
     * */
    @RequestMapping(value = "updateArticleBrowseAddById",method = RequestMethod.POST)
    public Map<String, Object> updateArticleBrowseAddById(@RequestBody Article article);
}
