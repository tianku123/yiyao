package com.qm.omp.api.pool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @ClassName: ExecutorPool
 * @Description: 通用线程池
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
public class ExecutorPool {
    private final static ExecutorPool self = new ExecutorPool();
    private ExecutorService pageletPool = null;

    /**
     * 单例私有构造函数
     */
    private ExecutorPool() {
        //构建自适应的线程池
//		pageletPool = Executors.newCachedThreadPool();
        //构建指定大小的线程池
        //pageletPool = Executors.newFixedThreadPool(200);\
        // 指定初始最大大小的线程池
        pageletPool = ThreadControl.newRetractedThreadPool();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ExecutorPool getInstance() {
        return self;
    }

    /**
     * 执行单个任务
     *
     * @param command
     */
    public void exe(Runnable command) {
        pageletPool.execute(command);
    }

    /**
     * 执行单个任务,有返回结果的
     *
     * @param command
     */
    public Future<?> submit(Runnable command) {
        return pageletPool.submit(command);
    }

    /**
     * 执行多个任务
     *
     * @param command
     */
    public void exes(List<Runnable> commands) {
        for (int i = 0; i < commands.size(); i++) {
            pageletPool.execute(commands.get(i));
        }
    }
}