package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-24下午3:26
 * Author: wjkjinke00@126.com
 * Description:
 */
public class OpenSearchFunctionFrame extends BaseMeter376Frame {

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5A);
        pack.getSeq().setValue((byte) 0x60);
        pack.setAfn((byte) 0x05);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT().setValue(new
                byte[]{0x10, 0x12}));
        pack.getDataBuffer().put((byte) 0x01);

        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {

        PmPacket376 pa = new PmPacket376();
        int beginpos = 0;

        beginpos = PmPacket376.getMsgHeadOffset(bs, beginpos);
        if (beginpos != -1) {
            try {
                pa.setValue(bs, beginpos);
                byte afn = pa.getAfn();
                PmPacketData databuffer = pa.getDataBuffer();
                databuffer.dataBuff.rewind();

                PmPacket376DA da = new PmPacket376DA();
                PmPacket376DT dt = new PmPacket376DT();
                databuffer.getDA(da);
                databuffer.getDT(dt);

                if (afn == 0x00) {
                    results = new HashMap<>();
                    if (da.getPn() == 0) {
                        if (dt.getFn() == 1) {
                            results.put(RESULT, "开启终端搜索功能成功");
                            return results;
                        } else {
                            results.put(RESULT, "开启终端搜索功能失败");
                            return results;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }

        }
        return results;
    }
}
