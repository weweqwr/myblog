package com.longer.controller;

import com.longer.entities.Comment;
import com.longer.entities.Reply;
import com.longer.entities.Reportcomment;
import com.longer.entities.Reportreply;
import com.longer.service.CommentReplyService;
import com.netflix.discovery.converters.jackson.EurekaXmlJacksonCodec;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commentReply")
public class CommentReplyController {
    @Autowired
    CommentReplyService commentReplyService;

    /**
     * 添加留言
     *
     * @param comment Comment
     * @author loner
     * @date 2020/5/3
     */
    @RequestMapping(value = "insertComment", method = RequestMethod.POST)
    public Map<String, Object> insertComment(@RequestBody Comment comment) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = commentReplyService.insertComment(comment.getContent(), comment.getCommentAccount(), comment.getArticleid());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 添加回复
     *
     * @param reply Reply
     * @author loner
     * @date 2020/5/3
     */
    @RequestMapping(value = "insertReply", method = RequestMethod.POST)
    public Map<String, Object> insertReply(@RequestBody Reply reply) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = commentReplyService.insertReply(reply.getContent(), reply.getRepplyAccount(), reply.getToRepplyAccount(), reply.getCommentid());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 查询回复
     *
     * @param curPage
     * @param showCount
     * @param articleid
     * @author loner
     * @date 2020/5/3
     */
    @RequestMapping(value = "selectCommentReply/{curPage}/{showCount}/{articleid}/{replyCurPage}/{replyShowCount}", method = RequestMethod.GET)
    public Map<String, Object> selectCommentReply(@PathVariable("curPage") int curPage,
                                                  @PathVariable("showCount") int showCount,
                                                  @PathVariable("articleid") int articleid,
                                                  @PathVariable("replyCurPage") int replyCurPage,
                                                  @PathVariable("replyShowCount") int replyShowCount) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = commentReplyService.selectCommentReply(curPage, showCount, articleid, replyCurPage, replyShowCount);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 软删除评论
     *
     * @param comment Comment
     * @author loner
     * @date 2020/5/12
     */
    @RequestMapping(value = "updateCommentById", method = RequestMethod.POST)
    public Map<String, Object> updateCommentById(@RequestBody Comment comment) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(comment.getState());
            System.out.println(comment.getId());
            map = commentReplyService.updateCommentById(comment.getState(), comment.getId());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 软删除回复
     *
     * @param reply Reply
     * @author loner
     * @date 2020/5/12
     */
    @RequestMapping(value = "updateReplyById", method = RequestMethod.POST)
    public Map<String, Object> updateReplyById(@RequestBody Reply reply) {
        Map<String, Object> map = new HashMap<>();
        try {

            map = commentReplyService.updateReplyById(reply.getState(), reply.getId());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 举报评论
     *
     * @param reportcomment Reportcomment
     * @author loner
     * @date 2020/5/13
     */
    @RequestMapping(value = "insertReportComment", method = RequestMethod.POST)
    public Map<String, Object> insertReportComment(@RequestBody Reportcomment reportcomment) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = commentReplyService.insertReportComment(reportcomment.getCommentId(), reportcomment.getReportAccount(), reportcomment.getReportContent());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 举报回复
     *
     * @param reportreply Reportreply
     * @author loner
     * @date 2020/5/13
     */
    @RequestMapping(value = "insertReportReply", method = RequestMethod.POST)
    public Map<String, Object> insertReportReply(@RequestBody Reportreply reportreply) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = commentReplyService.insertReportReply(reportreply.getReplyId(), reportreply.getReportAccount(), reportreply.getReportContent());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 查询举报评论列表
     *
     * @param state         int
     * @param reportAccount String
     * @param time          int
     * @param curPage       int
     * @param showCount     int
     * @author loner
     * @date 2020/5/13
     */
    @RequestMapping(value = "selectReportComment", method = RequestMethod.GET)
    public Map<String, Object> selectReportComment(@RequestParam("state") int state,
                                                   @RequestParam("time") int time,
                                                   @RequestParam("curPage") int curPage,
                                                   @RequestParam("showCount") int showCount,
                                                   @RequestParam("reportAccount") String reportAccount
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = commentReplyService.selectReportComment(state, reportAccount, time,curPage,showCount);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

    /**
     * 查询举报回复列表
     *
     * @param state         int
     * @param reportAccount String
     * @param time          int
     * @param curPage       int
     * @param showCount     int
     * @author loner
     * @date 2020/5/13
     */
    @RequestMapping(value = "selectReportReply", method = RequestMethod.GET)
    public Map<String, Object> selectReportReply(@RequestParam("state") int state,
                                                 @RequestParam("reportAccount") String reportAccount,
                                                 @RequestParam("time") int time,
                                                 @RequestParam("curPage") int curPage,
                                                 @RequestParam("showCount") int showCount) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = commentReplyService.selectReportReply(state, reportAccount, time,curPage,showCount);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }
    /**
     * 举报评论成功
     * @param comment Comment
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateCommentStateSuccess", method = RequestMethod.POST)
    public Map<String, Object> updateCommentStateSuccess(@RequestBody Comment comment) {
        Map<String,Object> map=new HashMap<>();
        try {
        map=commentReplyService.updateCommentStateSuccess(comment.getId());
        }catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }
    /**
     * 举报评论失败
     * @param  comment Comment
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateCommentStateFail", method = RequestMethod.POST)
    public Map<String, Object> updateCommentStateFail(@RequestBody Comment comment) {
        Map<String,Object> map=new HashMap<>();
        try {
            map=commentReplyService.updateCommentStateFail(comment.getId());
        }catch (Exception e){
            e.printStackTrace();
            map.put("result",false);
            map.put("flag",0);
        }

        return map;
    }
    /**
     * 举报回复成功
     * @param reply Reply
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateReplyStateSuccess", method = RequestMethod.POST)
    public Map<String, Object> updateReplyStateSuccess(@RequestBody Reply reply) {
        Map<String,Object> map=new HashMap<>();
        try {
            map = commentReplyService.updateReplyStateSuccess(reply.getId());
        }catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }
    /**
     * 举报回复失败
     * @param reply Reply
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateReplyStateFail", method = RequestMethod.POST)
    public Map<String, Object> updateReplyStateFail(@RequestBody Reply reply) {
        Map<String,Object> map=new HashMap<>();
        try {
            map=commentReplyService.updateReplyStateFail(reply.getId());
        }catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            map.put("flag", 0);
        }
        return map;
    }

}
