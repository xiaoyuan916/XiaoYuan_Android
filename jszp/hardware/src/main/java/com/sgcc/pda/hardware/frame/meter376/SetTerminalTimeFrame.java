package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA1;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-24下午2:49
 * Author: wjkjinke00@126.com
 * Description:
 */
public class SetTerminalTimeFrame extends BaseMeter376Frame{

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x02;
        pack.getControlCode().setValue((byte) 0x4a);
        pack.getSeq().setValue((byte) 0x73);
        pack.setAfn((byte) 0x05);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(
                new PmPacket376DT().setValue(new byte[]{0x40, 0x03}));

        DataTypeA1 dataTypeA1 = new DataTypeA1();
        byte[] array = dataTypeA1.getArray();
        pack.getDataBuffer().put(
                new byte[]{array[0], array[1], array[2], array[3], array[4],
                        array[5]});

        System.out.println(BcdUtils.binArrayToString(pack.getValue()));

        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return super.decode(bs);
    }
}
