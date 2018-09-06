package com.sgcc.pda.hardware.protocol.p698.utils;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class CommUtils {
    private static String hexStr =  "0123456789ABCDEF";
    private static String[] binaryArray =
            {"0000","0001","0010","0011",
                    "0100","0101","0110","0111",
                    "1000","1001","1010","1011",
                    "1100","1101","1110","1111"};
    public static void main(String args[]) {
        byte[] bytes = new byte[]{1, 2, 3};
        String ss = "568006";
        int a = Integer.parseInt("FF01FF00", 16);
        System.out.println(a);
    }

    /**
     * <p>将参数转换为BigDecimal，E值改为-1</p>
     *
     * @param arg
     * @return
     */
    public static BigDecimal getBigDecimal(String arg) {
        int Ecount = 0;
        for (int i = 0; i < arg.length(); i++) {
            if (arg == null || "".equals(arg)) {
                return new BigDecimal(-1);
            } else {
                if (arg.contains("E") || arg.contains("e")) {
                    Ecount++;
                }
            }
        }
        if (Ecount == arg.length()) {
            arg = "-1";
        }
        return new BigDecimal(arg);
    }

    public static BigDecimal getBigDecimal(int arg) {
        return new BigDecimal(arg);
    }

    /**
     * <p>
     * 判断是否是数字，小数，皆可
     * </p>
     */
    public static boolean isNumberc(String str) {
//		String str1 = str;
//		String str2 = str;
//		if (str.contains(".")) {
//			str1 = str.substring(0, str.indexOf("."));
//			str2 = str.substring(str.indexOf(".") + 1);
//		}
//		Pattern pattern = Pattern.compile("[0-9]*");
//		return pattern.matcher(str1).matches()
//				& pattern.matcher(str2).matches();
        if (getBigDecimal(str).intValue() == -1) {
            return false;
        }
        return true;
    }

    /**
     * 将byte数组转为字符串
     *
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        String result = "";
        if (bytes != null)
            result = byteToHexString(bytes, 0, bytes.length);
        return result;
    }

    public static boolean isEEOrFF(byte[] bytes, int fromIndex, int len) {
        int count = 0;
        for (int i = 0; i < len; i++) {
            if ((bytes[fromIndex] & 0xFF) == 0xEE) {
                count++;
            }
        }
        if (count == len)
            return true;

        count = 0;
        for (int i = 0; i < len; i++) {
            if ((bytes[fromIndex] & 0xFF) == 0xFF) {
                count++;
            }
        }
        if (count == len)
            return true;

        return false;
    }

    /**
     * 将byte数组从指定索引位置fromindex截取len长度的数组转为字符串
     *
     * @param bytes     原始数组
     * @param fromIndex 索引位置
     * @param len       截取长度
     * @return
     */
    public static String byteToHexString(byte[] bytes, int fromIndex, int len) {
        String result = "";
        if (bytes != null) {
            String hexString;
            for (int i = fromIndex; i < fromIndex + len; i++) {
                hexString = Integer.toHexString(bytes[i] & 0xFF);
                if (hexString.length() == 1)
                    hexString = "0" + hexString;
                //result = result + "0x" + hexString.toUpperCase() + ",";
                result = result + hexString.toUpperCase();
            }
        }
        return result;
    }

    public static String byteToHexStringLH(byte[] bytes, int fromIndex, int len) {
        return byteToHexStringLH(bytes, fromIndex, len, false);
    }

    /**
     * 以十六进制无符号整数形式返回一个整数参数的字符串
     *
     * @param bytes
     * @param fromIndex
     * @param len
     * @param checkSign
     * @return
     */
    public static String byteToHexStringLH(byte[] bytes, int fromIndex, int len, boolean checkSign) {
        String result = "";
        if (bytes != null) {
            String hexString;
            int _from = fromIndex + len - 1;
            for (int i = _from; i >= fromIndex; i--) {
                if (i == _from && checkSign) {
                    hexString = Integer.toHexString(bytes[i] & 0xFF & 0x7F);//Integer.toHexString 以十六进制无符号整数形式返回一个整数参数的字符串
                } else {
                    hexString = Integer.toHexString(bytes[i] & 0xFF);
                }
                if (hexString.length() == 1)
                    hexString = "0" + hexString;
                result = result + hexString.toUpperCase();
            }
        }
        return result;
    }

    public static String getSignStr(byte dataByte) {
        String sign = "";
        if ((dataByte & 0xFF) >> 7 == 1)
            sign = "-";
        return sign;
    }

    public static String byteToHexStringLH(byte[] bytes) {
        String result = "";
        if (bytes != null)
            result = byteToHexStringLH(bytes, 0, bytes.length);
        return result;
    }

    public static int bytes2Int(byte[] dataBytes, int startIndex, int len) {
        int k = 0;
        int val = 0;
        for (int i = 0; i < len; i++) {
            val = (val << 8) + (dataBytes[startIndex + len - 1 - i] & 0xFF);
            if ((dataBytes[startIndex + len - 1 - i] & 0xFF) == 0xFF)
                k++;
        }
        if (k == len)
            val = 0;
        return val;
    }

    public static byte[] intToByte(int i) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(i);
        return buffer.array();
    }

    public static int bytes2IntReverse(byte[] dataBytes, int startIndex, int len) {

        int k = 0;
        int val = 0;
        for (int i = 0; i < len; i++) {
            val = (val << 8) + (dataBytes[startIndex + i] & 0xFF);
            if ((dataBytes[startIndex + i] & 0xFF) == 0xFF)
                k++;
        }
        if (k == len)
            val = 0;
        return val;
    }

    public static int bcd2Int(byte bcd) {
        int iVal;
        iVal = bcd & 0xF;
        iVal += ((bcd >> 4) & 0xF) * 10;
        return iVal;
    }

    public static byte byte2Bcd(byte b) {
        byte r = (byte) (((b / 10) << 4) + (b % 10));
        return r;
    }

    public static byte[] bytes2toBcd(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (((b[i] / 10) << 4) + (b[i] % 10));
        }
        return b;
    }

    public static String formatDateTimeBytes1(byte[] dataBytes, int fromIndex) {
        String r = "";
        int pos = 0;
        String ss = CommUtils
                .byteToHexString(dataBytes, fromIndex + (pos++), 1);
        String mm = CommUtils
                .byteToHexString(dataBytes, fromIndex + (pos++), 1);
        String HH = CommUtils
                .byteToHexString(dataBytes, fromIndex + (pos++), 1);
        String dd = CommUtils
                .byteToHexString(dataBytes, fromIndex + (pos++), 1);
        String MM = Integer
                .toHexString(dataBytes[fromIndex + (pos++)] & 0xFF & 0x1F);
        if (MM.length() == 1)
            MM = "0" + MM;
        String yy = CommUtils
                .byteToHexString(dataBytes, fromIndex + (pos++), 1);
        r = String.format("20%s-%s-%s %s:%s:%s", yy, MM, dd, HH, mm, ss);
        return r;
    }

    /**
     * 倒序
     * 将十六进制的字符串  倒序 转换为byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hex2BinaryLH(String hexString) { // 123456789ABC
        int len = hexString.length() / 2;// 20130612
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) Integer.parseInt(hexString.substring((len - i - 1) * 2, (len - i) * 2), 16);
        }
        return bytes;
    }

    /**
     * 将字符串转换为byte数组
     * <p>
     * A12 数据格式12 可以用来转换"通讯地址"
     * </p>
     *
     * @param hexString
     * @return
     */
    public static byte[] hex2Binary(String hexString) { // 123456789ABC
        int len = hexString.length() / 2;
        byte[] bytes;
        if(len == 0){
            bytes = new byte[1];
            int i = Integer.parseInt(hexString.substring(0, hexString.length()));
            bytes[0] = (byte) i;
        }else{
            bytes = new byte[len];
            for (int i = 0; i < len; i++) {
                bytes[len - 1 - i] = (byte) Integer.parseInt(hexString.substring((len - i - 1) * 2, (len - i) * 2), 16);
            }
        }
        return bytes;
    }

    public static byte[] hex2ToTerminal(String hexString) {
        byte[] r = hex2Binary(hexString);
        byte[] result = new byte[r.length];
        int index = 0;
        result[index++] = r[1];
        result[index++] = r[0];
        for (int i = r.length - 1; i > 1; i--) {
            result[index++] = r[i];
        }

        return result;
    }

    public static String getTcomByLen(String frameStr, int len) {
        String tcomId = "";
        frameStr = frameStr.substring(0, len);
        tcomId += frameStr.substring(2, 4) + frameStr.substring(0, 2);
        for (int i = frameStr.length(); i > 4; i = i - 2) {
            tcomId += frameStr.substring(i - 2, i);
        }
        return tcomId;
    }

    public static String getOperatorId() {
        return "00000001";
    }

    public static int daToInt(int DA) {
        int high = (DA >> 8) & 0xFF; // DA2
        int low = DA & 0xFF; // DA1
        int iVal = 0;

        if (high == 0)
            return 0;

        for (int i = 0; i < 8; i++) {
            if ((1 << i) == low) {
                iVal = 8 * (high - 1) + i + 1;
                break;
            }
        }
        return iVal;
    }

    public static String hex2tohex(String args) {
        int i = Integer.parseInt(args, 2);
        String hex = Integer.toHexString(i);
        return hex;
    }

    public static String hextohex2(String args) {
        int length = args.length();
        String result = "";

        if (args.length() % 2 == 0) {

        } else {
            args = "0" + args; //
            length = length + 1;
        }

        for (int i = 0, j = 0; i < length / 2; i++, j = j + 2) {
            String str16 = args.substring(j, j + 2);
            int str16Int = Integer.parseInt(str16, 16);
            String str2 = Integer.toBinaryString(str16Int);
            str2 = "00000000".substring(0, 8 - str2.length()) + str2;
            result += str2;
        }
        return result;
    }

    /**
     * @param bytes
     * @param pos
     * @param length
     * @return
     */
    public static String bytesToASII(byte[] bytes, int pos, int length) {

        String resultStr = "";
        int startPos = pos;
        for (int i = pos; i < pos + length; i++) {
            char a = ((char) (bytes[startPos++]));
            resultStr += a + "";
        }
        return resultStr;
    }

    /**
     * ?????byte[] ????octet-String
     *
     * @param bytes
     * @param pos
     * @param length
     * @return
     */
    public static String bytesToOctetString(byte[] bytes, int pos, int length) {

        String resultStr = "";
        int startPos = pos;
        for (int i = pos; i < pos + length; i++) {
            if (i == pos + length - 1)
                resultStr += Integer.parseInt("" + (bytes[startPos++] & 0xFF),
                        10);
            else
                resultStr += Integer.parseInt("" + (bytes[startPos++] & 0xFF),
                        10)
                        + ".";
        }
        return resultStr;
    }

    public static byte[] HexString2Bytes(String args) {
        byte[] ret = new byte[args.length() / 2];
        byte[] tmp = args.getBytes();
        for (int i = 0; i < args.length() / 2; ++i) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 | _b1);
        return ret;
    }

    /**
     * @param asciiBytes
     * @return
     */
    public static String parseAsciiString(byte[] asciiBytes) {
        String asciiString = new String(asciiBytes);
        asciiString = (asciiString == null) ? "NULL" : asciiString.trim();
        return asciiString;
    }

    /**
     * @param ip
     * @return
     */
    public static byte[] ipStringToBytes(String ip) {
        byte[] ipBytes = new byte[4];
        if (ipBytes.equals("")) {
            return ipBytes;
        } else {
            String[] ips = ip.split("\\.");
            for (int i = 0; i < ipBytes.length; i++) {
                ipBytes[i] = (byte) Integer.parseInt(ips[i]);
            }
            return ipBytes;
        }
    }

    /**
     * @param bytes
     * @param pos
     * @param len
     * @return
     */
    public static String bytesDateToString(byte[] bytes, int pos, int len) {
        int year = (bytes[pos++] & 0xFF) * 256 + (bytes[pos++] & 0xFF);
        String month = (bytes[pos++] & 0xFF) + "";
        String day = (bytes[pos++] & 0xFF) + "";
        pos++; // week;
        String hour = (bytes[pos++] & 0xFF) + "";
        String min = (bytes[pos++] & 0xFF) + "";
        String sec = (bytes[pos++] & 0xFF) + "";
        String msec = (bytes[pos++] & 0xFF) + "";
        String zeroFormat = "00";

        String monthFormat = zeroFormat.substring(0, 2 - month.length())
                + month;
        String dayFormat = zeroFormat.substring(0, 2 - day.length()) + day;
        String hourFormat = zeroFormat.substring(0, 2 - hour.length()) + hour;
        String minFormat = zeroFormat.substring(0, 2 - min.length()) + min;
        String secFormat = zeroFormat.substring(0, 2 - sec.length()) + sec;
        String time = year + "-" + monthFormat + "-" + dayFormat + " "
                + hourFormat + ":" + minFormat + ":" + secFormat;
        return time;
    }

    /**
     * @param bytes
     * @param pos
     * @param len
     * @param pointNum
     * @return
     */
    public static String bytesToStringFormat(byte[] bytes, int pos, int len,
                                             int pointNum) {

        String result = "";
        String flag = "";
        int val = 0;
        for (int i = 0; i < len; i++) {
            val = (val << 8) + (bytes[pos++] & 0xFF);
        }
        result = val + "";
        String pointStr = "";
        for (int i = 0; i < pointNum; i++) {
            pointStr = "0" + pointStr;
        }
        if (result.length() > pointNum) {
            result = result.substring(0, result.length() - pointNum)
                    + "."
                    + result.substring(result.length() - pointNum, result
                    .length());
        } else {
            result = "0." + pointStr.substring(0, pointNum - result.length())
                    + result;
        }
        return result;
    }

    /**
     * 将14位无符号日期时间字符串转换为含有格式的日期
     * 例如：20130621095214----2013-06-21 09:52:14
     *
     * @param strDate
     * @return
     */
    public static String formatStringDate(String strDate) {
        if (strDate.length() != 14)
            throw new NumberFormatException(
                    "时间转换时字节长度不是14位(例如：20130621095214)，类型转换错误！当前是：" + strDate
                            + "---长度：" + strDate.length());
        StringBuffer buffer = new StringBuffer();
        buffer.append(strDate.substring(0, 4));// yyyy
        buffer.append("-");
        buffer.append(strDate.substring(4, 6));// MM
        buffer.append("-");
        buffer.append(strDate.substring(6, 8));// dd
        buffer.append(" ");
        buffer.append(strDate.substring(8, 10));// HH
        buffer.append(":");
        buffer.append(strDate.substring(10, 12));// mm
        buffer.append(":");
        buffer.append(strDate.substring(12, 14));// ss

        return buffer.toString();
    }


    /**
     * 把byte[]数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String bytesToHexString(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
//            sb.append(" ");
        }
        return sb.toString().trim();
    }


    /**
     *
     * @return 二进制数组转换为二进制字符串   2-2
     */
    public static String bytes2BinStr(byte[] bArray){

        String outStr = "";
        int pos = 0;
        for(byte b:bArray){
            //高四位
            pos = (b&0xF0)>>4;
            outStr+=binaryArray[pos];
            //低四位
            pos=b&0x0F;
            outStr+=binaryArray[pos];
        }
        return outStr;
    }

    /**
     *
     * @param bytes
     * @return 将二进制数组转换为十六进制字符串  2-16
     */
    public static String bin2HexStr(byte[] bytes){

        String result = "";
        String hex = "";
        for(int i=0;i<bytes.length;i++){
            //字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));
            result +=hex;  //+" "
        }
        return result;
    }

    /**
     *
     * @param hexString
     * @return 将十六进制转换为二进制字节数组   16-2
     */
    public static byte[] hexStr2BinArr(String hexString){
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length()/2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位
        for(int i=0;i<len;i++){
            //右移四位得到高位
            high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);
            low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));
            bytes[i] = (byte) (high|low);//高地位做或运算
        }
        return bytes;
    }

    /**
     *
     * @param hexString
     * @return 将十六进制转换为二进制字符串   16-2
     */
    public static String hexStr2BinStr(String hexString){
        return bytes2BinStr(hexStr2BinArr(hexString));
    }
}
