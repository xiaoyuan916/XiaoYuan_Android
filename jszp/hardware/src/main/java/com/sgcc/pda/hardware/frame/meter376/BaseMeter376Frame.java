package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.frame.FrameI;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-18上午10:58
 * Author: wjkjinke00@126.com
 * Description:
 */
public class BaseMeter376Frame implements FrameI {

    public HashMap<Integer, Object> results = null;

    public static final int ZDLJDZ = 0x01;
    public static final int RESULT = 0x02;

    public HashMap<Integer, Object> decodeConfirmFrame(byte[] bs) {
        PmPacket376 pa = new PmPacket376();
        int beginpos = 0;
        beginpos = PmPacket376.getMsgHeadOffset(bs, beginpos);
        if (beginpos != -1) {
            try {
                pa.setValue(bs, beginpos);
                byte afn = pa.getAfn();
                PmPacketData databuffer = pa.getDataBuffer();
                databuffer.dataBuff.rewind();
                PmPacket376DA da = new PmPacket376DA();
                PmPacket376DT dt = new PmPacket376DT();
                databuffer.getDA(da);
                databuffer.getDT(dt);
                if (afn == 0x00) {
                    results = new HashMap<>();
                    if (da.getPn() == 0) {
                        if (dt.getFn() == 1) {
                            results.put(RESULT, "全部确认");
                            return results;
                        } else if (dt.getFn() == 2) {
                            results.put(RESULT, "全部否认");
                            return results;
                        } else if (dt.getFn() == 3) {
                            results.put(RESULT, "按数据单元标识确认和否认");
                            return results;
                        } else {
                            results.put(RESULT, "请开启终端编程功能再进行对时");
                            return results;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }
        }
        return results;
    }

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        return new byte[0];
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return null;
    }
}
