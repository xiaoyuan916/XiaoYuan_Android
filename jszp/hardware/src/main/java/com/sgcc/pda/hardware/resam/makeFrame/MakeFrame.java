package com.sgcc.pda.hardware.resam.makeFrame;

import com.sgcc.pda.hardware.resam.beans.Frame;
import com.sgcc.pda.hardware.resam.constant.Constant;
import com.sgcc.pda.hardware.util.DataConvert;

import java.util.Arrays;

/**
 * resam 报文组帧
 * Created by fubiao on 2017/7/6.
 */

public class MakeFrame {

    //装载报文的数组  给数组设置一个预计最大长度
    private byte[] bytes = new byte[Constant.FRAME_MAX_LEN];

    //记录当前位置
    private int pos = 0;

    public byte[] makeFrames(Frame frame) {
        Arrays.fill(bytes, (byte) 0); //将数组中全部元素都赋值为0

        this.bytes[pos++] = (byte) 0x55;  //将帧头拼接到报文数组中
        this.bytes[Constant.POS_CLA] = frame.getCla();//将命令类别拼接到报文数组中
        pos++;
        this.bytes[Constant.POS_INS] = frame.getIns();//将指令代码拼接到报文数组中
        pos++;
        this.bytes[Constant.POS_P1] = frame.getP1();//将操作符号1拼接到报文数组中
        pos++;
        this.bytes[Constant.POS_P2] = frame.getP2();//将操作符号1拼接到报文数组中
        pos++;

        //判断数据域为null情况
        if (frame.getData() == null)
            frame.setData(new byte[]{});

        String len = Integer.toHexString(frame.getData().length); //帧长度转化为十六进制字符串

        frame.setDataLen(frame.getData().length);  //设置帧长度

        if (len.length() < 4) {
            //帧长度不足4位用零补齐
            len = "0000".substring(0, 4 - len.length()) + len;
        }
        //十六进制字符串转化byte数组  将帧长度拼接到报文数组中    从len的0位开始copy
        System.arraycopy(DataConvert.toBytes(len), 0, bytes, pos, 2);
        pos = pos + 2;

        byte[] temp = frame.getData(); //获取数据域
        int dataLen = temp.length;
        System.arraycopy(temp, 0, bytes, pos, dataLen);  //将数据域拼接到报文数组中
        pos += dataLen; //当前pos的位置为数据域最后一个字节

        //帧校验
        completeFrame();

        // Log.d("tag1",DataConvert.toHexString(bytes).toUpperCase());

        //报文数组的是长度为数据域的长度加8 只有数据域的长度是不确定的 其他长度都是确定的
        byte[] retByte = new byte[frame.getDataLen() + 8];

        System.arraycopy(bytes, 0, retByte, 0, frame.getDataLen() + 8); //将有效报文截取出来

        //  Log.d("tag1",DataConvert.toHexString(retByte).toUpperCase());

        return retByte;
    }


    /**
     * 帧校验 :除帧头外，每个字节的异或值再取反
     */
    private void completeFrame() {
        byte xor = XorUtil.getXor(bytes);
        this.bytes[pos] = xor;
        pos++;
    }
}