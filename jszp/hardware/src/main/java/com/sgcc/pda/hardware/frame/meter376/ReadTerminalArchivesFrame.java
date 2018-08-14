package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA12;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.ByteUtils;
import com.sgcc.pda.hardware.util.Convert;

import java.util.HashMap;


/**
 * 读取终端档案信息
 * Author：GuangJie_Wang
 * Date: 2016/6/13
 * Time: 10:30.
 */
public class ReadTerminalArchivesFrame extends BaseMeter376Frame {

    public static final int MPED_INDEX = 0x02;//测量点号

    public static final int TERMINAL_NO = 0x11;//电能表/交流采样序号
    public static final int TERMINAL_CLDH = 0x12;//所属u测量点号
    public static final int TERMINAL_TXSL = 0x13;//通信速率
    public static final int TERMINAL_DKH = 0x14;//端口号
    public static final int TERMINAL_TXXYLX = 0x15;//通信协议
    public static final int TERMINAL_TXDZ = 0x16;//通信地址
    public static final int TERMINAL_TXMM = 0x17;//通信密码
    public static final int TERMINAL_DNFLGS = 0x18;//电能费率个数
    public static final int TERMINAL_ZSW = 0x19;//有功电能示值整数位
    public static final int TERMINAL_XSW = 0x20;//无功电能示值小数位
    public static final int TERMINAL_CJQTXDZ = 0x21;//采集器通信地址
    public static final int TERMINAL_DLH = 0x22;//用户大类号
    public static final int TERMINAL_XLH = 0x23;//用户小类号
    public static final int TERMINAL_TXSLDKH = 0x24;//通信速率及端口号

    //68 42 00 42 00 68 5B 17 11 39 71 14 0A 60 00 00 02 01 01 00 02 00 B1 16

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        String mpedIndex = (String) args.get(MPED_INDEX);
        byte[] no = BcdUtils.reverseBytes(ByteUtils.intToBytes(Integer.valueOf(mpedIndex), 2));
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);
        pack.getSeq().setValue((byte) 0x60);
        pack.setAfn((byte) 0x0A);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(10));
        pack.getDataBuffer().put((byte) 01);
        pack.getDataBuffer().put((byte) 00);
        pack.getDataBuffer().put(no);//序号
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
                pa.setValue(bs, beginpos);
                PmPacketData databuffer = pa.getDataBuffer();
                databuffer.dataBuff.rewind();
                PmPacket376DA da = new PmPacket376DA();
                PmPacket376DT dt = new PmPacket376DT();
                databuffer.getDA(da);
                databuffer.getDT(dt);
                databuffer.getBin(2);// 本次电能表/交流采样装置配置数量
                long no = databuffer.getBin(2);//电能表/交流采样装置序号
                long cldh = databuffer.getBin(2);//所属测量点号
                long TD = databuffer.getBin(1);//通信速率及端口号
                int txsl = Convert.ToIntOffset((byte) TD);//通信速率
                int dkh = Convert.ToIntEnd((byte) TD);//端口号
                long xy = databuffer.getBin(1);//通信协议类型
                DataTypeA12 txAddress = databuffer.getA12();//通信地址；
                byte[] arr = txAddress.getArray();
                String address = Convert.toHexString(arr);
                long pwd = databuffer.getBin(6);//通信密码
                String dnfNUm = databuffer.getBS8();//电能费率个数
                int dnNUm = Convert.byteToInt((byte) Convert.toInt(dnfNUm));
                String xswNUm = databuffer.getBS8();//有功电能示值整数位及小数位个数
                int zxsw = Convert.toInt(xswNUm);
                int zsw = Convert.byte4ToInt((byte) zxsw) + 4;
                int xsw = Convert.byteToInt((byte) zxsw) + 1;
                DataTypeA12 cjqAddress = databuffer.getA12();//所属采集器通信地址
                byte[] cjq = cjqAddress.getArray();
                String cAddress = Convert.toHexString(cjq);
                String dxlh = databuffer.getBS8();//用户大类号及用户小类号、
                int dx = Convert.toInt(dxlh);
                int dlh = Convert.byte4ToInt((byte) dx);
                int xlh = Convert.byteToInt((byte) dx);

                results.put(TERMINAL_NO, String.valueOf(no));
                results.put(TERMINAL_CLDH, String.valueOf(cldh));
                results.put(TERMINAL_TXSLDKH, String.valueOf(TD));
                results.put(TERMINAL_TXSL, String.valueOf(txsl));
                results.put(TERMINAL_DKH, String.valueOf(dkh));
                results.put(TERMINAL_TXXYLX, String.valueOf(xy));
                results.put(TERMINAL_TXDZ, address);
                results.put(TERMINAL_TXMM, String.valueOf(pwd));
                results.put(TERMINAL_DNFLGS, String.valueOf(dnNUm));
                results.put(TERMINAL_ZSW, String.valueOf(zsw));
                results.put(TERMINAL_XSW, String.valueOf(xsw));
                results.put(TERMINAL_CJQTXDZ, cAddress);
                results.put(TERMINAL_DLH, String.valueOf(dlh));
                results.put(TERMINAL_XLH, String.valueOf(xlh));
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }
        }
        return results;
    }
}
