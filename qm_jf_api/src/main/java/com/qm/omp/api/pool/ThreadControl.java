package com.qm.omp.api.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ThreadControl
 * @Description: 线程管理类
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
public class ThreadControl {

    private static final Logger logger = LoggerFactory.getLogger("threadPoolControl");

    // 线程池维护线程的最少数量
    public static int corePoolSize = 5;
    // 线程池维护线程的最大数量
    public static int maximumPoolSize = 20;
    // 线程池维护线程所允许的空闲时间，单位MS，超时将会终止该线程
    public static int keepAliveTime = 1000;
    // 线程池队列大小
    public static int queueSize = 10;

    public static ThreadPoolExecutor newRetractedThreadPool() {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize),
                new RejectedExecutionHandler() {
                    public void rejectedExecution(Runnable r,
                                                  ThreadPoolExecutor executor) {
                        logger.info("线程池状态已满,"
                                + "线程池中计划被执行的任务总数：" + executor.getTaskCount() + ","
                                + "执行完毕的任务数：" + executor.getCompletedTaskCount() + ","
                                + "线程池中同时存在最大线程数：" + executor.getLargestPoolSize() + ","
                                + "当前正在执行的任务数：" + executor.getActiveCount() + ","
                                + "线程池中当前线程数：" + executor.getPoolSize()
                        );
                    }

                }
        );

    }
}
