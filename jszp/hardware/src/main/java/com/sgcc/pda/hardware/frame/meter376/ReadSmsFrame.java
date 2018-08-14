package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.frame.ClassF4;
import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645MeterPacket;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-22下午2:57
 * Author: wjkjinke00@126.com
 * Description:读取短信号码
 */
public class ReadSmsFrame extends BaseMeter376Frame {

    public static final int SMS = 0x11;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getControlCode().setValue((byte) 0x4b);
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x02;
        pack.getSeq().setValue((byte) 0x71);
        pack.setAfn((byte) 0x0A);

        // F4 主站电话号码和短信中心号码
        pack.getDataBuffer().put(new byte[]{0x00, 0x00});
        pack.getDataBuffer().put(new byte[]{0x08, 0x00});

        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        PmPacket376 pa = new PmPacket376();
        int beginpos = 0;
        beginpos = PmPacket376.getMsgHeadOffset(bs, beginpos);
        if (beginpos != -1) {
            ClassF4 classF4 = new ClassF4();
            pa.setValue(bs, beginpos);
            byte[] metterbuff = pa.getDataBuffer().getValue();
            int meterbegin = Gb645MeterPacket.getMsgHeadOffset(metterbuff,
                    0);
            PmPacketData databuffer = pa.getDataBuffer();
            databuffer.dataBuff.rewind();
            PmPacket376DA da = new PmPacket376DA();
            PmPacket376DT dt = new PmPacket376DT();
            databuffer.getDA(da);
            databuffer.getDT(dt);
            byte[] datas = databuffer.getBytes(16);// 数据域
            boolean flag = classF4.byteToClass(datas);
            if (flag) {
                results = new HashMap<>();
                results.put(SMS, classF4.getShortMessNumString().substring(0, 11));
                return results;
            }
        }

        return results;
    }
}
