package com.longer.shiro.filter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.longer.utils.ActiveUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 自定义过滤器记住我的功能处理session内容丢失的问题
 * 
 * **/
public class RemberMeFilter extends FormAuthenticationFilter{
	@Resource(name = "redisRedis3Template")
	private RedisTemplate redisRedis3Template;
	@Resource(name = "redisRedis4Template")
	private RedisTemplate redisRedis4Template;
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		//得到主体
		Subject subject=SecurityUtils.getSubject();
		//得到session
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		String sessionId=null;
		HttpServletRequest requests = servletRequestAttributes.getRequest();
		Cookie[] cookie = requests.getCookies();
		for (int i = 0; i < cookie.length; i++) {
			Cookie cook = cookie[i];
			if (cook.getName().equals("sessionId")) { //获取键
				System.out.println("sessionId:" + cook.getValue().toString());    //获取值权
				sessionId = cook.getValue().toString();
			}
		}

		Session session = (Session) redisRedis4Template.opsForValue().get(sessionId);


		if(!subject.isAuthenticated()&&subject.isRemembered()&&session.getAttribute("user")==null){//没有认证，但是有记住我的功能
			ActiveUser activeUser=(ActiveUser) subject.getPrincipal();
			session.setAttribute("user", activeUser.getUser());
		}
		return true;//作完上面的事情就放行了，不会直接拦截
	}

}
