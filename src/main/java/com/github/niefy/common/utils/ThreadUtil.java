package com.github.niefy.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ThreadUtil {
    private static Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(ThreadUtil.class);
    /**
     * 睡眠maxSeconds以内的随机时间
     * @param maxSeconds
     */
    public static void  sleepRandomTime(int maxSeconds){
        try {
            int randomMils=random.nextInt(maxSeconds*1000);
            Thread.sleep(randomMils);
        }catch (Exception e){
            logger.error("出错",e);
        }
    }
}
