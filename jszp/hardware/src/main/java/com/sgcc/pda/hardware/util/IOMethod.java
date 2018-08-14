package com.sgcc.pda.hardware.util;

import java.io.InputStream;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/30.
 * 流操作的方法集合
 */
public class IOMethod {
    /**
     * 从流中读取一块数据
     * @param stream 输入流
     * @param data 返回-读取的数据HEX字符串
     * @param offset 流偏移量
     * @param dataLength 数据长度（指字节长度）
     * @return 0-成功 其它-错误码
     */
    public static int streamRead(InputStream stream, StringBuffer data, int offset, int dataLength) {
        try {
            stream.available();
        } catch (Exception e) {
            return ErrorManager.ErrorType.CommonReadError.getValue();
        }
        if (data == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (offset < 0 || dataLength < 0) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        byte[] buffer = new byte[dataLength];
        try {
            int readCount = stream.read(buffer, offset, dataLength);

            // 如果返回的读取字节数小于请求的字节数，需要进行再次请求读取
            while (readCount != -1 && readCount < dataLength) {
                int tmp_count = stream.read(buffer, readCount + offset, dataLength - readCount);
                if (tmp_count < 0) { //已经到达流的末尾或无读取数据，直接结束读取过程
                    break;
                } else {
                    readCount += tmp_count;
                }
            }

            data.delete(0, data.length()).append(DataConvert.toHexString(buffer, 0, buffer.length));

        } catch (Exception e) {
            return ErrorManager.ErrorType.CommonReadError.getValue();
        }

        return 0;
    }
}
