package com.longer.service;

import com.longer.entities.Comment;
import com.longer.entities.Reply;
import com.longer.entities.Reportcomment;
import com.longer.entities.Reportreply;
import com.longer.fallback.CommentReplyServiceFallback;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@FeignClient(value = "MYBLOG-PROVIDER-ARTICLE/commentReply/",fallbackFactory = CommentReplyServiceFallback.class)
public interface CommentReplyService {
    /**
     * 添加留言
     *
     * @param comment Comment
     * @author loner
     * @date 2020/5/3
     */
    @RequestMapping(value = "insertComment", method = RequestMethod.POST)
    public Map<String, Object> insertComment(@RequestBody Comment comment);

    /**
     * 添加回复
     *
     * @param reply Reply
     * @author loner
     * @date 2020/5/3
     */
    @RequestMapping(value = "insertReply", method = RequestMethod.POST)
    public Map<String, Object> insertReply(@RequestBody Reply reply);

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
                                                  @PathVariable("replyShowCount") int replyShowCount);

    /**
     * 软删除评论
     *
     * @param comment Comment
     * @author loner
     * @date 2020/5/12
     */
    @RequestMapping(value = "updateCommentById", method = RequestMethod.POST)
    public Map<String, Object> updateCommentById(@RequestBody Comment comment);
    /**
     * 软删除回复
     *
     * @param reply Reply
     * @author loner
     * @date 2020/5/12
     */
    @RequestMapping(value = "updateReplyById", method = RequestMethod.POST)
    public Map<String, Object> updateReplyById(@RequestBody Reply reply);

    /**
     * 举报评论
     *
     * @param reportcomment Reportcomment
     * @author loner
     * @date 2020/5/13
     */
    @RequestMapping(value = "insertReportComment", method = RequestMethod.POST)
    public Map<String, Object> insertReportComment(@RequestBody Reportcomment reportcomment);

    /**
     * 举报回复
     *
     * @param reportreply Reportreply
     * @author loner
     * @date 2020/5/13
     */
    @RequestMapping(value = "insertReportReply", method = RequestMethod.POST)
    public Map<String, Object> insertReportReply(@RequestBody Reportreply reportreply);

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
                                                   @RequestParam("reportAccount") String reportAccount);

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
                                                 @RequestParam("showCount") int showCount);
    /**
     * 举报评论成功
     * @param comment Comment
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateCommentStateSuccess", method = RequestMethod.POST)
    public Map<String, Object> updateCommentStateSuccess(@RequestBody Comment comment);
    /**
     * 举报评论失败
     * @param  comment Comment
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateCommentStateFail", method = RequestMethod.POST)
    public Map<String, Object> updateCommentStateFail(@RequestBody Comment comment);
    /**
     * 举报回复成功
     * @param reply Reply
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateReplyStateSuccess", method = RequestMethod.POST)
    public Map<String, Object> updateReplyStateSuccess(@RequestBody Reply reply);
    /**
     * 举报回复失败
     * @param reply Reply
     * @author loner
     * @date 2020/5/13
     * */
    @RequestMapping(value = "updateReplyStateFail", method = RequestMethod.POST)
    public Map<String, Object> updateReplyStateFail(@RequestBody Reply reply);

}
