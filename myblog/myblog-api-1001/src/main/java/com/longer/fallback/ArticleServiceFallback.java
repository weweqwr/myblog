package com.longer.fallback;

import com.longer.entities.Article;
import com.longer.service.ArticleService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Component //千万不要忘记加
public class ArticleServiceFallback implements FallbackFactory<ArticleService> {
    @Override
    public ArticleService create(Throwable throwable) {
        return new ArticleService() {
            @Override
            public Map<String, Object> insertArticle(Article article) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> selectArticle(int id, String title, int time, int browse, int state, int curPage, int showCount) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> selectArticleByAccountOrTitle(String title, String author, int time, int browse, int state, int curPage, int showCount) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateArticle(Article article) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateArticleStateByAuthorAndId(Article article) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> deleteArticleStateByAuthorAndId(Article article) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateArticleBrowseAddById(Article article) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }
        };
    }
}