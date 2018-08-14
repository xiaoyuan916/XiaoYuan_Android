package com.sgcc.pda.hardware.util;

import android.text.TextUtils;

import java.util.Locale;

public class ByteUtils {

    public static byte[] intToBytes(int value, int length) {
        byte[] src = new byte[length];
        for (int i = 0; i < src.length; i++) {
            src[src.length - 1 - i] = (byte) ((value >> (8 * i)) & 0xFF);
        }
        return src;
    }

    public static byte[] toBytes(short value) {
        byte[] result = new byte[2];
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return "";
        }
        for (int i = 0; i < src.length; i++) {
            if (i % 16 == 0) {
                stringBuilder.append("\n");
            }
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            stringBuilder.append("0x");
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + "\t");
        }
        return stringBuilder.toString();
    }

    /**
     * 将10进制字符串转化为16进制
     *
     * @param intValue 10进制字符串
     * @return
     */
    public static String IntToHex(String intValue) {
        String result = "";

        if (TextUtils.isEmpty(intValue)) {
            return result;
        }

        StringBuffer resultStr = new StringBuffer();
        if (intValue.contains("_")) {//如果终端逻辑地址用下划线分开则转化为八位
            String[] res = intValue.split("_");
            String value1 = res[0];
            String value2 = res[1];
            if (value1.length() != 4) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < 4 - value1.length(); i++) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(value1);
                resultStr.append(stringBuffer.toString());
            } else {
                resultStr.append(value1);
            }
            String lastStr = Integer.toHexString(Integer.parseInt(value2)).toUpperCase();
            if (lastStr.length() != 4) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < 4 - lastStr.length(); i++) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(lastStr);
                resultStr.append(stringBuffer.toString());
            } else {
                resultStr.append(lastStr);
            }
            result = resultStr.toString();
        } else if (intValue.length() == 9) {//如果终端逻辑地址为9位转化为八位
            resultStr.append(intValue.substring(0, 4));
            String lastStr = "";
            if (isHex(intValue.substring(4, 9))) {
                lastStr = Integer.toHexString(Integer.parseInt(intValue.substring(4, 9), 16)).toUpperCase();
            } else {
                lastStr = Integer.toHexString(Integer.parseInt(intValue.substring(4, 9))).toUpperCase();
            }
            if (lastStr.length() < 4) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < 4 - lastStr.length(); i++) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(lastStr);
                resultStr.append(stringBuffer.toString());
            } else if (lastStr.length() > 4) {
                StringBuffer stringBuffer = new StringBuffer();
                lastStr = Integer.toHexString(Integer.parseInt(intValue.substring(4, 9))).toUpperCase();
                if (lastStr.length() == 4) {
                    stringBuffer.append(lastStr);
                    resultStr.append(stringBuffer.toString());
                }

            } else {
                resultStr.append(lastStr);
            }
            result = resultStr.toString();
        } else {
            result = intValue;
        }
        return result;
    }


    /**
     * 将10进制转换成16进程
     * @param intValue
     * @return
     */
    public static String IntTHex(String intValue) {
        String result = "";

        if (TextUtils.isEmpty(intValue)) {
            return result;
        }

        StringBuffer resultStr = new StringBuffer();
        if (intValue.length() == 9) {//如果终端逻辑地址为9位转化为八位
            resultStr.append(intValue.substring(0, 4));
            String lastStr = "";
            if (ifHex(intValue.substring(4, 9))) {
                lastStr = Integer.toHexString(Integer.parseInt(intValue.substring(4, 9), 16)).toUpperCase();
            } else {
                lastStr = Integer.toHexString(Integer.parseInt(intValue.substring(4, 9))).toUpperCase();
            }
            if (lastStr.length() < 4) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < 4 - lastStr.length(); i++) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(lastStr);
                resultStr.append(stringBuffer.toString());
            } else if (lastStr.length() > 4) {
                StringBuffer stringBuffer = new StringBuffer();
                lastStr = Integer.toHexString(Integer.parseInt(intValue.substring(4, 9))).toUpperCase();
                if (lastStr.length() == 4) {
                    stringBuffer.append(lastStr);
                    resultStr.append(stringBuffer.toString());
                }

            } else {
                resultStr.append(lastStr);
            }
            result = resultStr.toString();
        } else {
            result = intValue;
        }
        return result;
    }


    public static boolean isHex(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')) || ((c >= '0') && (c <= '9')))
                continue;
            else
                return false;
        }
        return true;
    }

    public static boolean ifHex(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')))
                continue;
            else
                return false;
        }
        return true;
    }


    /**
     * 将十进制字符串转化为十六进制字符串
     *
     * @param data 字符串
     * @return 返回结果
     */
    public static String intToHex(String data) {
        if (data == null || data.length() == 0) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        if (data.contains("_")) {
            String[] res = data.split("_");
            result.append(res[0]);
            String lastStr = Integer.toHexString(Integer.parseInt(res[1])).toUpperCase();
            if (lastStr.length() != 4) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < 4 - lastStr.length(); i++) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(lastStr);
                result.append(stringBuffer.toString());
            } else {
                result.append(lastStr);
            }
        } else {
            result.append(data);
        }
        return result.toString();
    }

    /**
     * 将数据进行转换
     * 16进制和10进制之间转换
     *
     * @param value 终端逻辑地址
     */
    public static String hexAndInt(String value) {
        String result = "";
        if (value == null || value.length() == 0) {
            return result;
        }
        StringBuffer resultStr = new StringBuffer();
        if (value.contains("_")) {
            String[] res = value.split("_");
            String value1 = res[0];
            String value2 = res[1];
            if (value1.length() != 4) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < 4 - value1.length(); i++) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(value1);
                resultStr.append(stringBuffer.toString());
            } else {
                resultStr.append(value1);
            }
            String lastStr = Integer.toHexString(Integer.parseInt(value2)).toUpperCase();
            if (lastStr.length() != 4) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < 4 - lastStr.length(); i++) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(lastStr);
                resultStr.append(stringBuffer.toString());
            } else {
                resultStr.append(lastStr);
            }
            result = resultStr.toString();
        } else if (value.length() == 8) {
            resultStr.append(value.substring(0, 4));
            resultStr.append("_");
            String radixStr = String.valueOf(Integer.parseInt(value.substring(4, 8), 16));
            if (radixStr.length() <= 5) {
                StringBuffer radixString = new StringBuffer();
                for (int i = 0; i < 5 - radixStr.length(); i++) {
                    radixString.append("0");
                }
                radixString.append(radixStr);
                resultStr.append(radixString.toString());
            } else {
                resultStr.append(radixStr);
            }
            result = resultStr.toString();
        } else if (value.length() == 9) {
            String str1 = value.substring(0, 4);
            String str2 = value.substring(4, 9);
            result = str1 + "_" + str2;

        } else {
            result = value;
        }
        return result;
    }


    /**
     * 将16进制字符串转换为10进制数据
     *
     * @param data  十六进制字符串
     * @param radix 进制
     * @return 10进制数据
     */
    public static String hexToInt(String data, int radix) {
        if (data == null || data.length() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (data.length() == 8 && !data.contains("_")) {
            stringBuffer.append(data.substring(0, 4));
            stringBuffer.append("_");
            int radixNum = Integer.parseInt(data.substring(4, 8), radix);
            String radixStr = String.valueOf(radixNum);
            if (radixStr.length() <= 5) {
                StringBuffer radixString = new StringBuffer();
                for (int i = 0; i < 5 - radixStr.length(); i++) {
                    radixString.append("0");
                }
                radixString.append(radixStr);
                stringBuffer.append(radixString.toString());
            } else {
                stringBuffer.append(radixStr);
            }
        } else {
            stringBuffer.append(data);
        }
        return stringBuffer.toString();
    }

    /**
     * 将byte数组转化为16进制字符串
     *
     * @param bytes 进行转换的byte数组
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xff);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            result += hexString;
        }
        return result;
    }

    /**
     * 将16进制表示的字符串转换为byte数组
     *
     * @param hexString
     * @return 二进制表示的byte[]数组
     */
    public static byte[] hexStringToBytes(String hexString) {

        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase(Locale.getDefault());
        int length = hexString.length() / 2;
        // 将十六进制字符串转换成字符数组
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            // 一次去两个字符
            int pos = i * 2;
            // 两个字符一个对应byte的高四位一个对应低四位
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 将传进来的字符代表的数字转换成二进制数
     *
     * @param c
     * @return 以byte的数据类型返回字符代表的数字的二进制表示形式
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    public static byte[] intToByteArray(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
            bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);

        }
        return bLocalArr;
    }

    public static boolean equals(byte[] data1, byte[] data2) {
        if (data1 == null || data2 == null || data1.length != data2.length) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < data1.length; i++) {
            flag = flag & (data1[i] == data2[i]);
        }

        return flag;
    }

    public static byte getCheckCode(byte[] data, int byte_len) {
        byte result = 0;
        for (int i = 0; i < byte_len; i++) {
            result = (byte) (result + data[i]);
        }

        return result;
    }


    /**
     * 计算校验位
     *
     * @param data
     * @param startPostion
     * @param endPostion
     * @return
     */
    public static byte getCheckCode(byte[] data, int startPostion,
                                    int endPostion) {
        byte result = 0;
        for (int i = startPostion; i < endPostion; i++) {
            result = (byte) (result + data[i]);
        }

        return result;
    }

    /**
     * byte数组中的数据转为Int数据
     *
     * @param b      byte数组(低字节在前，高字节在后)
     * @param offset 转换开始的位置
     * @param len    byte数据的个数
     * @return
     */
    public static int byteToInt(byte[] b, int offset, int len) {

        int mask = 0xff;
        int temp = 0;
        int n = 0;
        for (int i = offset + len - 1; i >= offset; i--) {
            n <<= 8;
            temp = b[i] & mask;
            n |= temp;
        }
        return n;
    }

    /**
     * 把16进制的字符串转换成2进制数
     *
     * @param n
     * @return
     */
    public static String HexToBin(String n) {
        char[] chars = n.toCharArray();
        char str;
        String s = "";
        int p = 0;
        for (int i = 0; i < chars.length; i++) {
            str = chars[i];
            if (str >= '0' && str <= '9') {
                String s1 = Character.toString(str); //字符转字符串
                p = Integer.valueOf(s1).intValue(); //字符串转整形
            } else {
                if (str == 'A') {
                    str = 10;
                } else if (str == 'B') {
                    str = 11;
                } else if (str == 'C') {
                    str = 12;
                } else if (str == 'D') {
                    str = 13;
                } else if (str == 'E') {
                    str = 14;
                } else if (str == 'F') {
                    str = 15;
                } else {

                }
                p = (int) str;
            }
            switch (p) {
                case 0:
                    s += "0000";
                    break;

                case 1:
                    s += "0001";
                    break;

                case 2:
                    s += "0010";
                    break;
                case 3:
                    s += "0011";
                    break;
                case 4:
                    s += "0100";
                    break;

                case 5:
                    s += "0101";
                    break;

                case 6:
                    s += "0110";
                    break;

                case 7:
                    s += "0111";
                    break;
                case 8:
                    s += "1000";
                    break;
                case 9:
                    s += "1001";
                    break;
                case 10:
                    s += "1010";
                    break;
                case 11:
                    s += "1011";
                    break;
                case 12:
                    s += "1100";
                    break;
                case 13:
                    s += "1101";
                    break;
                case 14:
                    s += "1110";
                    break;
                case 15:
                    s += "1111";
                    break;

                default:
                    break;
            }
        }
        return s;
    }
}
