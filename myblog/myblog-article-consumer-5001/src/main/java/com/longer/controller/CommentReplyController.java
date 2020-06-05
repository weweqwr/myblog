package com.longer.controller;

import com.longer.entities.*;
import com.longer.service.CommentReplyService;
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
@RequestMapping("commentReply")
public class CommentReplyController {
    private static final String REST_URL_PREFIX = "http://MYBLOG-PROVIDER-ARTICLE/commentReply/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommentReplyService commentReplyService;


    /**
     * 添加留言
     *
     * @param comment Comment
     * @author loner
     * @date 2020/5/3
     */
    @RequestMapping(value = "insertComment", method = RequestMethod.POST)
    public Map<String,Object> insertComment(@RequestBody Comment comment, HttpServletRequest request) {
        return this.commentReplyService.insertComment(comment);
    }

    /**
     * 添加回复
     *
     * @param reply Reply
     * @author loner
     * @date 2020/5/3
     */
    @RequestMapping(value = "insertReply", method = RequestMethod.POST)
    public Map<String,Object> insertReply(@RequestBody Reply reply, HttpServletRequest request) {
        return this.commentReplyService.insertReply(reply);
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
    @RequestMapping(value = "selectCommentReply", method = RequestMethod.GET)
    public Map<String,Object> selectCommentReply(@RequestParam(value = "curPage") int curPage,
                                                     @RequestParam(value = "showCount") int showCount,
                                                     @RequestParam(value = "articleid") int articleid,
                                                     @RequestParam("replyCurPage") int replyCurPage,
                                                     @RequestParam("replyShowCount") int replyShowCount,
                                                     HttpServletRequest request) {
        return this.commentReplyService.selectCommentReply(curPage,showCount,articleid,replyCurPage,replyShowCount);
    }
    /**
     * 软删除评论
     * @param comment Comment
     * @author loner
     * @date 2020/5/12
     * */
    @RequestMapping(value = "updateCommentById", method = RequestMethod.POST)
    public Map<String,Object> updateCommentById(@RequestBody Comment comment,HttpServletRequest request) {
        return this.commentReplyService.updateCommentById(comment);
    }

    /**
     * 软删除回复
     * @param reply Reply
     * @author loner
     * @date 2020/5/12
     * */
    @RequestMapping(value = "updateReplyById", method = RequestMethod.POST)
    public Map<String,Object> updateReplyById(@RequestBody Reply reply,HttpServletRequest request) {
        return this.commentReplyService.updateReplyById(reply);
    }
    /**
     * 举报评论
     * @param reportcomment Reportcomment
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "insertReportComment", method = RequestMethod.POST)
    public Map<String,Object> insertReportComment(@RequestBody Reportcomment reportcomment,HttpServletRequest request) {
        return this.commentReplyService.insertReportComment(reportcomment);
    }

    /**
     * 举报回复
     * @param reportreply Reportreply
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "insertReportReply", method = RequestMethod.POST)
    public Map<String,Object> insertReportReply(@RequestBody Reportreply reportreply,HttpServletRequest request) {
        return this.commentReplyService.insertReportReply(reportreply);
    }
    /**
     * 查询举报评论列表
     *
     * @param state         int
     * @param reportAccount String
     * @param time          int
     * @author loner
     * @date 2020/5/13
     */
    @RequestMapping(value = "selectReportComment", method = RequestMethod.GET)
    public Map<String,Object> selectReportComment(@RequestParam("state") int state,
                                                      @RequestParam("reportAccount") String reportAccount,
                                                      @RequestParam("time") int time,
                                                      @RequestParam("curPage") int curPage,
                                                      @RequestParam("showCount") int showCount,
                                                      HttpServletRequest request) {
        return this.commentReplyService.selectReportComment(state,time,curPage,showCount,reportAccount);
    }

    /**
     * 查询举报回复列表
     *
     * @param state         int
     * @param reportAccount String
     * @param time          int
     * @author loner
     * @date 2020/5/13
     */
    @RequestMapping(value = "selectReportReply", method = RequestMethod.GET)
    public Map<String,Object>selectReportReply(@RequestParam("state") int state,
                                                     @RequestParam("reportAccount") String reportAccount,
                                                     @RequestParam("time") int time,
                                                     @RequestParam("curPage") int curPage,
                                                     @RequestParam("showCount") int showCount,
                                                     HttpServletRequest request) {

        return this.commentReplyService.selectReportReply(state,reportAccount,time,curPage,showCount);
    }
    /**
     * 举报评论成功
     * @param comment Comment
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateCommentStateSuccess", method = RequestMethod.POST)
    public Map<String,Object>updateCommentStateSuccess(@RequestBody Comment comment,HttpServletRequest request) {
        return this.commentReplyService.updateCommentStateSuccess(comment);
    }
    /**
     * 举报评论失败
     * @param comment Comment
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateCommentStateFail", method = RequestMethod.POST)
    public Map<String,Object>  updateCommentStateFail(@RequestBody Comment comment,HttpServletRequest request) {
        return this.commentReplyService.updateCommentStateFail(comment);
    }
    /**
     * 举报回复成功
     * @param reply Reply
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateReplyStateSuccess", method = RequestMethod.POST)
    public Map<String,Object> updateReplyStateSuccess(@RequestBody Reply reply,HttpServletRequest request) {
        return this.commentReplyService.updateReplyStateSuccess(reply);
    }
    /**
     * 举报回复失败
     * @param reply Reply
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateReplyStateFail", method = RequestMethod.POST)
    public Map<String,Object> updateReplyStateFail(@RequestBody Reply reply,HttpServletRequest request) {
        return this.commentReplyService.updateReplyStateFail(reply);
    }

}
