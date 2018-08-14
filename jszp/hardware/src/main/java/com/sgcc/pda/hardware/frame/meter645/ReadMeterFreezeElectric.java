package com.sgcc.pda.hardware.frame.meter645;

import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645Address;
import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645ControlCode;
import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645MeterPacket;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdDataBuffer;

import java.util.HashMap;


/**
 * CreateTime: 15/6/24下午4:55
 * Author: wjkjinke00@126.com
 * Description:07表冻结数据解析
 */
public class ReadMeterFreezeElectric extends BaseMeter645Frame {
    private String bjdz;

    public ReadMeterFreezeElectric(String bjdz) {
        this.bjdz = bjdz;
    }

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        return super.encode(args);
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        int offset = Gb645MeterPacket.getMsgHeadOffset(bs, 0);
        if (offset == -1) {

            return results;
        }

        Gb645MeterPacket packet = Gb645MeterPacket.getPacket(bs, offset);
        Gb645Address address = packet.getAddress();
        String addressStr = address.getAddress();
        if (!bjdz.equals(addressStr)) {
            return results;
        }

        Gb645ControlCode controlCode = packet.getControlCode();
        byte value = controlCode.getValue();
        if (value != (byte) 0x91) {
            return results;
        }

        BcdDataBuffer data = packet.getData();
        byte[] values = data.getValue();
        StringBuilder sb = new StringBuilder();
        for (int i = 4; i < values.length; i = i + 4) {
            String s = "";
            if (i == 4) {
                s = "正向有功总电能" + values[i + 3] + "" + values[i + 2] + "" + values[i + 1] + "." + values[i];
            } else {
                s = "正向有功费率" + ((i - 4) / 4) + "电能" + values[i + 3] + "" + values[i + 2] + "" + values[i + 1] + "." + values[i];
            }

            sb.append(s + "\n");
        }
        results.put(RESULT, sb.toString());
        return results;
    }

}
