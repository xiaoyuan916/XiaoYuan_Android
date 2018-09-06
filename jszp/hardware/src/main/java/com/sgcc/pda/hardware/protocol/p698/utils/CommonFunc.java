package com.sgcc.pda.hardware.protocol.p698.utils;


import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 创建者 田汉鑫
 * 创建时间 2016/4/6.
 * 通用操作集合
 */
public class CommonFunc {
    private static final String IP_MATCHER = "[1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5](.\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]){3}";
    private static final String PORT_MATCHER = "[1-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5]";
    private static final String LOG_PATH1 = "/storage/sdcard0/eaadp_log.log";
    private static final String LOG_PATH = Environment.getExternalStorageDirectory()+ File.separator + "eaadp_log.log";
    /**
     * 从输入流中读取数据到data中
     * 总计读取dataLength数据
     * 从data数组的offset位置开始保存
     * @param stream 输入流
     * @param data 保存数据缓存
     * @param offset 数据开始位置
     * @param dataLength 数据长度
     * @throws IOException
     *
     */
    public static void streamRead(InputStream stream, byte[] data, int offset, int dataLength) throws IOException {
        // 如果请求的索引及长度之和大于缓冲区，则直接抛出异常
        if (offset + dataLength > data.length) {
            throw new IOException();
        }

        int readCount = stream.read(data, offset, dataLength);
        // 如果返回的读取字节数小于请求的字节数，需要进行再次请求读取
        while (readCount < dataLength) {
            int tmp_count = stream.read(data, readCount + offset, dataLength - readCount);
            if (tmp_count < 0) { //已经到达流的末尾或无读取数据，直接结束读取过程
                break;
            } else {
                readCount += tmp_count;
            }
        }
    }

    /**
     * 判断输入字符串是否符合合法IP格式
     * @param ip 输入IP字符串
     * @return true-合法IP字符串 false-非法IP字符串
     */
    public static boolean isIp(String ip) {
        if (ip == null || "".equals(ip) || ip.length() < 7 || ip.length() > 19) {
            return false;
        }
        Pattern pat = Pattern.compile(IP_MATCHER);
        Matcher mat = pat.matcher(ip);
        return mat.find();
    }

    /**
     * 判断输入的PORT字符串是否符合合法端口格式
     * @param port 输入PORT字符串
     * @return true-合法PORT字符串 false-非法PORT字符串
     */
    public static boolean isPort(String port) {
        if (port == null || "".equals(port) || port.length() > 5) {
            return false;
        }
        Pattern pat = Pattern.compile(PORT_MATCHER);
        Matcher mat = pat.matcher(port);
        return mat.find();
    }

    public static void log(String message) {
        CommonLogger.getInstance(LOG_PATH).write(Level.DEBUG, message);
    }
}
