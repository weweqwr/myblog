package com.longer.fallback;

import com.longer.entities.Ueditor;
import com.longer.service.ArticleService;
import com.longer.service.UeditorService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@Component //千万不要忘记加
public class UeditorServiceFallback  implements FallbackFactory<UeditorService> {
    @Override
    public UeditorService create(Throwable throwable) {
        return new UeditorService() {
            @Override
            public String ueditor(String callback) {
                return null;
            }

            @Override
            public Ueditor imageUpload(Map map1) {
                return null;
            }
        };
    }
}
