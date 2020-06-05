package com.longer.utils;

import com.longer.entities.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListFind {
    //对lisi进行模糊查询
    public List search(String name, List list){
        List results = new ArrayList();
        Pattern pattern = Pattern.compile(name);
        for(int i=0; i < list.size(); i++){
            Matcher matcher = pattern.matcher(((Article)list.get(i)).getTextContent());
            if(matcher.find()){
                results.add(list.get(i));
            }
        }
        return results;
    }
}
