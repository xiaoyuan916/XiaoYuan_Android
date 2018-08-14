package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.util.DateUtil;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA18;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA19;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;


import java.text.SimpleDateFormat;
import java.util.HashMap;


/**
 * 读取抄表参数
 * Author:GuangJie_Wang
 * Date:2016/7/22
 * Time:15:36.
 */
public class ReadCbParamFrame extends BaseMeter376Frame {
    private static final String TAG = "ReadCbParamFrame";
    public static final int DKH = 0X11;//端口号

    public static final int CB_DKH = 0X12;//抄表端口号
    public static final int CB_AUTO = 0x13;//自动抄表
    public static final int CB_OPTION = 0x14;//自动抄表
    public static final int CB_INTERVAL = 0x15;//抄表间隔
    public static final int CB_TIME_INTERVAL_COUNT = 0x16;//时段个数
    public static final int CB_START_TIME = 0x17;//开始时间
    public static final int CB_END_TIME = 0x18;//结束时间

    public static final int BS8 = 0x19;
    public static final int BS32 = 0x20;
    public static final int A19 = 0x21;
    public static final int A18 = 0x22;
    public static final int CONTROL_INT = 0x23;


    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        byte dkh = (byte) Integer.parseInt((String) args.get(DKH));
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);
        pack.getSeq().setValue((byte) 0x60);
        pack.setAfn((byte) 0x0A);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(33));
        pack.getDataBuffer().put((byte) 01);
        pack.getDataBuffer().put(dkh);
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

                long datablockNum = databuffer.getBin(1);
                long dkh = databuffer.getBin(1);
                int controlInt = databuffer.getByte();
                int cb_auto = (((byte) controlInt & 01) == 00) ? 0 : 1;
                int cb_option = (((byte) controlInt & 02) == 02) ? 1 : 0;

                String bs8 = databuffer.getBS8();
                String bs32 = databuffer.getBS32();
                DataTypeA19 a19 = databuffer.getA19();
                String cb_interval = String.valueOf(databuffer.getBin(1));
                DataTypeA18 a18 = databuffer.getA18();
                String time_interval_count = String.valueOf(databuffer.getBin(1));
                SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DatePattern.HM);
                String startTime = dateFormat.format(databuffer.getA19().getDate());
                String end_time = dateFormat.format(databuffer.getA19().getDate());

                results.put(CB_DKH, String.valueOf(dkh));
                results.put(CONTROL_INT, controlInt);
                results.put(CB_AUTO, cb_auto);
                results.put(CB_OPTION, cb_option);
                results.put(BS8, bs8);
                results.put(BS32, bs32);
                results.put(A19, a19);
                results.put(CB_INTERVAL, cb_interval);
                results.put(A18, a18);
                results.put(CB_TIME_INTERVAL_COUNT, time_interval_count);
                results.put(CB_START_TIME, startTime);
                results.put(CB_END_TIME, end_time);

            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }
        }
        return results;
    }
}
