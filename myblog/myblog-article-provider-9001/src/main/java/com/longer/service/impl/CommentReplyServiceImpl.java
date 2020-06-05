package com.longer.service.impl;

import com.longer.entities.Article;
import com.longer.entities.Comment;
import com.longer.mapperDao.CommentReplyDao;
import com.longer.service.CommentReplyService;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CommentReplyServiceImpl implements CommentReplyService {
    @Autowired
    CommentReplyDao commentReplyDao;
    @Resource(name = "redisRedis1Template")
    private RedisTemplate redisRedis1Template;
    @Resource(name = "redisRedis2Template")
    private RedisTemplate redisRedis2Template;

    /**
     * 添加留言
     * @param content String
     * @param commentAccount String
     * @author loner
     * @date 2020/5/3
     * */
    @Override
    public Map<String, Object> insertComment(String content, String commentAccount,int articleid) {
        Map<String,Object> map=new HashMap<>();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String commentTime=format.format(new Date());
        map.put("result",commentReplyDao.insertComment(content,commentAccount,articleid,commentTime));
        map.put("flag",1);
        redisRedis1Template.delete("commentReply"+articleid);
        return map;
    }

    /**
     * 添加回复
     * @param content String
     * @param replyAccount String
     * @param toRepplyAccount String
     * @author loner
     * @date 2020/5/3
     * */
    @Override
    public Map<String, Object> insertReply(String content, String replyAccount, String toRepplyAccount,int commentid) {
        Map<String,Object> map=new HashMap<>();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String createTime=format.format(new Date());
        int articleid=commentReplyDao.selectArticleidByCommentId(commentid);
        map.put("result",commentReplyDao.insertReply(content,replyAccount,toRepplyAccount,commentid,createTime));
        map.put("flag",1);
        redisRedis1Template.delete("commentReply"+articleid);
        return map;
    }

    /**
     * 查询回复
     * @param curPage
     * @param showCount
     * @param articleid
     * @author loner
     * @date 2020/5/3
     * */
    @Override
    public Map<String, Object> selectCommentReply(int curPage, int showCount, int articleid,int replyCurPage,int replyShowCount){
        Map<String,Object> map=new HashMap<>();
        //先从redis查询是否有该文章的评论缓存
        List<Comment> articleList=new ArrayList<>();
        //key以commentReply+articleid声明明
        //file以commentReply+articleid+curPage+replyCurPage声明
        articleList= (List<Comment>) redisRedis2Template.opsForHash().get("commentReply"+articleid,"commentReply"+articleid+curPage+replyCurPage);
        if(articleList==null){
            articleList=commentReplyDao.selectCommentReply(curPage,showCount,articleid,replyCurPage,replyShowCount);
            //存进redis
            redisRedis1Template.opsForHash().put("commentReply"+articleid,"commentReply"+articleid+curPage+replyCurPage,articleList);
            //设置生命周期。
            redisRedis1Template.expire("commentReply"+articleid,30, TimeUnit.DAYS);
        }
        map.put("result",articleList);
        map.put("flag",1);
        return map;
    }

    /**
     * 软删除评论
     * @param state
     * @param id
     * @author loner
     * @date 2020/5/12
     * */
    @Override
    public Map<String, Object> updateCommentById(int state, int id) {
        Map<String,Object> map=new HashMap<>();
        boolean result=commentReplyDao.updateReplyById(state,id);
        int articleid=commentReplyDao.selectArticleidByCommentId(id);
        redisRedis1Template.delete("commentReply"+articleid);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }

    /**
     * 软删除回复
     * @param state
     * @param id
     * @author loner
     * @date 2020/5/12
     * */
    @Override
    public Map<String, Object> updateReplyById(int state, int id) {
        Map<String,Object> map=new HashMap<>();
        boolean result=commentReplyDao.updateReplyById(state,id);
        int articleid=commentReplyDao.selectArticleidByReplyId(id);
        redisRedis1Template.delete("commentReply"+articleid);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }

    /**
     * 举报评论
     * @param commentId int
     * @param reportAccount String
     * @param reportContent String
     * @author loner
     * @date 2020/5/13
     * */
    @Override
    public Map<String, Object> insertReportComment(int commentId, String reportAccount, String reportContent) {
        Map<String, Object> map=new HashMap<>();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String createTime=format.format(new Date());

        boolean result=commentReplyDao.insertReportComment(commentId,reportAccount,reportContent,createTime);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }

    /**
     * 举报回复
     * @param replyId int
     * @param reportAccount String
     * @param reportContent String
     * @author loner
     * @date 2020/5/13
     * */
    @Override
    public Map<String, Object> insertReportReply(int replyId, String reportAccount, String reportContent) {
        Map<String, Object> map=new HashMap<>();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String createTime=format.format(new Date());

        boolean result=commentReplyDao.insertReportReply(replyId,reportAccount,reportContent,createTime);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }
    /**
     * 查询举报评论列表
     * @param state int
     * @param reportAccount String
     * @param time int
     * @param curPage int
     * @param showCount int
     * @author loner
     * @date 2020/5/13
     * */
    @Override
    public Map<String, Object> selectReportComment(int state, String reportAccount, int time,int curPage,int showCount) {
        Map<String, Object> map=new HashMap<>();
        map.put("result",commentReplyDao.selectReportComment(state,reportAccount,time,curPage,showCount));
        map.put("flag",1);
        return map;
    }
    /**
     * 查询举报回复列表
     * @param state int
     * @param reportAccount String
     * @param time int
     * @param curPage int
     * @param showCount int
     * @author loner
     * @date 2020/5/13
     * */
    @Override
    public Map<String, Object> selectReportReply(int state, String reportAccount, int time,int curPage,int showCount) {
        Map<String, Object> map=new HashMap<>();
        map.put("result",commentReplyDao.selectReportReply(state,reportAccount,time,curPage,showCount));
        map.put("flag",1);
        return map;
    }
    /**
     * 举报评论成功
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    @Override
    public Map<String, Object> updateCommentStateSuccess(int id) {
        Map<String,Object> map=new HashMap<>();
        boolean result=commentReplyDao.updateCommentStateSuccess(id);
        int articleid=commentReplyDao.selectArticleidByCommentId(id);
        redisRedis1Template.delete("commentReply"+articleid);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }
    /**
     * 举报评论失败
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    @Override
    public Map<String, Object> updateCommentStateFail(int id) {
        Map<String,Object> map=new HashMap<>();
        boolean result=commentReplyDao.updateCommentStateFail(id);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }
    /**
     * 举报回复成功
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    @Override
    public Map<String, Object> updateReplyStateSuccess(int id) {
        Map<String,Object> map=new HashMap<>();
        boolean result=commentReplyDao.updateReplyStateSuccess(id);
        int articleid=commentReplyDao.selectArticleidByReplyId(id);
        redisRedis1Template.delete("commentReply"+articleid);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }
    /**
     * 举报回复失败
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    @Override
    public Map<String, Object> updateReplyStateFail(int id) {
        Map<String,Object> map=new HashMap<>();
        boolean result=commentReplyDao.updateReplyStateFail(id);
        map.put("result",result);
        map.put("flag",1);
        return map;
    }

}
