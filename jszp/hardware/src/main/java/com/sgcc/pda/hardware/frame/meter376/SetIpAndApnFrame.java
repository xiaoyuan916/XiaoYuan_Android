package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.frame.ClassF3;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-18下午3:40
 * Author: wjkjinke00@126.com
 * Description:设置ip,备用ip,apn
 */
public class SetIpAndApnFrame extends BaseMeter376Frame{

    public static final int IP = 0x11;
    public static final int BIP = 0x12;
    public static final int APN = 0x13;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        String ip = (String) args.get(IP);
        String bip = (String) args.get(BIP);
        String apn = (String) args.get(APN);

        ClassF3 classF3 = new ClassF3();
        classF3.setIpAndPortString(ip);
        classF3.setExtraIpAndPortString(bip);
        classF3.setApnString(apn);

        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x02;
        pack.getControlCode().setValue((byte) 0x4a);
        pack.getSeq().setValue((byte) 0x73);
        pack.setAfn((byte) 0x04);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(
                new PmPacket376DT().setValue(new byte[]{0x04, 0x00}));// F3
        pack.getDataBuffer().put(classF3.classToByte());
        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return super.decode(bs);
    }
}
