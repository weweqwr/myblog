package com.longer.service;

import com.longer.entities.Article;
import com.longer.entities.Reportcomment;
import com.longer.entities.Reportreply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommentReplyService {
    /**
     * 添加留言
     * @param content String
     * @param commentAccount String
     * @author loner
     * @date 2020/5/3
     * */
    Map<String, Object> insertComment(String content, String commentAccount, int articleid);
    /**
     * 添加回复
     * @param content String
     * @param replyAccount String
     * @param toRepplyAccount String
     * @author loner
     * @date 2020/5/3
     * */
    Map<String, Object> insertReply(String content, String replyAccount, String toRepplyAccount, int commentid);
    /**
     * 查询回复
     * @param curPage
     * @param showCount
     * @param articleid
     * @author loner
     * @date 2020/5/3
     * */
    Map<String, Object> selectCommentReply(int curPage, int showCount, int articleid, int replyCurPage, int replyShowCount);
    /**
     * 软删除评论
     * @param state
     * @param id
     * @author loner
     * @date 2020/5/12
     * */
    Map<String, Object> updateCommentById(@Param("state") int state, @Param("id") int id);

    /**
     * 软删除回复
     * @param state
     * @param id
     * @author loner
     * @date 2020/5/12
     * */
    Map<String, Object> updateReplyById(@Param("state") int state, @Param("id") int id);
    /**
     * 举报评论
     * @param commentId int
     * @param reportAccount String
     * @param reportContent String
     * @author loner
     * @date 2020/5/13
     * */
    Map<String, Object> insertReportComment(int commentId, String reportAccount, String reportContent);
    /**
     * 举报回复
     * @param replyId int
     * @param reportAccount String
     * @param reportContent String
     * @author loner
     * @date 2020/5/13
     * */
    Map<String, Object> insertReportReply(int replyId, String reportAccount, String reportContent);

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
    Map<String, Object> selectReportComment(int state, String reportAccount, int time, int curPage, int showCount);
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
    Map<String, Object> selectReportReply(int state, String reportAccount, int time, int curPage, int showCount);

    /**
     * 举报评论成立
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    Map<String, Object> updateCommentStateSuccess(int id);
    /**
     * 举报评论失败
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    Map<String, Object> updateCommentStateFail(int id);
    /**
     * 举报回复成立
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    Map<String, Object> updateReplyStateSuccess(int id);
    /**
     * 举报回复失败
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    Map<String, Object> updateReplyStateFail(int id);

}
