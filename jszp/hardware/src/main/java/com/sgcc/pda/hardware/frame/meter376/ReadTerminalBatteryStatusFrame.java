package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

import java.util.HashMap;

/**
 * CreateTime: 2015-12-22下午2:37
 * Author: wjkjinke00@126.com
 * Description:读取电池状态
 */
public class ReadTerminalBatteryStatusFrame extends BaseMeter376Frame {
    private static final String TAG = "ReadTerminalBattery";
    public static final int TERMINAL_BATTERY_STATUS = 0x11;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);//终端地址

        pack.getAddress().getValue()[4] = (byte) 0x14;//主站地址
        pack.getControlCode().setValue((byte) 0x5b);//控制码

        pack.getSeq().setValue((byte) 0x60);//序列号
        pack.setAfn((byte) 0x0c);//功能码
        pack.getDataBuffer().putDA(new PmPacket376DA(0));//DA
        pack.getDataBuffer().putDT(new PmPacket376DT().setValue(new byte[]{0x40, 0x1E}));//DT
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
                byte[] result = databuffer.getBytes(1);
                results.put(TERMINAL_BATTERY_STATUS, BcdUtils.binArrayToString(result));
                return results;
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }
        }
        return results;
    }
}
