package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-18下午4:28
 * Author: wjkjinke00@126.com
 * Description:
 */
public class TerminalResetFrame extends BaseMeter376Frame {

    public static final int RESET_TYPE = 0x11;

    //终端复位类型
    public static final int RESET_TYPE_HARDWARE = 0x12;//硬件复位
    public static final int RESET_TYPE_DATA = 0x13;//数据区复位
    public static final int RESET_TYPE_PARAMTER = 0x14;//参数复位

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        int resetType = (int) args.get(RESET_TYPE);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x51);

        pack.getSeq().setValue((byte) 0x73);
        pack.setAfn((byte) 0x01);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));

        switch (resetType) {
            case RESET_TYPE_HARDWARE:
                pack.getDataBuffer().putDT(new PmPacket376DT(1));
                break;
            case RESET_TYPE_DATA:
                pack.getDataBuffer().putDT(new PmPacket376DT(2));
                break;
            case RESET_TYPE_PARAMTER:
                pack.getDataBuffer().putDT(new PmPacket376DT(4));
                break;
        }
        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return super.decode(bs);
    }
}
