package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.frame.ClassF10;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.util.DataConvert;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-18下午3:40
 * Author: wjkjinke00@126.com
 * Description:电能表/交流采样装置配置参数
 */
public class RecordIssueFrame extends BaseMeter376Frame {

    //本次电能表/交流采样装置配置数量n
    public static final int METERNUM = 0x10;
    //电能表/交流采样装置序号
    public static final int POSITION = 0x11;
    //所属测量点号
    public static final int MEASUREPOINT = 0x12;
    //通信速率
    public static final int SPEED = 0x13;
    //端口号
    public static final int PORT = 0x14;
    //通信协议类型
    public static final int PROCOLE = 0x15;
    //通信地址
    public static final int ADDRESS = 0x16;
    //通信密码
    public static final int PASSWORD = 0x17;
    //电能费率个数
    public static final int FLNUM = 0x18;
    //有功电能示值整数位
    public static final int ZHENSHUNUM = 0x19;
    //小数位个数
    public static final int XIAOSHUNUM = 0x1A;
    //所属采集器通信地址
    public static final int CAIJIQIADDRESS = 0x1B;
    //用户大类号
    public static final int BIGNUM = 0x1C;
    //用户小类号
    public static final int SMALLNUM = 0x1D;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        int meterNum = (int) args.get(METERNUM);
        int position = (int) args.get(POSITION);
        int measurepoint = (int) args.get(MEASUREPOINT);
        int speed = (int) args.get(SPEED);
        int port = (int) args.get(PORT);
        int speedPort = (speed << 5) | port;
        String speedandport = DataConvert.toHexString(speedPort, 1);
        int procole = (int) args.get(PROCOLE);
        String address = (String) args.get(ADDRESS);
        String password = (String) args.get(PASSWORD);
        int flnum = (int) args.get(FLNUM);
        //整数位0~3对应4~7
        int zhengshunum = (int) args.get(ZHENSHUNUM) - 4;
        //小数位0~3对应1~4
        int xiaoshunum = (int) args.get(XIAOSHUNUM) - 1;
        int zhengshuAndxiaoshu = (zhengshunum << 2) | xiaoshunum;
        String zhenshuandxiaoshunum = DataConvert.toHexString(zhengshuAndxiaoshu, 1);
        String caijiqiaddress = (String) args.get(CAIJIQIADDRESS);
        int bigNum = (int) args.get(BIGNUM);
        int smallNum = (int) args.get(SMALLNUM);
        int bigandSmall = (bigNum << 4) | smallNum;
        String bigandsmallnum = DataConvert.toHexString(bigandSmall, 1);
        ClassF10 classF10 = new ClassF10();
        String positionString = DataConvert.toHexString(position, 2);
        assert positionString != null;
        classF10.setPositionString(DataConvert.strReverse(positionString, 0, positionString.length()));
        String measurePointString = DataConvert.toHexString(measurepoint, 2);
        assert measurePointString != null;
        classF10.setMeasurePointString(DataConvert.strReverse(measurePointString, 0, measurePointString.length()));
        classF10.setSpeedAndPortString(speedandport);
        classF10.setProcoleString(DataConvert.toHexString(procole, 1));
        classF10.setAddressString(DataConvert.strReverse(address, 0, address.length()));
        classF10.setPasswordString(DataConvert.strReverse(password, 0, password.length()));
        classF10.setFlnumString(DataConvert.toHexString(flnum, 1));
        classF10.setZhenshuAndXiaoshuNumString(zhenshuandxiaoshunum);
        classF10.setCaijiqiAddressString(DataConvert.strReverse(caijiqiaddress, 0, caijiqiaddress.length()));
        classF10.setBigAndSmallNumString(bigandsmallnum);

        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5a);
        pack.getSeq().setValue((byte) 0x73);
        pack.setAfn((byte) 0x04);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(
                new PmPacket376DT().setValue(new byte[]{0x02, 0x01}));// F10
        String meterNumString = DataConvert.toHexString(meterNum, 2);
        assert meterNumString != null;
        pack.getDataBuffer().put(DataConvert.toBytes(DataConvert.strReverse(meterNumString, 0, meterNumString.length())));
        pack.getDataBuffer().put(classF10.classToByte());
        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return super.decode(bs);
    }
}
