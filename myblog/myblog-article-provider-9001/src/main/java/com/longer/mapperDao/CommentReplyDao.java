package com.longer.mapperDao;

import com.longer.entities.Article;
import com.longer.entities.Comment;
import com.longer.entities.Reportcomment;
import com.longer.entities.Reportreply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.security.PermitAll;
import java.util.List;

@Mapper
public interface CommentReplyDao {
    /**
     * 添加留言
     * @param content String
     * @param commentAccount String
     * @author loner
     * @date 2020/5/3
     * */
    boolean insertComment(@Param("content")String content,@Param("commentAccount")String commentAccount,@Param("articleid")int articleid,@Param("commentTime")String commentTime);
    /**
     * 添加回复
     * @param content String
     * @param replyAccount String
     * @param toRepplyAccount String
     * @author loner
     * @date 2020/5/3
     * */
    boolean insertReply(@Param("content")String content,@Param("replyAccount")String replyAccount,@Param("toRepplyAccount")String toRepplyAccount,@Param("commentid")int commentid,@Param("replyTime")String replyTime);
    /**
     * 查询回复
     * @param curPage
     * @param showCount
     * @param articleid
     * @author loner
     * @date 2020/5/3
     * */
    List<Comment> selectCommentReply(@Param("curPage")int curPage, @Param("showCount")int showCount, @Param("articleid")int articleid,@Param("replyCurPage")int replyCurPage,@Param("replyShowCount")int replyShowCount);

    /**
     * 软删除评论
     * @param state
     * @param id
     * @author loner
     * @date 2020/5/12
     * */
    boolean updateCommentById(@Param("state")int state,@Param("id")int id);

    /**
     * 软删除回复
     * @param state
     * @param id
     * @author loner
     * @date 2020/5/12
     * */
    boolean updateReplyById(@Param("state")int state,@Param("id")int id);

    /**
     * 举报评论
     * @param commentId int
     * @param reportAccount String
     * @param reportContent String
     * @author loner
     * @date 2020/5/13
     * */
    boolean insertReportComment(@Param("commentId")int commentId,@Param("reportAccount")String reportAccount,@Param("reportContent")String reportContent,@Param("createTime")String createTime);
    /**
     * 举报回复
     * @param replyId int
     * @param reportAccount String
     * @param reportContent String
     * @author loner
     * @date 2020/5/13
     * */
    boolean insertReportReply(@Param("replyId")int replyId,@Param("reportAccount")String reportAccount,@Param("reportContent")String reportContent,@Param("createTime")String createTime);

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
    List<Reportcomment> selectReportComment(@Param("state") int state,@Param("reportAccount")String reportAccount,@Param("time")int time,@Param("curPage")int curPage,@Param("showCount")int showCount);
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
    List<Reportreply> selectReportReply(@Param("state") int state, @Param("reportAccount")String reportAccount, @Param("time")int time,@Param("curPage")int curPage,@Param("showCount")int showCount);

    /**
     * 举报评论成立
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    boolean updateCommentStateSuccess(@Param("id")int id);
    /**
     * 举报评论失败
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    boolean updateCommentStateFail(@Param("id")int id);
    /**
     * 举报回复成立
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    boolean updateReplyStateSuccess(@Param("id")int id);
    /**
     * 举报回复失败
     * @param id int
     * @author loner
     * @date 2020/5/13
     * */
    boolean updateReplyStateFail(@Param("id")int id);
    /**根据repidId查询articleid
     * @param id int
     * @author loner
     * @date 2020/5/18
     * */
    int selectArticleidByReplyId(@Param("id")int id);
    /**根据repidId查询articleid
     * @param id int
     * @author loner
     * @date 2020/5/18
     * */
    int selectArticleidByCommentId(@Param("id")int id);

}
