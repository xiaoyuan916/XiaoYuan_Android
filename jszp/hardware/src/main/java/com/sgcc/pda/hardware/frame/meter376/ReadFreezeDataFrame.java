package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA15;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.DataSignExchangeUtils;
import com.sgcc.pda.hardware.util.DateUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * 读取集中器日冻结正向有功电能示值
 *
 * @author: hzwq
 * @date: 2017/2/15
 * @time: 10:43
 */

public class ReadFreezeDataFrame extends BaseMeter376Frame {

    public static final int START_DATE = 0x03;
    public static final int FREEZE_DATE = 0X04;//日冻结数据时标
    public static final int MPED_INDEX = 0x13;//测量点号
    public static final int DATASIGN = 0x14;//数据标识


    public static final int ZDCBSJ = 0x05;//终端抄表时间
    public static final int FLNUM = 0X06;//费率数
    public static final int TOTLE_DN = 0X07;//正向有功总电能示值
    public static final int[] FREEZE_DATA = new int[]{0x08, 0x09, 0x10, 0x11};//四费率
    public static final int FLAG = 0x12;//是否为有效数据


    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        PmPacket376 pa = new PmPacket376();
        int beginpos = 0;

        beginpos = PmPacket376.getMsgHeadOffset(bs, beginpos);
        if (beginpos != -1) {
            try {
                results = new HashMap<>();
                byte afn = pa.getAfn();
                pa.setValue(bs, beginpos);
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
                            results.put(RESULT, "全部确认");
                            return results;
                        } else if (dt.getFn() == 2) {
                            results.put(RESULT, "全部否认");
                            return results;
                        } else if (dt.getFn() == 3) {
                            results.put(RESULT, "按数据单元标识确认和否认");
                            return results;
                        } else {
                            results.put(RESULT, "请开启终端编程功能再进行对时");
                            return results;
                        }
                    }
                }
                String freezeDate = DataConvert.toHexString(databuffer.getBytes(3));
                results.put(FREEZE_DATE, DataConvert.strReverse(freezeDate, 0, freezeDate.length()));
                DataTypeA15 dataTypeA15 = databuffer.getA15();
                Date data15 = dataTypeA15.getDate();
                SimpleDateFormat dateFormat = DateUtil.getDateFormat();
                String formatA15 = dateFormat.format(data15);
                results.put(ZDCBSJ, formatA15);
                results.put(FLNUM, databuffer.getBin(1));
                String tag = DataConvert.toHexString(databuffer.getBytes(5));
                if (tag.equalsIgnoreCase("eeeeeeeeee")) {
                    results.put(FLAG, false);
                } else {
                    results.put(FLAG, true);
                    DecimalFormat df = new DecimalFormat("0.0000");
                    double f1 = databuffer.getA14().getValue();
                    double f2 = databuffer.getA14().getValue();
                    double f3 = databuffer.getA14().getValue();
                    double f4 = databuffer.getA14().getValue();
                    double totle = f1 + f2 + f3 + f4;
                    String freezeData01 = df.format(f1);
                    String freezeData02 = df.format(f2);
                    String freezeData03 = df.format(f3);
                    String freezeData04 = df.format(f4);
                    String FreezeTotle = df.format(totle);
                    results.put(TOTLE_DN, FreezeTotle);
                    results.put(FREEZE_DATA[0], freezeData01);
                    results.put(FREEZE_DATA[1], freezeData02);
                    results.put(FREEZE_DATA[2], freezeData03);
                    results.put(FREEZE_DATA[3], freezeData04);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }

        }
        return results;
    }

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        String startDate = (String) args.get(START_DATE);
        String mpedIndex = (String) args.get(MPED_INDEX);
        String dataSign = (String) args.get(DATASIGN);
        byte[] dadt = DataSignExchangeUtils.getData(dataSign, mpedIndex);
        if (dadt == null || dadt.length == 0) {
            return null;
        }
        PmPacket376 pack = new PmPacket376();

        pack.getAddress().setRtua(zdljdz);//终端地址
        pack.getAddress().getValue()[4] = (byte) 0x14;//主站地址
        pack.getControlCode().setValue((byte) 0x5b);//控制码
        pack.getSeq().setValue((byte) 0x60);//序列号
        pack.setAfn((byte) 0x0D);//功能码
        pack.getDataBuffer().put(dadt);
//        pack.getDataBuffer().put(new byte[]{0x01, 0x01});//DA
//        pack.getDataBuffer().put(new byte[]{0x01, 0x14});//DT
        pack.getDataBuffer().put(DataConvert.toBytes(DataConvert.strReverse(startDate, 0, startDate.length())));//开始日期倒序

        return pack.getValue();
    }

}
