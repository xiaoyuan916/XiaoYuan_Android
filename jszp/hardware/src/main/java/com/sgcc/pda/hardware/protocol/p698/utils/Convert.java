package com.sgcc.pda.hardware.protocol.p698.utils;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {

    public static String toHexString(int value) {
        String stmp = Integer.toHexString(value & 0xFF);
        return stmp.length() % 2 != 0 ? "0" + stmp : stmp;
    }

    public static String toHexString(byte value) {
        String stmp = Integer.toHexString(value & 0xFF);
        return (stmp.length() == 1) ? "0" + stmp : stmp;
    }

    public static String toHexString(byte[] value) {
        if (value == null) {
            return "";
        }
        return toHexString(value, 0, value.length);
    }

    //TODO：错误的字符串转换
    public static String toHexString(byte[] value, int index, int length) {
        if (value == null || value.length <= index)
            return "";
        StringBuffer retBuffer = new StringBuffer();
        int len = value.length <= index + length ? value.length - index : length;
        for (int i = 0; i < len; i++) {
            retBuffer.append(toHexString(value[index + i]));
        }
        return retBuffer.toString();
    }

    public static byte[] intToBytes(int value) {
        byte[] result = new byte[4];
        result[3] = (byte) ((value >> 24) & 0xFF);
        result[2] = (byte) ((value >> 16) & 0xFF);
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    public static byte[] shortToBytes(short value) {
        byte[] result = new byte[2];
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    /***
     * 将十六进制字符串转换成byte数组
     *
     * @param value
     * @return
     */
    public static byte[] toBytes(String value) {
        if (value == null || value.trim().length() <= 0) {
            return null;
        }
        int len = 0;
        if (value.trim().length() % 2 == 0) {
            len = value.trim().length() / 2;
        } else {
            len = value.trim().length() / 2 + 1;
        }
        byte[] ret = new byte[len];
        for (int i = 0; i < len; i++) {
            try {
                String temp = value.trim().substring(i * 2, value.length());
                if (temp.length() < 2) {
                    ret[i] = (byte) Integer.parseInt(temp, 16);
                } else {
                    ret[i] = (byte) Integer.parseInt(temp.substring(0, 2), 16);
                }
            } catch (Exception e) {
                ret[i] = 0;
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static byte[] toBytes(long value) {
        byte[] result = new byte[8];
        for (int i = 0; i < 8; i++) {
            result[i] = (byte) ((value >> (i) * 8) & 0xFF);
        }
        return result;
    }

    public static byte[] toBytes(int value) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (byte) ((value >> (i) * 8) & 0xFF);
        }
        return result;
    }

    /***
     * 将指定的字节数组从开始位置的两个字节合并成一个short类型
     *
     * @param data
     * @param start
     * @return
     */
    public static short ToUInt16(byte[] data, int start) {
        int ret = 0;
        if (data != null && data.length > 0 && data.length - 2 >= start) {
            ret = ((data[start] & 0x000000FF) | ((data[start + 1] << 8) & 0x0000FF00));
        }
        return (short) ret;
    }

    public static long ToInt64(byte[] data, int start, int len) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(data, start, len);
        buffer.flip();
        long ret = buffer.getLong();
        return ret;
    }

    public static int ToInt32(byte[] data, int start) {
        int ret = 0;
        if (data != null && data.length > 0 && data.length - 4 >= start) {
            ret = (data[start] & 0xFF) | (data[start + 1] & 0xFF) << 0x08
                    | (data[start + 2] & 0xFF) << 0x10
                    | (data[start + 3] & 0xFF) << 0x18;
        }
        return ret;
    }

    public static byte[] toBytes(short value) {
        byte[] result = new byte[2];
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    public static byte[] ipToBytes(String ip) {
        if (ip == null || ip.length() <= 3) {
            return null;
        }
        String[] ips = ip.split("\\.");
        if (ips.length != 4) {
            return null;
        }
        byte[] ret = new byte[4];
        try {
            for (int i = 0; i < ret.length; i++) {
                int b = Integer.parseInt(ips[i]);
                if (b > 255) {
                    return null;
                }
                ret[i] = (byte) b;
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据传入的时间串返回时间实例
     *
     * @param timeString 格式为“yyyy-MM-dd hh:mm”的时间字符串
     * @return 时间实例
     * @throws ParseException 解析异常
     */
    public static Date TimeStringToDate(String timeString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return format.parse(timeString);
    }

    /**
     * 将字符串转换成16进制
     *
     * @param str 要转换的字符串，本字符串只包含有效的16进制值
     * @return 成功返回16进制数据，失败返回null
     */
    public static byte[] ConvertFromString(String str) {
        return Convert.toBytes(str);
    }

    /**
     * 将字节数组逆序
     *
     * @param datas   需要逆序的字节数组
     * @param dataLen 需要逆序的字节数组中有效数据的长度
     * @return
     */
    public static byte[] ByteReverse(byte[] datas, int dataLen) {
        byte[] result;
        if (datas.length < 1 || datas == null) {
            // cozi:注意用此种方式是否会出异常
            result = new byte[0];
            return result;
        }

        result = new byte[dataLen];
        for (int i = dataLen - 1; i > 0; i--) {
            result[dataLen - 1 - i] = datas[i];
        }
        return result;
    }

    /**
     * 将16进制数据转换成字符串
     *
     * @param datas      16进制数据
     * @param startIndex 16进制数据起始索引
     * @param dataLen    16进制数据个数
     * @return 成功返回转换后的字符串，失败返回空字串
     */
    public static String ConvertToString(byte[] datas, int startIndex,
                                         int dataLen) {
        int i;
        String result = "";

        if (datas == null || datas.length < 1 || startIndex < 0 || dataLen < 1
                || startIndex >= datas.length
                || startIndex + dataLen > datas.length)
            return "";

        for (i = startIndex; i < startIndex + dataLen; i++)
            result += Convert.toHexString(datas[i]);

        return result;
    }

    /***
     * 字符串两位一组逆序如：123456变为563412
     *
     * @param str        传入的字符串
     * @param startIndex 起始指针
     * @param dataLen    截取的字符串长度需为偶数
     * @return
     */
    public static String String2Reverse(String str, int startIndex, int dataLen) {
        int i;
        String result = "";

        if (str == null || dataLen < 1 || startIndex < 0
                || startIndex >= dataLen || startIndex + dataLen > str.length())
            return "";

        for (i = startIndex + dataLen - 2; i >= startIndex; i -= 2)
            result = str.substring(i, i * 2);
        return result;
    }

    public static String parseDoubleBy2(double value) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(value);
    }

    public static byte int2OneByte(int num) {
        return (byte) (num & 0x000000ff);
    }

    /***
     * 将 16 进制数据转换成 BCD 码
     *
     * @param hex 16 进制数据
     * @return 对应的CBD码
     */
    public static byte ToBcd(byte hex) {
        return (byte) (hex / 10 << 4 | hex % 10);
    }

    /***
     * 将 BCD 码转换成 16 进制
     *
     * @param bcd BCD 码
     * @return 16 进制数据
     */
    public static byte ToHex(byte bcd) {
        return (byte) ((bcd >> 4) * 10 + (bcd & 0x0F));
    }

    public static int ToIntOffset(byte num) {
        return (num >> 5) & 0x07;
    }

    public static int ToIntEnd(byte num) {
        return num & 0x1F;

    }

    public static int byte4ToInt(byte num) {
        return (num >> 4) & 0x0F;
    }

    public static int byteToInt(byte num) {
        return (num) & 0x0F;
    }

    public static int byteToInt2(byte num) {
        return (num >> 2) & 0x03;
    }

    public static int byte2ToInt(byte num) {
        return num & 0x03;
    }

    /**
     * 将二进制字符串转化为int
     *
     * @param bi
     * @return
     */
    public static int toInt(String bi) {
        int len = bi.length();
        int sum = 0;
        int tmp, max = len - 1;
        for (int i = 0; i < len; ++i) {
            tmp = bi.charAt(i) - '0';
            sum += tmp * Math.pow(2, max--);
        }
        return sum;
    }
}