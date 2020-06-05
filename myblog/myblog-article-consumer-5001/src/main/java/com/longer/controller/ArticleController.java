package com.longer.controller;

import com.longer.entities.Article;
import com.longer.entities.Reply;
import com.longer.service.ArticleService;
import com.longer.service.UserService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    private static final String REST_URL_PREFIX = "http://MYBLOG-PROVIDER-ARTICLE/article/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ArticleService articleService;

    /**
     * 插入文章信息
     *
     * @param article Article
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value = "insertArticle", method = RequestMethod.POST)
    public Map<String,Object>  insertArticle(@RequestBody Article article,HttpServletRequest request) {

        return this.articleService.insertArticle(article);
    }

    /**
     * 查询文章、用于社区全部文章列表、查询文章
     *
     * @param title String
     * @param time  int
     * @param state int
     * @auth longer
     * @date 2020/4/24
     */
    @RequestMapping(value = "selectArticle", method = RequestMethod.GET)
    public Map<String,Object>  selectArticle(@RequestParam(value = "id") int id,
                                             @RequestParam(value = "title") String title,
                                             @RequestParam(value = "time") int time,
                                             @RequestParam(value = "browse") int browse,
                                             @RequestParam(value = "state") int state,
                                             @RequestParam(value = "curPage") int curPage,
                                             @RequestParam(value = "showCount") int showCount,
                                             HttpServletRequest request) {
        return this.articleService.selectArticle(id,title,time,browse,state,curPage,showCount);
    }

    /**
     * 查询文章、用于个人文章列表、查询文章、草稿箱
     *
     * @param title  String
     * @param author String
     * @param time   int
     * @param state  int
     * @auth longer
     * @date 2020/4/28
     */
    @RequestMapping(value = "selectArticleByAccountOrTitle", method = RequestMethod.GET)
    public Map<String,Object>  selectArticleByAccountOrTitle(@RequestParam(value = "title") String title,
                                                             @RequestParam(value = "author") String author,
                                                             @RequestParam(value = "time") int time,
                                                             @RequestParam(value = "browse") int browse,
                                                             @RequestParam(value = "state") int state,
                                                             @RequestParam(value = "curPage") int curPage,
                                                             @RequestParam(value = "showCount") int showCount) {
        System.out.println("到这里了？");
        return this.articleService.selectArticleByAccountOrTitle(title,author,time,browse,state,curPage,showCount);
    }

    /**
     * 文章的修改
     *
     * @param article Article
     * @author longer
     * @date 2020/4/26
     */
    @RequestMapping(value = "updateArticle", method = RequestMethod.POST)
    public Map<String,Object>  updateArticle(@RequestBody Article article) {
        return this.articleService.updateArticle(article);
    }

    /**
     * 文章的下架
     *
     * @param article Article
     * @author longer
     * @date 2020/4/27
     */
    @RequestMapping(value = "updateArticleStateByAuthorAndId", method = RequestMethod.POST)
    public Map<String,Object>  updateArticleStateByAuthorAndId(@RequestBody Article article, HttpServletRequest request) {
      return this.articleService.updateArticleStateByAuthorAndId(article);
    }

    /**
     * 文章的删除
     *
     * @param article Article
     * @author longer
     * @date 2020/4/27
     */
    @RequestMapping(value = "deleteArticleStateByAuthorAndId", method = RequestMethod.POST)
    public Map<String,Object>  deleteArticleStateByAuthorAndId(@RequestBody Article article, HttpServletRequest request) {
        return this.articleService.deleteArticleStateByAuthorAndId(article);
    }

    /**
     * 文章浏览数+1
     *
     * @param article Article
     * @author lobger
     * @date 2020/4/30
     */
    @RequestMapping(value = "updateArticleBrowseAddById", method = RequestMethod.POST)
    public Map<String,Object>  updateArticleBrowseAddById(@RequestBody Article article,HttpServletRequest request) {
        return this.articleService.updateArticleBrowseAddById(article);

    }



}
