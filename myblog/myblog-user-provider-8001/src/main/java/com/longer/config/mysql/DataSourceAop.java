package com.longer.config.mysql;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {

    @Pointcut("!@annotation(com.longer.config.mysql.Master) " +
            "&& (execution(* com.longer.mapperDao..*.select*(..)) " +
            "|| execution(* com.longer.mapperDao..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.longer.config.mysql.Master) " +
            "|| execution(* com.longer.mapperDao..*.insert*(..)) " +
            "|| execution(* com.longer.mapperDao..*.add*(..)) " +
            "|| execution(* com.longer.mapperDao..*.update*(..)) " +
            "|| execution(* com.longer.mapperDao..*.edit*(..)) " +
            "|| execution(* com.longer.mapperDao..*.delete*(..)) " +
            "|| execution(* com.longer.mapperDao..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slaveMysql();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.mainMysql();
    }


}
