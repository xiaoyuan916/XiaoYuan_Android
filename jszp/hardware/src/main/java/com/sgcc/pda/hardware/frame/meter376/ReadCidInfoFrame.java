package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;


/**
 * CreateTime: 2015-12-24下午3:42
 * Author: wjkjinke00@126.com
 * Description:
 */
public class ReadCidInfoFrame extends BaseMeter376Frame {

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);
        pack.getSeq().setValue((byte) 0x60);
        pack.setAfn((byte) 0x0C);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(11));

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

                long datablockNum = databuffer.getBin(1);
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < datablockNum; i++) {
                    sb.append(getDataBlockString(databuffer));
                }
                results.put(RESULT, sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return results;
    }

    private String getDataBlockString(PmPacketData databuffer) {
        StringBuffer sb = new StringBuffer();
        sb.append("终端通信端口号:").append(databuffer.getBin(1)).append("\n");
        sb.append("要抄电能表数:").append(databuffer.getBin(2)).append("\n");
        sb.append("当前抄表状态标识:").append(databuffer.getBS8()).append("\n");
        sb.append("抄表成功块数:").append(databuffer.getBin(2)).append("\n");
        sb.append("抄重点表成功块数:").append(databuffer.getBin(1)).append("\n");
        SimpleDateFormat dateFormat = DateUtil.getDateFormat();
        sb.append("抄表开始时间:").append(dateFormat.format(databuffer.getA1().getDate())).append("\n");
        sb.append("抄表结束时间:").append(dateFormat.format(databuffer.getA1().getDate())).append("\n")
                .append("\n");
        return sb.toString();
    }
}
