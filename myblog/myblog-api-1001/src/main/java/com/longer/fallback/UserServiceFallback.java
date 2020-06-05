package com.longer.fallback;

import com.longer.entities.Article;
import com.longer.entities.User;
import com.longer.service.ArticleService;
import com.longer.service.UserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component //千万不要忘记加
public class UserServiceFallback  implements FallbackFactory<UserService> {

    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public Map<String, Object> insertUserRedist(User user) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> selectUserByAccount(String account, int roleid, int state) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> doLogin(String account, String password, int roleid, int state) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateUserInfo(User user) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> cashRecentVisitors(Article article) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> queryRecentVisitors(int articleid) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> selectAdminUserBy(int roleid, int time, int state, int curPage, int showCount, String account) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> updateUserStateById(User user) {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }

            @Override
            public Map<String, Object> selectHotUser() {
                Map<String,Object> map=new HashMap<>();
                map.put("flag",4);
                map.put("result","该服务暂时不能访问");
                return map;
            }
        };
    }
}
