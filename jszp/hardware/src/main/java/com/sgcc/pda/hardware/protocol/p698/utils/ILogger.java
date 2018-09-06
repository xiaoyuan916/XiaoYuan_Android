package com.sgcc.pda.hardware.protocol.p698.utils;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/5/6
 * 日志功能接口
 */
public interface ILogger {
    /**
     * 向日志文件写入一条日志
     * @param level 等级
     * @param message 日志内容
     */
    void write(Level level, String message);
}
