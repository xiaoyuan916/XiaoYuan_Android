package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.frame.ClassF16;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;

/**
 * CreateTime: 2017-6-6下午4:09
 * Author:songweijie
 * Description:读取虚拟专网用户名及密码
 */
public class ReadApnNameAndPwdFrame extends BaseMeter376Frame {


    public static final int APN_USER = 0x11;
    public static final int APN_PWD = 0x12;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getControlCode().setValue((byte) 0x4b);
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x02;
        pack.getSeq().setValue((byte) 0x71);
        pack.setAfn((byte) 0x0A);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(16));

        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        PmPacket376 pa = new PmPacket376();
        int beginpos = 0;
        beginpos = PmPacket376.getMsgHeadOffset(bs, beginpos);
        if (beginpos != -1) {
            results = new HashMap<>();
            pa.setValue(bs, beginpos);
            PmPacketData databuffer = pa.getDataBuffer();
            databuffer.dataBuff.rewind();
            PmPacket376DA da = new PmPacket376DA();
            PmPacket376DT dt = new PmPacket376DT();
            databuffer.getDA(da);
            databuffer.getDT(dt);
            byte[] datas = databuffer.getBytes(64);// 数据域
            ClassF16 classF16 = new ClassF16();
            classF16.byteToClass(datas);
            results.put(APN_USER, classF16.getApnNameString());
            results.put(APN_PWD, classF16.getApnPasswordpString());
            return results;
        }
        return results;
    }
}
