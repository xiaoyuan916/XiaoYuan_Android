package com.xiao.project.task;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;


/**
 * author:xuxiaoyuan
 * date:2019/4/24
 * <p>
 * //自主定义线程池
 * param corePoolSize    线程池的核心运行线程数量
 * param maximumPoolSize 线程池中可以创建的最大运行线程数量【包括核心运行线程】
 * param keepAliveTime   当线程池线程数量超过corePoolSize时，
 * 多余的空余线程在缓冲队列的存活时间，超时后将会被移除
 * param unit            线程池维护线程所允许的空闲时间的单位，一般设置为秒
 * param workQueue       线程池所使用的缓冲队列，可以设置缓冲队列容纳的线程数量
 * param threadFactory   线程工厂，用于创建线程。
 * <p>
 * 当一个异步任务执行execute的时候将会通过该工厂new出一个thread
 * Executor executor = new ThreadPoolExecutor(3,5,10,
 * TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(5));
 * <p>
 * AsyncTask自带线程池
 * .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
 */
public class UploadLogTask extends AsyncTask<String, Void, String> {
    private WeakReference<Context> context;

    public UploadLogTask(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //异步任务开始
    }

    /**
     * 传入参数，对应excute里面的传递参数
     *
     * @param strings
     * @return
     */
    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    /**
     * 对应处理完毕的结果
     *
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
