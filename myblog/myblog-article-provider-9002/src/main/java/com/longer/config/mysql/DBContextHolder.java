package com.longer.config.mysql;

import java.util.concurrent.atomic.AtomicInteger;

public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void mainMysql() {
        set(DBTypeEnum.mainMysql);
        System.out.println("切换到master");
    }

    public static void slaveMysql() {   set(DBTypeEnum.slaveMysql);
        set(DBTypeEnum.slaveMysql);
        System.out.println("切换到slaveMysql");

//        //  轮询
//        int index = counter.getAndIncrement() % 2;
//        if (counter.get() > 9999) {
//            counter.set(-1);
//        }
//        if (index == 0) {
//            set(DBTypeEnum.SLAVE1);
//            System.out.println("切换到slave1");
//        }else {
//            set(DBTypeEnum.SLAVE2);
//            System.out.println("切换到slave2");
//        }
    }

}