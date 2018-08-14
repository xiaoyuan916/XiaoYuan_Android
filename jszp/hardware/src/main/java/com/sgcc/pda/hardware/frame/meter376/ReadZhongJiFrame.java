package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.frame.Frame485Cheker;
import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645MeterPacket;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.util.Constant;

import java.nio.ByteBuffer;
import java.util.HashMap;


/**
 * CreateTime: 2015-12-22下午3:44
 * Author: wjkjinke00@126.com
 * Description:
 */
public class ReadZhongJiFrame extends BaseMeter376Frame {

    public static final int METER_ADDRESS = 0x11;
    public static final int DKH = 0x12;
    public static final int ZHONG_JI_RESULT = 0x13;
    public static final int ZHONG_JI_RES = 0x15;
    public static final int ZHONG_JI_FLAG = 0x14;


    private boolean meter_07;

    private String flag;

    public ReadZhongJiFrame(boolean meter_07) {
        this.meter_07 = meter_07;
    }

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        String meter_address = (String) args.get(METER_ADDRESS);
        byte dkh = (byte) args.get(DKH);
        flag = (String) args.get(ZHONG_JI_FLAG);

        PmPacket376 pack = new PmPacket376();
        pack.getControlCode().setIsOrgniger(true).setIsUpDirect(false)
                .setIsDownDirectFrameCountAvaliable(false)
                .setFunctionKey((byte) 0x0b);
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().setIsGroupAddress(false)
                .setMastStationId((byte) 0x01);
        pack.getSeq().setIsFinishFrame(true).setIsFirstFrame(true)
                .setIsNeedCountersign(false).setIsTpvAvalibe(false)
                .setSeq((byte) 0x04);
        pack.setAfn((byte) 0x10);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(1));

        if (meter_07) {
            // 中继报文
            Gb645MeterPacket meterpack = new Gb645MeterPacket(meter_address);
            meterpack.getControlCode().isFromMast(true)
                    .isHasHouxuzhen(false).isYichang(false)
                    .setFuncCode((byte) 17);
            if ("1".equals(flag)) {
                meterpack.getData().putLongWord(0x04000101);
            } else {
                meterpack.getData().putLongWord(0x00010000);
            }
            // 将中继报文放入终端报文中
//				pack.getDataBuffer().put((byte) 0x1f).put((byte) 0x6b)
            pack.getDataBuffer().put(dkh).put((byte) 0x6b)
                    .put((byte) 0xe4).put((byte) 0x64)
                    .putWord(meterpack.getValue().length + 4);
            pack.getDataBuffer().put((byte) 0xfe).put((byte) 0xfe)
                    .put((byte) 0xfe).put((byte) 0xfe);
            pack.getDataBuffer().put(meterpack.getValue());
        } else {
            Gb645MeterPacket meterpack97 = new Gb645MeterPacket(
                    meter_address);
            meterpack97.getControlCode().setValue((byte) 0x01);
            if ("1".equals(flag)) {
                meterpack97.getData().putWord(0xC010);
            } else {
                meterpack97.getData().putWord(0x9010);
            }
            byte[] dt = new byte[meterpack97.getValue().length + 4];

            System.arraycopy(new byte[]{(byte) 0xFE, (byte) 0xFE,
                    (byte) 0xFE, (byte) 0xFE}, 0, dt, 0, 4);
            System.arraycopy(meterpack97.getValue(), 0, dt, 4,
                    meterpack97.getValue().length);

            byte[] data = new byte[dt.length];
            ByteBuffer buf = ByteBuffer.wrap(data);
            buf.put(dt);
            // 将中继报文放入终端报文中
            pack.getDataBuffer().put(dkh).put((byte) 0x6b)
                    .put((byte) 0x85).put((byte) 0x64)
                    .putWord(data.length + 4);
            pack.getDataBuffer().put(data);
        }
        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        byte[] b = null;
        PmPacket376 pa = new PmPacket376();
        int beginpos = 0;
        beginpos = PmPacket376.getMsgHeadOffset(bs, beginpos);
        if (beginpos != -1) {
            pa.setValue(bs, beginpos);
            byte[] metterbuff = pa.getDataBuffer().getValue();
            int meterbegin = Gb645MeterPacket.getMsgHeadOffset(metterbuff,
                    0);
            if (meterbegin != -1) {
                results = new HashMap<>();
                Gb645MeterPacket mp = Gb645MeterPacket.getPacket(
                        metterbuff, meterbegin);
                b = mp.getValue();
                String s = Frame485Cheker.getResultOf485Callback(Constant
                        .TYPE_METER_9010, meter_07 ? 0 : 1, b);
                results.put(ZHONG_JI_RESULT, s);
                return results;
            } else {
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
                            results.put(ZHONG_JI_RES, "全部确认");
                            return results;
                        } else if (dt.getFn() == 2) {
                            results.put(ZHONG_JI_RES, "否认帧");
                            return results;
                        } else if (dt.getFn() == 3) {
                            results.put(ZHONG_JI_RES, "按数据单元标识确认和否认");
                            return results;
                        } else {
                            results.put(ZHONG_JI_RES, "请开启终端编程功能再进行对时");
                            return results;
                        }
                    }
                }
            }
        }
        return results;
    }
}
