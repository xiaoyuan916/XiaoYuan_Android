package com.sgcc.pda.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 检查网络是否可用工具类
 * Created by xuzl on 2016/9/22.
 */
public class NetWorkUtil {
    private static NetWorkUtil instance;

    public static synchronized NetWorkUtil getInstance() {
        if (null == instance) {
            instance = new NetWorkUtil();
        }
        return instance;
    }

    private ConnectivityManager cm = null;

    private NetWorkUtil() {
    }

    /**
     * 检查当前网络是否可用
     *
     * @param context
     * @return true表示当前网络可用，否则返回false
     */
    public boolean isAvailable(Context context) {
        //实例化ConnectivityManager   cm
        if (null != context) {

            if (null == cm) {
                Object obj = context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (null != obj && obj instanceof ConnectivityManager) {
                    cm = (ConnectivityManager) obj;
                }
            }
        }
        //获取网络状态
        if (null != cm) {
            NetworkInfo newWorkInfo = cm.getActiveNetworkInfo();
            if (newWorkInfo != null && newWorkInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    //检测网络是否可用
    public static final boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }



        /**
         * 检查当前是否连接
         *
         * @param context
         * @return true表示当前网络处于连接状态，否则返回false
         */
    public boolean isConnected(Context context) {
        //实例化ConnectivityManager   cm
        if (null != context) {

            if (null == cm) {
                Object obj = context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (null != obj && obj instanceof ConnectivityManager) {
                    cm = (ConnectivityManager) obj;
                }
            }
        }
        //获取网络状态
        if (null != cm) {
            NetworkInfo newWorkInfo = cm.getActiveNetworkInfo();
            if (newWorkInfo != null && newWorkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    //是否连接网络
    public boolean isNetConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();

    }
}
