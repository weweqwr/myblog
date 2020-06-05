package com.longer.service.impl;

import com.longer.entities.OBSEntity;
import com.longer.service.UeditorService;
import com.longer.utils.Base64Util;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UeditorServiceImpl implements UeditorService {
    @Override
    public Map<String, Object> imageUpload(String group,String imageBase64) {
        Map<String,Object> map=new HashMap<>();
        try {
            OBSEntity obsEntity = new OBSEntity();
            String endPoint = obsEntity.getEndPoint();
            String ak = obsEntity.getAk();
            String sk = obsEntity.getSk();
            ObsClient obsClient = new ObsClient(ak, sk, endPoint);
            String ObsPath = "myBlog/"+group+"/article"+ "/image" + "/";
            //base64不能出现 data:image/png;base64,要去掉以下步骤去除
            Base64Util bs = new Base64Util();
            //将base64转为file
            File file = bs.base64ToFile(imageBase64,   "author.jpg");
            //生成时间戳,作为文件夹区分头像文件
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmSSss");
            String creteTime = format.format(new Date());
            PutObjectResult result = obsClient.putObject("longlongago", ObsPath + creteTime+".jpg", file);
            //获取云存储对象路径
            String imageUrl = result.getObjectUrl();
            map.put("result",imageUrl);
            map.put("flag",1);
            obsClient.close();
        } catch (IOException e) {
            map.put("result",false);
            map.put("flag",2);
            e.printStackTrace();
        }
        return map;
    }
}
