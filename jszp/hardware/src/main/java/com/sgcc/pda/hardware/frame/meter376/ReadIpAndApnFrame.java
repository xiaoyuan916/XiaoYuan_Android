package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.frame.ClassF3;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-18下午3:09
 * Author: wjkjinke00@126.com
 * Description:读取ip,备用ip,apn
 * 修改记录：2017/6/6 songweijie
 * 1.增加读取port,备用port
 */
public class ReadIpAndApnFrame extends BaseMeter376Frame {

    public static final int IP = 0x11;
    public static final int BIP = 0x12;
    public static final int APN = 0x13;
    public static final int PORT = 0x14;
    public static final int BPORT = 0x15;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getControlCode().setValue((byte) 0x4b);
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x02;
        pack.getSeq().setValue((byte) 0x71);
        pack.setAfn((byte) 0x0A);
        pack.getDataBuffer().put(new byte[]{0x00, 0x00});
        pack.getDataBuffer().put(new byte[]{0x04, 0x00});

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
            byte[] datas = databuffer.getBytes(28);// 数据域
            ClassF3 classF3 = new ClassF3();
            classF3.byteToClass(datas);
            results.put(IP, classF3.getMainIpString() + ":"
                    + classF3.getMainPortString());
            results.put(BIP, classF3.getExtraIpString() + ":"
                    + classF3.getExtraPortString());
            results.put(APN, classF3.getApnString());
            results.put(PORT,classF3.getMainPortString());
            results.put(BPORT,classF3.getExtraPortString());
            return results;
        }
        return results;
    }
}
