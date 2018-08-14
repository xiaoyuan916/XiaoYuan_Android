package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;


/**
 * CreateTime: 2015-12-18下午1:42
 * Author: wjkjinke00@126.com
 * Description:读取上行通信方式
 */
public class ReadUpstreamCommunicationFrame extends BaseMeter376Frame {

    public static final int CONNECT_TCP_OR_UDP = 0x11;
    public static final int CONNECT_TCP = 0x12;
    public static final int CONNECT_UDP = 0x13;

    public static final int CONNECT_MODEL = 0x14;
    //混合模式
    public static final int CONNECT_MIXTURE = 0x15;
    //客户机模式
    public static final int CONNECT_CLIENT = 0x16;
    //服务器模式
    public static final int CONNECT_SERVERS = 0x17;

    public static final int ON_LINE_MODEL = 0x18;
    //永久在线
    public static final int ON_LINE_ALWAYS = 0x19;
    //被动激活
    public static final int ON_LINE_PASSIVE = 0x20;
    //时段在线
    public static final int ON_LINE_PERIOD = 0x21;

    public static final int READ_UP_BYTES_7 = 0x22;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);

        pack.setAfn((byte) 0x0A);
        pack.getSeq().setValue((byte) 0x71);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(8));

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
                byte afn = pa.getAfn();
                PmPacketData databuffer = pa.getDataBuffer();
                databuffer.dataBuff.rewind();
                PmPacket376DA da = new PmPacket376DA();
                PmPacket376DT dt = new PmPacket376DT();
                databuffer.getDA(da);
                databuffer.getDT(dt);


                byte[] bytes = databuffer.getBytes(1);
                byte b = bytes[0];
                if ((b & 0x80) == 0x80) {
                    results.put(CONNECT_TCP_OR_UDP, CONNECT_UDP);
                } else {
                    results.put(CONNECT_TCP_OR_UDP, CONNECT_TCP);
                }

                byte i = (byte) (b & 0x30);
                if (i == 0) {
                    results.put(CONNECT_MODEL, CONNECT_MIXTURE);
                } else if (i == (byte) 0x10) {
                    results.put(CONNECT_MODEL, CONNECT_CLIENT);
                } else if (i == (byte) 0x20) {
                    results.put(CONNECT_MODEL, CONNECT_SERVERS);
                }

                byte i1 = (byte) (b & 0x3);
                if (i1 == (byte) 0x1) {
                    results.put(ON_LINE_MODEL, ON_LINE_ALWAYS);
                } else if (i1 == (byte) 0x2) {
                    results.put(ON_LINE_MODEL, ON_LINE_PASSIVE);
                } else if (i1 == (byte) 0x3) {
                    results.put(ON_LINE_MODEL, ON_LINE_PERIOD);
                }

                byte[] mBytesUpConnect = databuffer.getBytes(7);
                results.put(READ_UP_BYTES_7, mBytesUpConnect);

                return results;
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }
        }
        return results;
    }
}
