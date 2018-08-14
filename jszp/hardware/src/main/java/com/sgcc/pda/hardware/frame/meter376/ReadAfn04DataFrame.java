package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.sdk.utils.StringUtil;

import java.util.HashMap;


/**
 * 读取集中器日冻结正向有功电能示值
 *
 * @author: hzwq
 * @date: 2017/2/15
 * @time: 10:43
 */

public class ReadAfn04DataFrame extends BaseMeter376Frame {
    public static final int DA_DT = 0x14;//数据标识da
    public static final int DATA = 0x16;//数据单元


    public int decode1(byte[] bs, StringBuffer results) {
        int ret = -1;
        PmPacket376 pa = new PmPacket376();
        int beginpos = 0;
        beginpos = PmPacket376.getMsgHeadOffset(bs, beginpos);
        if (beginpos != -1) {
            try {
                pa.setValue(bs, beginpos);
                PmPacketData databuffer = pa.getDataBuffer();
                databuffer.dataBuff.rewind();
                PmPacket376DA da = new PmPacket376DA();
                PmPacket376DT dt = new PmPacket376DT();
                databuffer.getDA(da);
                databuffer.getDT(dt);
                if (pa.getAfn() == 0x00) {
                    if (da.getPn() == 0) {
                        if (dt.getFn() == 1) {
                            ret = 0;
                            results.append("全部确认");
                        } else if (dt.getFn() == 2) {
                            results.append("全部否认");
                        } else if (dt.getFn() == 3) {
                           results.append("按数据单元标识确认和否认");
                        } else if (dt.getFn() == 4) {
                            results.append("硬件安全认证错误应答");
                        } else {
                            results.append("未知错误");
                        }

                    } else {
                        results.append("返回帧格式错误");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                results.append("解析失帧败");
                return -1;
            }

        }
        return ret;
    }

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        String data = (String) args.get(DATA);
        String jzqDA_dt = (String) args.get(DA_DT);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);//终端地址
        pack.getAddress().getValue()[4] = (byte) 0x14;//主站地址
        pack.getControlCode().setValue((byte) 0x4a);//控制码
        pack.getSeq().setValue((byte) 0xf0);//序列号
        pack.setAfn((byte) 0x04);//功能码
        pack.getDataBuffer().put(DataConvert.toBytes(jzqDA_dt));
        if (!StringUtil.isBlank(data)) {
            pack.getDataBuffer().put(DataConvert.toBytes(data));
        }
        return pack.getValue();
    }

    /**
     * 是否是无效数据
     *
     * @param packetData
     * @param bytes
     * @return
     */
    public boolean isInvalidData(PmPacketData packetData, int bytes) {
        String s = DataConvert.toHexString(packetData.getBytes(bytes));
        StringBuffer s1 = new StringBuffer();
        for (int i = 0; i < bytes; i++) {
            s1.append("ee");
        }
        if (s.equalsIgnoreCase(s1.toString())) {
            return true;
        }
        packetData.dataBuff.position(packetData.dataBuff.position() - bytes);
        return false;
    }
}
