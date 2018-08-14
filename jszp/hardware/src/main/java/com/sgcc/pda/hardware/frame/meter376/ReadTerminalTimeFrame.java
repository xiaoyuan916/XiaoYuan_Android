package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA1;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * CreateTime: 2015-12-24下午2:34
 * Author: wjkjinke00@126.com
 * Description:
 */
public class ReadTerminalTimeFrame extends BaseMeter376Frame {

    public static final int TERMINAL_TIME = 0x11;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0xf2;
        pack.getControlCode().setValue((byte) 0x4b);

        pack.getSeq().setValue((byte) 0x73);
        pack.setAfn((byte) 0x0C);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(2));
        System.out.println(BcdUtils.binArrayToString(pack.getValue()));

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
                DataTypeA1 date = databuffer.getA1();
                Date dateDate = date.getDate();
                SimpleDateFormat dateFormat = DateUtil.getDateFormat();
                String format = dateFormat.format(dateDate);
                results.put(TERMINAL_TIME, format);
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }
        }
        return results;
    }
}
