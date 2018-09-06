package com.sgcc.pda.hardware.protocol.p698.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


/**
 * 创建者 田汉鑫
 * 创建时间 2016/5/6
 * 通用日志实现类
 */
public class CommonLogger implements ILogger {
    private Logger logger = null;
    private static HashMap<String, ILogger> logs = new HashMap<>();
    private CommonLogger(String filePath) {
        logger = Logger.getLogger(filePath);
        try {
            FileHandler fh = new FileHandler(filePath, true);
            fh.setLevel(java.util.logging.Level.ALL);
            fh.setFormatter(new MyLogHandler());
            logger.addHandler(fh);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        logs.put(filePath, this);
    }
    public static ILogger getInstance(String filePath)  {
        synchronized (CommonLogger.class) {
            if (logs.containsKey(filePath)) {
                return logs.get(filePath);
            } else {
                return new CommonLogger(filePath);
            }
        }
    }

    /**
     * 向日志文件写入一条日志
     * @param level 等级
     * @param message 日志内容
     */
    public void write(Level level, String message) {
        String levelString;
        switch (level) {
            case ERROR:
                levelString = "ERROR";
                break;
            case WARN:
                levelString = "WARN";
                break;
            case INFO:
                levelString = "INFO";
                break;
            case DEBUG:
                levelString = "DEBUG";
                break;
            case VERBOSE:
            default:
                levelString = "VERBOSE";
                break;
        }
        logger.severe(levelString + "]:[" + message);
    }

    private static class MyLogHandler extends Formatter {
        @Override
        public String format(LogRecord record) {
            return "[" + getDateString(record.getMillis()) + "]:[" +
                    record.getMessage() + "]\r\n";
        }
        private static String getDateString(long mills) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
            return sdf.format(new Date(mills));
        }
    }
}
