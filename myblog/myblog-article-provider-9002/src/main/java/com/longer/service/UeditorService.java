package com.longer.service;

import org.omg.CORBA.MARSHAL;

import java.util.Map;

public interface UeditorService {
    /**
     * @param imageBase64 String 上传图片的imageBase64
     * @author longer
     * @date 2020/4/24
     * */
    Map<String,Object> imageUpload(String group, String imageBase64);
}
