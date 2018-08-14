package com.sgcc.pda.hardware.frame;

import java.util.HashMap;

/**
 * CreateTime: 2015-12-18上午10:40
 * Author: wjkjinke00@126.com
 * Description:
 */
public interface FrameI {


    /**
     * 组帧
     *
     * @param args
     * @return
     */
    byte[] encode(HashMap<Integer, Object> args);

    /**
     * 解帧
     *
     * @param bs
     * @return
     */
    HashMap<Integer, Object> decode(byte[] bs);

}
