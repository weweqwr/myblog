package com.longer.shiro.rewrite;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.Serializable;
import java.util.*;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.*;

public class RedisSessionDAO extends AbstractSessionDAO{
    private static Logger logger = LoggerFactory.getLogger(org.crazycake.shiro.RedisSessionDAO.class);
    private RedisManager redisManager;
    private String keyPrefix = "shiro_redis_session:";

    public RedisSessionDAO() {
    }

    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session != null && session.getId() != null) {
            byte[] key = this.getByteKey(session.getId());
            byte[] value = SerializeUtils.serialize(session);
            session.setTimeout((long)(this.redisManager.getExpire() * 1000));
            this.redisManager.set(key, value, this.redisManager.getExpire());
        } else {
            logger.error("session or session id is null");
        }
    }

    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            this.redisManager.del(this.getByteKey(session.getId()));
        } else {
            logger.error("session or session id is null");
        }
    }

    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet();
        Set<byte[]> keys = this.redisManager.keys(this.keyPrefix + "*");
        if (keys != null && keys.size() > 0) {
            Iterator i$ = keys.iterator();

            while(i$.hasNext()) {
                byte[] key = (byte[])i$.next();
                Session s = (Session)SerializeUtils.deserialize(this.redisManager.get(key));
                sessions.add(s);
            }
        }

        return sessions;
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId=null;
        String roleid=null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Cookie[] cookies= request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("sessionId")){
                sessionId=cookie.getValue();
            }
        }

        if(sessionId==""||sessionId==null) {
            sessionId = this.generateSessionId(session);
            //存储sessionid
            session.setAttribute("sessionId",sessionId);
        }

        sessionId=sessionId+roleid;
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        } else {
            Session s = (Session)SerializeUtils.deserialize(this.redisManager.get(this.getByteKey(sessionId)));
            return s;
        }
    }

    private byte[] getByteKey(Serializable sessionId) {
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }

    public RedisManager getRedisManager() {
        return this.redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
        this.redisManager.init();
    }

    public String getKeyPrefix() {
        return this.keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
