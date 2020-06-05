package com.longer.controller;

import com.longer.config.Base64Util;
import com.longer.config.MultipartFileToFile;
import com.longer.entities.Ueditor;
import com.longer.service.UeditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ueditor")
public class UeditorArticle {
    private static final String REST_URL_PREFIX = "http://MYBLOG-PROVIDER-ARTICLE/ueditor/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    UeditorService ueditorService;

    /**
     * ueditor富文本编辑器上传配置
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value="/ueditor",produces = "text/script;charset=UTF-8",method = RequestMethod.GET)
    public ResponseEntity<String> ueditor(HttpServletRequest request, HttpServletResponse response,String callback) {
        String url=REST_URL_PREFIX+"ueditor?callback="+callback;
        HttpHeaders requestHeaders = new HttpHeaders();
        List<String> cookieList = new ArrayList<>();

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            cookieList=cookieList;
        }

        for (Cookie cookie : cookies) {
            cookieList.add(cookie.getName() + "=" + cookie.getValue());
        }

        requestHeaders.put(HttpHeaders.COOKIE,cookieList);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, requestEntity, String.class);
        return responseEntity;
    }

    /**
     * ueditor富文本编辑器上传
     * @param upfile String
     * @author longer
     * @date 2020/4/24
     */
    @RequestMapping(value = "ueditorImageUpload",method = RequestMethod.POST)
    public Ueditor ueditorImageUpload(MultipartFile upfile, HttpServletRequest request) throws Exception {
        String url=REST_URL_PREFIX+"ueditorImageUpload";
        Map<String, Object> map= new HashMap<>();
        //将MultipartFile装为file
        MultipartFileToFile mf=new MultipartFileToFile();
        File file=mf.multipartFileToFile(upfile);
        //将file装base64
        Base64Util bs=new  Base64Util();
        String base64=bs.fileToBase64(file);
        map.put("imageBase64",base64);
        return this.ueditorService.imageUpload(map);
    }
}
