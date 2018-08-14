package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.frame.ClassF4;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-24下午3:01
 * Author: wjkjinke00@126.com
 * Description:
 */
public class SetSmsFrame extends BaseMeter376Frame {

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x02;
        pack.getControlCode().setValue((byte) 0x4a);
        pack.getSeq().setValue((byte) 0x73);
        pack.setAfn((byte) 0x04);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(
                new PmPacket376DT().setValue(new byte[]{0x08, 0x00}));
        ClassF4 classF4 = new ClassF4();
        pack.getDataBuffer().put(classF4.classToByte());
        return pack.getValue();
    }
}
