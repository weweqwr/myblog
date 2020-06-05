package com.longer.service;

import com.longer.entities.Ueditor;

import com.longer.fallback.UeditorServiceFallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@FeignClient(value = "MYBLOG-PROVIDER-ARTICLE/ueditor/",fallbackFactory = UeditorServiceFallback.class)
public interface UeditorService {

    /**
     * ueditor富文本编辑器上传配置
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value="/ueditor",produces = "text/script;charset=UTF-8",method = RequestMethod.GET)
    public String ueditor(@RequestParam("callback") String callback);

    /**
     * ueditor富文本编辑器上传
     * @param map1 Map
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value = "ueditorImageUpload",method = RequestMethod.POST)
    public Ueditor imageUpload(@RequestBody Map map1);

}
