package com.sgcc.pda.hardware.frame.meter645;


import com.sgcc.pda.hardware.frame.FrameI;

import java.util.HashMap;

/**
 * CreateTime: 2015-12-24下午3:09
 * Author: wjkjinke00@126.com
 * Description:
 */
public class BaseMeter645Frame implements FrameI {
    public HashMap<Integer, Object> results = null;
    public static final int RESULT = 0x01;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        return new byte[0];
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return null;
    }
}
