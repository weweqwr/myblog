package com.longer.controller;

import com.longer.entities.Ueditor;
import com.longer.service.UeditorService;
import com.longer.utils.PublicMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("ueditor")
public class UeditorController {
    @Autowired
    UeditorService ueditorService;
    /**
     * ueditor富文本编辑器上传配置
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value="/ueditor",produces = "text/script;charset=UTF-8",method = RequestMethod.GET)
    public String ueditor(HttpServletRequest request, HttpServletResponse response, String callback) {
        String configText= PublicMsg.UEDITOR_CONFIG;
        return callback+"("+configText+")";
    }

    /**
     * ueditor富文本编辑器上传
     * @param map1 Map
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value = "ueditorImageUpload",method = RequestMethod.POST)
    Ueditor ueditorImageUpload(@RequestBody Map map1){
        Ueditor ueditor=new Ueditor();
        try {
            Map<String,Object> map=ueditorService.imageUpload("article",map1.get("imageBase64").toString());
            String imageUrl=map.get("result").toString();
            ueditor.setState("SUCCESS");
            ueditor.setUrl(imageUrl);
            ueditor.setTitle("article"+".jpg");
            ueditor.setOriginal("article"+".jpg");
        }catch (Exception e){
            ueditor.setState("FAIL");
            e.printStackTrace();
        }
       return ueditor;
    }

}
