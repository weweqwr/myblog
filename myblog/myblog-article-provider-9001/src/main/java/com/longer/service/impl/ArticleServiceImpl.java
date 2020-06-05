package com.longer.service.impl;

import com.longer.entities.Article;
import com.longer.entities.OBSEntity;
import com.longer.mapperDao.ArticleDao;
import com.longer.service.ArticleService;
import com.longer.utils.Base64Util;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Resource(name = "redisRedis1Template")
    private RedisTemplate redisRedis1Template;
    @Resource(name = "redisRedis2Template")
    private RedisTemplate redisRedis2Template;

    /**
     * 插入文章信息
     *
     * @param content String
     * @param author  String 账号
     * @author longer
     * @date 2020/4/24
     */
    @Override
    public Map<String, Object> insertArticle(String title, String content, String author, String textContent, String coverImage, int state) {
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String createTime = format.format(new Date());

        //上传文章的封面
        if (coverImage != "" && coverImage != null) {
            try {
                OBSEntity obsEntity = new OBSEntity();
                String endPoint = obsEntity.getEndPoint();
                String ak = obsEntity.getAk();
                String sk = obsEntity.getSk();
                ObsClient obsClient = new ObsClient(ak, sk, endPoint);
                String ObsPath = "myBlog/" + author + "/article" + "/coverImage" + "/";
                //base64不能出现 data:image/png;base64,要去掉以下步骤去除
                String[] s=coverImage.split(";");
                String[] ss=coverImage.split(",");
                Base64Util bs = new Base64Util();
                //将base64转为file
                File file = bs.base64ToFile(ss[1], "author.jpg");
                //生成时间戳,作为文件夹区分头像文件
                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmSSss");
                String creteTime = format1.format(new Date());
                PutObjectResult result = obsClient.putObject("longlongago", ObsPath + creteTime + ".jpg", file);
                //获取云存储对象路径
                coverImage = result.getObjectUrl();
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //删除redis缓存防止redis数据不更新
        redisRedis1Template.delete(author + "articleListDraft");
        redisRedis1Template.delete(author + "articleList");
        boolean results = articleDao.insertArticle(title, content, author, createTime, textContent, coverImage, state);
        map.put("result", results);
        map.put("flag", 1);
        return map;
    }

    /**
     * 查询文章、用于社区全部文章列表、个人文章、查询文章、草稿箱
     *
     * @param title   String
     * @param content String
     * @param time    int
     * @param state   int
     * @auth longer
     * @date 2020/4/24
     */
    @Override
    public Map<String, Object> selectArticle(int id,String title, String content, int time, int browse, int state,int curPage,int showCount) {
        Map<String, Object> map = new HashMap<>();
        List<Article> articleList =new ArrayList<>();
        //文章详情从redis中获取
        if(id==0){
            articleList = articleDao.selectArticle(id,title, content, time, browse, state,curPage,showCount);
        }else{
            articleList= (List<Article>) redisRedis2Template.opsForValue().get("article"+id);
            if(articleList==null){//如果没有这条数据，则从mysql获取
                articleList= articleDao.selectArticle(id,title, content, time, browse, state,curPage,showCount);
                redisRedis1Template.opsForValue().set("article"+id,articleList);
            }
        }
        map.put("result", articleList);
        map.put("flag", 1);
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
     */    @Override
    public Map<String, Object> selectArticleByAccountOrTitle(String title, String author, int time, int browse, int state,int curPage,int showCount) {
        Map<String, Object> map = new HashMap<>();
        List<Article> articleList = new ArrayList<>();

        //从redis中取数据
        if(title==""||title==null) {
            if (state == 1) { //我的文章列表
                articleList = (List<Article>) redisRedis2Template.opsForHash().get(author + "articleList","articleList"+state+curPage);
            } else if (state == 0 ) {//草稿列表
                articleList = (List<Article>) redisRedis2Template.opsForHash().get(author + "articleListDraft", "articleListDraft"+state+curPage);
            }
            //判断是否能在redis中取到数据,取不到从mysql中取
            if (articleList == null) {
                articleList = articleDao.selectArticleByAccountOrTitle(title, author, time, browse, state,curPage,showCount);
                //将数据存到redis中
                if (state == 1) { //我的文章列表
                    redisRedis1Template.opsForHash().put(author + "articleList","articleList"+state+curPage,articleList);
                    redisRedis1Template.expire(author + "articleList", 30, TimeUnit.DAYS);
                } else if (state == 0) {//草稿列表
                    redisRedis1Template.opsForHash().put(author + "articleListDraft", "articleListDraft"+state+curPage,articleList);
                    redisRedis1Template.expire(author + "articleList", 30, TimeUnit.DAYS);
                }
            }
        }else {
            articleList = articleDao.selectArticleByAccountOrTitle(title, author, time, browse, state,curPage,showCount);
        }
        map.put("result", articleList);
        map.put("flag", 1);
        return map;
    }

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
    @Override
    public Map<String, Object>  updateArticle(String title, String content, String textContent, String coverImage, int id,String author,int state) {
        Map<String, Object> map = new HashMap<>();
        //上传文章的封面
        if (coverImage != "" && coverImage != null) {
            try {
                OBSEntity obsEntity = new OBSEntity();
                String endPoint = obsEntity.getEndPoint();
                String ak = obsEntity.getAk();
                String sk = obsEntity.getSk();
                ObsClient obsClient = new ObsClient(ak, sk, endPoint);
                String ObsPath = "myBlog/" + author + "/article" + "/coverImage" + "/";
                //base64不能出现 data:image/png;base64,要去掉以下步骤去除
                String[] s = coverImage.split(";");
                String[] ss = coverImage.split(",");
                Base64Util bs = new Base64Util();
                //将base64转为file
                File file = bs.base64ToFile(ss[1], "author.jpg");
                //生成时间戳,作为文件夹区分头像文件
                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmSSss");
                String creteTime = format1.format(new Date());
                PutObjectResult result = obsClient.putObject("longlongago", ObsPath + creteTime + ".jpg", file);
                //获取云存储对象路径
                coverImage = result.getObjectUrl();
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //删除redis缓存防止redis数据不更新
        redisRedis1Template.delete("article"+id);
        redisRedis1Template.delete(author + "articleListDraft");
        redisRedis1Template.delete(author + "articleList");
        boolean result=articleDao.updateArticle(title,content,textContent,coverImage,id,author,state);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }

    /**
     * 文章的下架
     * @param author       String
     * @param id          int
     * @author longer
     * @date 2020/4/27
     */
    @Override
    public Map<String, Object> updateArticleStateByAuthorAndId(String author, int id,int state) {
        Map<String,Object> map=new HashMap<>();
        map.put("result",articleDao.updateArticleStateByAuthorAndId(author,id,state));
        //删除redis缓存防止redis数据不更新
        redisRedis1Template.delete("article"+id);
        redisRedis1Template.delete(author + "articleListDraft");
        redisRedis1Template.delete(author + "articleList");
        map.put("flag",1);
        return map;
    }

    /**
     * 文章的删除
     * @param author       String
     * @param id          int
     * @author longer
     * @date 2020/4/27
     */
    @Override
    public Map<String, Object> deleteArticleStateByAuthorAndId(String author, int id) {
        Map<String,Object> map=new HashMap<>();
        redisRedis1Template.delete("article"+id);
        redisRedis1Template.delete(author + "articleListDraft");
        redisRedis1Template.delete(author + "articleList");
        map.put("result",articleDao.deleteArticleStateByAuthorAndId(author,id));
        map.put("flag",1);
        return map;
    }

    /**
     * 文章浏览数+1
     * @param id int
     * @author lobger
     * @date 2020/4/30
     * */
    @Override
    public Map<String, Object> updateArticleBrowseAddById(int id) {
        Map<String, Object> map=new HashMap<>();
        map.put("result",articleDao.updateArticleBrowseAddById(id));
        map.put("flag",1);
        return map;
    }
}
