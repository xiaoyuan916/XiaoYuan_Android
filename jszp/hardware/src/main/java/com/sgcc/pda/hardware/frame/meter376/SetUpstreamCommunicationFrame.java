package com.sgcc.pda.hardware.frame.meter376;

import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

import java.util.HashMap;

/**
 * CreateTime: 2015-12-18下午1:26
 * Author: wjkjinke00@126.com
 * Description:设置上行通信方式
 */
public class SetUpstreamCommunicationFrame extends BaseMeter376Frame {
    public static final int READ_UP_BYTE_1 = 0x11;
    public static final int READ_UP_BYTES_7 = 0x12;


    public static final int CONNECT_TCP_OR_UDP = 0x13;
    public static final int CONNECT_TCP = 0x14;
    public static final int CONNECT_UDP = 0x15;

    public static final int CONNECT_MODEL = 0x16;
    //混合模式
    public static final int CONNECT_MIXTURE = 0x17;
    //客户机模式
    public static final int CONNECT_CLIENT = 0x18;
    //服务器模式
    public static final int CONNECT_SERVERS = 0x19;

    public static final int ON_LINE_MODEL = 0x20;
    //永久在线
    public static final int ON_LINE_ALWAYS = 0x21;
    //被动激活
    public static final int ON_LINE_PASSIVE = 0x22;
    //时段在线
    public static final int ON_LINE_PERIOD = 0x23;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        byte[] bs7 = (byte[]) args.get(READ_UP_BYTES_7);
        int tcp_udp = (int) args.get(CONNECT_TCP_OR_UDP);
        int connect_model = (int) args.get(CONNECT_MODEL);
        int on_line_model = (int) args.get(ON_LINE_MODEL);

        byte b = (byte) 0xff;
        if (tcp_udp == CONNECT_TCP) {
            b = (byte) (b & 0x7f);
        }

        switch (connect_model) {
            case CONNECT_MIXTURE:
                b = (byte) (b & 0xcf);
                break;
            case CONNECT_CLIENT:
                b = (byte) (b & 0xdf);
                break;
            case CONNECT_SERVERS:
                b = (byte) (b & 0xef);
                break;
        }

        switch (on_line_model) {
            case ON_LINE_ALWAYS:
                b = (byte) (b & 0xfd);
                break;
            case ON_LINE_PASSIVE:
                b = (byte) (b & 0xfe);
                break;
            case ON_LINE_PERIOD:
                b = (byte) (b & 0xff);
                break;
        }

        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5A);

        pack.setAfn((byte) 0x04);
        pack.getSeq().setValue((byte) 0x71);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(8));
        pack.getDataBuffer().put(b);
        pack.getDataBuffer().put(bs7);

        return pack.getValue();
    }

    @Override
    public HashMap<Integer, Object> decode(byte[] bs) {
        return null;
    }
}
