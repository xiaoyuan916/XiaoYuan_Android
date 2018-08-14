package com.sgcc.pda.hardware.resam.makeFrame;

/**
 * Created by fubiao on 2017/7/13.
 */


public class XorUtil {

    public static byte getXor(byte[] yhz) {
        String a = "";
        a = xor(HexToString(yhz[0]), HexToString(yhz[1]));
        for (int i = 2; i < yhz.length; i++) {
            a = xor(a, HexToString(yhz[i]));
        }
        String b = getFan(a);
        System.out.println("------b-" + b);
        byte[] abc = toBytes(b);
        return abc[0];
    }

    //16进制数组转化成16进制字符串
    public static String HexToString(byte a) {
        StringBuffer sb = new StringBuffer(1);
        String temp = Integer.toHexString(0xFF & a);
        System.out.println("---str1---" + temp);
        if (temp.length() < 2) {
            sb.append(0);
            sb.append(temp.toUpperCase());
        } else {
            sb.append(temp);
        }
        System.out.println("---str2---" + sb.toString());
        return sb.toString();
    }


    //将16进制字符串 进行异或运算  结果为16进制字符串
    public static String xor(String strHex_X, String strHex_Y) {
        //将x、y转成二进制形式
        String anotherBinary = Integer.toBinaryString(Integer.valueOf(strHex_X, 16));
        String thisBinary = Integer.toBinaryString(Integer.valueOf(strHex_Y, 16));
        String result = "";

        //判断是否为8位二进制，否则左补零
        if (anotherBinary.length() != 8) {
            for (int i = anotherBinary.length(); i < 8; i++) {
                anotherBinary = "0" + anotherBinary;
            }
        }
        if (thisBinary.length() != 8) {
            for (int i = thisBinary.length(); i < 8; i++) {
                thisBinary = "0" + thisBinary;
            }
        }
        //异或运算
        for (int i = 0; i < anotherBinary.length(); i++) {
            //如果相同位置数相同，则补0， 否则补1
            if (thisBinary.charAt(i) == anotherBinary.charAt(i)) {
                result += "0";
            } else {
                result += "1";            }
        }
        return Integer.toHexString(Integer.parseInt(result, 2));
    }

    //将16进制字符串 进行 取反  结果为16进制字符串
    public static String getFan(String strHex_X) {
        //将x、y转成二进制形式
        String anotherBinary = Integer.toBinaryString(Integer.valueOf(strHex_X, 16));
        String result = "";

        //判断是否为8位二进制，否则左补零
        if (anotherBinary.length() != 8) {
            for (int i = anotherBinary.length(); i < 8; i++) {
                anotherBinary = "0" + anotherBinary;
            }
        }

        StringBuffer sb = new StringBuffer(anotherBinary);
        //异或运算
        for (int i = 0; i < sb.length(); i++) {
            //如果相同位置数相同，则补0， 否则补1
            if (String.valueOf(sb.charAt(i)).equals("0")) {
                sb.replace(i, i + 1, "1");
            } else {
                sb.replace(i, i + 1, "0");
            }
        }
        System.out.println("------sb-" + sb.toString());
        result = sb.toString();
        return Integer.toHexString(Integer.parseInt(result, 2));
    }

    /**
     * 将16进制字符串转换成byte数组
     *
     * @param value 待转换字符串
     * @return 转换完成的字符串-正确 null-错误
     */
    public static byte[] toBytes(String value) {
        // 确保传入数据有效
        if (value == null || value.trim().length() <= 0) {
            return null;
        }
        value = value.trim();
        // 确保有效数据长度有效（为2的倍数）
        if (value.length() % 2 != 0) {
            return null;
        }
        byte[] ret = new byte[value.length() / 2];
        for (int i = 0; i < ret.length; i++) {
            try {
                /*
                前面的逻辑已经保证了字符串长度是byte数组长度的2倍
                这里没有进行数组越界的判断
                 */
                String tmp = value.substring(i * 2, i * 2 + 2);
                int a = Integer.parseInt(tmp, 16);
                int b = a & 0xFF;
                byte c = (byte) b;
                ret[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
            } catch (Exception e) {
                return null;
            }
        }
        return ret;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
}

