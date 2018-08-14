package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-24下午2:10
 * Author: wjkjinke00@126.com
 * Description:表计端口读取
 */
public class ReadMeterPortFrame extends BaseMeter376Frame {
    private static final String TAG = "ReadMeterPortFrame";
    public static final int CLDH = 0x11;
    public static final int PORT = 0x12;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        String cldh = (String) args.get(CLDH);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);
        pack.getSeq().setValue((byte) 0x60);
        pack.setAfn((byte) 0x0A);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(10));

        pack.getDataBuffer().put((byte) 0x01);
        pack.getDataBuffer().put((byte) 0x00);

        int tmp = Integer.valueOf(cldh);
        byte a = (byte) (tmp % 256);
        byte b = (byte) (tmp / 256);
        pack.getDataBuffer().put(new byte[]{a, b});

        LogUtil.i(TAG, BcdUtils.binArrayToString(pack.getValue()));

        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        PmPacket376 pa = new PmPacket376();
        int beginpos = 0;

        beginpos = PmPacket376.getMsgHeadOffset(bs, beginpos);
        if (beginpos != -1) {
            try {
                results = new HashMap<>();
                pa.setValue(bs, beginpos);
                PmPacketData databuffer = pa.getDataBuffer();
                databuffer.dataBuff.rewind();
                PmPacket376DA da = new PmPacket376DA();
                PmPacket376DT dt = new PmPacket376DT();
                databuffer.getDA(da);
                databuffer.getDT(dt);
                String port = String.valueOf(databuffer.getBytes(7)[6] & 31);
                results.put(PORT, port);
                return results;
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }

        }
        return results;
    }
}
