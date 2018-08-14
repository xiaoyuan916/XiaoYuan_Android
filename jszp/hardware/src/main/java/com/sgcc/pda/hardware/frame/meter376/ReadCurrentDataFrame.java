package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA15;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.DateUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by songweijie on 2017/6/23.
 * 当前正向有功电能示值
 */
public class ReadCurrentDataFrame extends BaseMeter376Frame {
    public static final int MPED_INDEX = 0x02;//测量点号
    public static final int ZDCBSJ = 0x05;//终端抄表时间
    public static final int FLNUM = 0X06;//费率数
    public static final int TOTLE_DN = 0X07;//正向有功总电能示值
    public static final int[] CURRENT_DATA = new int[]{0x08, 0x09, 0x10, 0x11};//四费率
    public static final int FLAG = 0x12;//是否为有效数据
    public static final int RESULT_CODE = 0x13;
    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        String mpedIndex = (String) args.get(MPED_INDEX);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);
        pack.getSeq().setValue((byte) 0x60);
        pack.setAfn((byte) 0x0C);
        pack.getDataBuffer().putDA(new PmPacket376DA(Integer.valueOf(mpedIndex)));
        pack.getDataBuffer().putDT(new PmPacket376DT(129));
        /*pack.getDataBuffer().put((byte) 01);
        pack.getDataBuffer().put((byte) 10);*/
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
                    results.put(CURRENT_DATA[0], freezeData01);
                    results.put(CURRENT_DATA[1], freezeData02);
                    results.put(CURRENT_DATA[2], freezeData03);
                    results.put(CURRENT_DATA[3], freezeData04);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }

        }
        return results;
    }
}
