package com.github.niefy.config;

import java.util.concurrent.*;

/**
 * 系统线程池，系统各种任务使用同一个线程池，以防止创建过多线程池
 */
public class TaskExcutor {
    private TaskExcutor(){}

    /**
     * 使用静态内部类实现EXCUTOR单例懒加载
     */
    private static class ExcutorHolder{
        private static final ExecutorService EXCUTOR = new ThreadPoolExecutor(5,30,10L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 将任务提交到系统线程池中执行
     * @param task
     * @return
     */
    public static Future<?> submit(Runnable task){
        return ExcutorHolder.EXCUTOR.submit(task);
    }
}
