package com.sgcc.pda.hardware.frame.meter376;
import com.sgcc.pda.hardware.frame.ClassF16;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;

/**
 * CreateTime: 2017-6-6下午5:02
 * Author: songweijie
 * Description:设置虚拟专网用户名及密码
 */
public class SetApnNameAndApnPwdFrame extends BaseMeter376Frame {


    public static final int APN_NAME = 0x11;
    public static final int APN_PWD = 0x12;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        String apnName = (String) args.get(APN_NAME);
        String apnPwd = (String) args.get(APN_PWD);

        ClassF16 classF16 = new ClassF16();
        classF16.setApnNameString(apnName);
        classF16.setApnPasswordString(apnPwd);

        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5a);
        pack.getSeq().setValue((byte) 0x73);
        pack.setAfn((byte) 0x04);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(16));// F16
        pack.getDataBuffer().put(classF16.classToByte());
        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {

        return super.decode(bs);
    }
}
