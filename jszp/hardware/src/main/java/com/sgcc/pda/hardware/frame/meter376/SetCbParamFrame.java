package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA18;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA19;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.DateUtil;

import java.util.HashMap;


/**
 * 抄表参数设置
 * Author:GuangJie_Wang
 * Date::2016/7/22
 * Time:16:47.
 */
public class SetCbParamFrame extends BaseMeter376Frame {
    public static final int DKH = 0x11;//端口号
    //public static final int CONTROL_INT = 0x12;
    //public static final int BS8 = 0x13; //add by songweijie 2017/6/7
    public static final int BS16 = 0x12;//台区集中抄表控制字//add by songweijie 2017/6/7
    public static final int BS32 = 0x13;//抄表日-日期
    public static final int A19 = 0x14;//抄表日-时间
    public static final int INTERVAL_TIME = 0x15;//抄表间隔时间//add by songweijie 2017/6/7
    public static final int A18 = 0x16;//对电表广播校时定时时间
    public static final int CBINTERVAL = 0x17;//时段个数


    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        byte dkh = (byte) Integer.parseInt(String.valueOf(args.get(DKH)));
        // byte controlInt = (byte) Integer.parseInt(String.valueOf(args.get(CONTROL_INT)));
        String controlInt = (String) args.get(BS16);//add by songweijie 2017/6/7
        // String bs8 = (String) args.get(BS8);
        // String bs16 = (String) args.get(BS16);
        String bs32 = (String) args.get(BS32);
        DataTypeA19 a19_1 = (DataTypeA19) args.get(A19);
        byte intervalTime = (byte)Integer.parseInt(String.valueOf(args.get(INTERVAL_TIME)));//add by songweijie 2017/6/7
        DataTypeA18 a18 = (DataTypeA18) args.get(A18);
        int tmp2 = Integer.valueOf(String.valueOf(args.get(CBINTERVAL)));

        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5A);
        pack.getSeq().setValue((byte) 0x70);
        pack.setAfn((byte) 0x04);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(33));
        pack.getDataBuffer().put((byte) 01);
        pack.getDataBuffer().put(dkh);
        //保留原数据，根据选项修改 D0(允许或不允许自动抄表),D1位数据(抄所有表或只抄重点表)
        // pack.getDataBuffer().put(controlInt);
        //pack.getDataBuffer().putBS8(bs8);
        pack.getDataBuffer().putBS16(controlInt);//add by songweijie 2017/6/7
        pack.getDataBuffer().putBS8(bs32);
        pack.getDataBuffer().putA19(a19_1);
        pack.getDataBuffer().put(intervalTime);//add by songweijie 2017/6/7
        pack.getDataBuffer().putA18(a18);
        pack.getDataBuffer().put((byte) tmp2);
        // pack.getDataBuffer().put((byte) 0x01);
        pack.getDataBuffer().putA19(new DataTypeA19(DateUtil.parse("00:00"
                , DateUtil.DatePattern.HM)));
        pack.getDataBuffer().putA19(new DataTypeA19(DateUtil.parse("23:59",
                DateUtil.DatePattern.HM)));


        System.out.println(BcdUtils.binArrayToString(pack.getValue()));

        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return super.decode(bs);
    }
}
