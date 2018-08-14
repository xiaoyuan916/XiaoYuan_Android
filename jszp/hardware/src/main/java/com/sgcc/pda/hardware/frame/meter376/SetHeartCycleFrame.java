package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-18下午4:13
 * Author: wjkjinke00@126.com
 * Description:设置心跳
 */
public class SetHeartCycleFrame extends BaseMeter376Frame {
    private static final String TAG = "SetHeartCycleFrame";
    public static final int READ_HEART_CIRCLE = 0x11;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        byte[] bytes = (byte[]) args.get(READ_HEART_CIRCLE);

        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5a);
        pack.getSeq().setValue((byte) 0x70);
        pack.setAfn((byte) 0x04);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(
                new PmPacket376DT().setValue(new byte[]{0x01, 0x00}));
        pack.getDataBuffer().put(bytes);
        LogUtil.i(TAG, BcdUtils.binArrayToString(pack.getValue()));
        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return super.decode(bs);
    }
}
