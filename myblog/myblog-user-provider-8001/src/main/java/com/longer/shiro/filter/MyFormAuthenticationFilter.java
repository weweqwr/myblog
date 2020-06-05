package com.longer.shiro.filter;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * 重写shiro authc 的过滤器
 * 解决当用户使用浏览器访问未认证的页面时跳转到页面
 * 当用户使用ajax访问未认证的数据时返回json串
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    /**
     * 在访问Controller前判断是否登录，返回json,不进行重定向
     *
     * @return true 继续往下执行，false改filter过滤器已经处理，不继续执行其他过滤器
     */

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> resultData = new HashMap<>();
        Subject subject= SecurityUtils.getSubject();
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        response.reset();
        if(!subject.isAuthenticated()) {
            //重定向
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //这里是个坑，如果不设置的接受的访问源，那么前端都会报跨域错误，因为这里还没到corsConfig里面
            httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            resultData.put("result", "登录失败");
            resultData.put("flag", 4);
            httpServletResponse.getWriter().write(JSONObject.toJSON(resultData).toString());
        }
        return false;
    }


}
