package com.longer.controller;

import com.longer.entities.Article;
import com.longer.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 插入文章信息
     * @param article Article
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value = "insertArticle",method = RequestMethod.POST)
    Map<String,Object> insertArticle(@RequestBody Article article){
        Map<String,Object> map=new HashMap<>();
        try {
            map=articleService.insertArticle(article.getTitle(),article.getContent(),article.getAuthor(),article.getTextContent(),article.getCoverImage(),article.getState());
        }catch (Exception e) {
            map.put("result","系统代码报错");
            map.put("flag",0);
        }
        return map;
    }
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
    public Map<String, Object> selectArticle(int id,String title,int time,int browse, int state,int curPage,int showCount) {
        Map<String,Object> map=new HashMap<>();
        try {
            map=articleService.selectArticle(id,title,title,time,browse,state,curPage,showCount);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","查询失败");
            map.put("flag",0);
        }
        return map;
    }
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
    public Map<String, Object> selectArticleByAccountOrTitle(String title, String author, int time, int browse, int state,int curPage,int showCount) {
        Map<String,Object> map=new HashMap<>();
        try {
            map=articleService.selectArticleByAccountOrTitle(title,author,time,browse,state,curPage,showCount);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","查询失败");
            map.put("flag",0);
        }
        return map;
    }

    /**
     * 文章的修改
     * @param article Article
     * @author longer
     * @date 2020/4/26
     * */
    @RequestMapping(value = "updateArticle",method = RequestMethod.POST)
    public Map<String, Object>  updateArticle(@RequestBody Article article) {
        Map<String, Object> map=new HashMap<>();
        try {
            map=articleService.updateArticle(article.getTitle(),article.getContent(),article.getTextContent(),article.getCoverImage(),article.getId(),article.getAuthor(),article.getState());
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","系统繁忙");
            map.put("result",0);
        }
        return map;
    }

    /**
     * 文章的下架
     * @param article       Article
     * @author longer
     * @date 2020/4/27
     */
    @RequestMapping(value = "updateArticleStateByAuthorAndId",method = RequestMethod.POST)
    public Map<String, Object> updateArticleStateByAuthorAndId(@RequestBody Article article) {
        Map<String,Object> map=new HashMap<>();
       try {
           map=articleService.updateArticleStateByAuthorAndId(article.getAuthor(),article.getId(),article.getState());
       }catch (Exception e){
           e.printStackTrace();
           map.put("result",false);
           map.put("flag",0);
       }

        return map;
    }

    /**
     * 文章的删除
     * @param article       Article
     * @author longer
     * @date 2020/4/27
     */
    @RequestMapping(value = "deleteArticleStateByAuthorAndId",method = RequestMethod.POST)
    public Map<String, Object> deleteArticleStateByAuthorAndId(@RequestBody Article article) {
        Map<String,Object> map=new HashMap<>();
       try {
           map=articleService.deleteArticleStateByAuthorAndId(article.getAuthor(),article.getId());
       }catch (Exception e){
           e.printStackTrace();
           map.put("result",false);
           map.put("flag",0);
       }
        return map;
    }

    /**
     * 文章浏览数+1
     * @param article Article
     * @author lobger
     * @date 2020/4/30
     * */
    @RequestMapping(value = "updateArticleBrowseAddById",method = RequestMethod.POST)
    public Map<String, Object> updateArticleBrowseAddById(@RequestBody Article article) {
        Map<String, Object> map=new HashMap<>();
        try{
            map=articleService.updateArticleBrowseAddById(article.getId());
        }catch (Exception e){
            map.put("result",false);
            map.put("flag",1);
            e.printStackTrace();
        }
        return  map;
    }
}
