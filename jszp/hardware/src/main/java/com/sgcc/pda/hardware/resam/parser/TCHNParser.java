package com.sgcc.pda.hardware.resam.parser;


import android.util.Log;

import com.sgcc.pda.hardware.resam.beans.Frame;
import com.sgcc.pda.hardware.util.DataConvert;


/**
 * resam 下行帧解析
 * Created by fubiao on 2017/4/24.
 */

public class TCHNParser {
    public Frame parser(byte[] reciveBytes) {

        Frame frame = getframe(reciveBytes);
        byte resCode = frame.getSw1();
        //   byte stateCode = frame.getStateCode();

        frame.setUpValue(DataConvert.toHexString(frame.getDownData()));

        return frame;

    }

    private Frame getframe(byte[] reciveBytes) {
        Frame frame = new Frame();
        frame.setDownBytes(reciveBytes);
        //900000085602000000000027
        Log.e("tag", "----len-------" + DataConvert.toHexString(reciveBytes, 3, 2));
        frame.setDataLen(Integer.valueOf(DataConvert.toHexString(reciveBytes, 3, 2), 16)); //帧长度

        byte sw1 = (byte) (reciveBytes[1] & 0xFF);
        frame.setSw1(sw1);//状态字1

        byte sw2 = (byte) (reciveBytes[2] & 0xFF);
        frame.setSw1(sw2);//状态字2

        byte[] temp = new byte[frame.getDataLen()];
        System.arraycopy(reciveBytes, 5, temp, 0, frame.getDataLen());
        frame.setDownData(temp); //数据域

        //异常处理
        getErrorMsg(frame);

        return frame;
    }

    private void getErrorMsg(Frame frame) {

    }
}
