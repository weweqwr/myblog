package com.longer.fallback;

import com.longer.entities.Comment;
import com.longer.entities.Reply;
import com.longer.entities.Reportcomment;
import com.longer.entities.Reportreply;
import com.longer.service.ArticleService;
import com.longer.service.CommentReplyService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Component //千万不要忘记加
public class CommentReplyServiceFallback  implements FallbackFactory<CommentReplyService> {
    @Override
    public CommentReplyService create(Throwable throwable) {
        return new CommentReplyService() {
            @Override
            public Map<String, Object> insertComment(Comment comment) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> insertReply(Reply reply) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> selectCommentReply(int curPage, int showCount, int articleid, int replyCurPage, int replyShowCount) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateCommentById(Comment comment) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateReplyById(Reply reply) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> insertReportComment(Reportcomment reportcomment) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> insertReportReply(Reportreply reportreply) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> selectReportComment(int state, int time, int curPage, int showCount, String reportAccount) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> selectReportReply(int state, String reportAccount, int time, int curPage, int showCount) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateCommentStateSuccess(Comment comment) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateCommentStateFail(Comment comment) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateReplyStateSuccess(Reply reply) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateReplyStateFail(Reply reply) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }
        };
    }
}