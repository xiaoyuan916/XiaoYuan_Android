package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;

/**
 * Author：GuangJie_Wang
 * Date: 2016/4/20
 * Time: 15:45.
 */
public class TerminalTaskReadOrDown {
    /**
     * 抄表参数
     *
     * @param terminalAddress
     * @param i
     * @return
     */
    public static byte[] getMeterarameter(String terminalAddress, byte i) {
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(terminalAddress);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);

        pack.setAfn((byte) 0x0A);
        pack.getSeq().setValue((byte) 0x69);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(10));
        pack.getDataBuffer().put(new byte[]{0x01, 0x00, i, 0x00});

        return pack.getValue();
    }


    /**
     * 开启任务
     *
     * @param terminalAddress
     * @param jonNum          任务号
     * @return
     */
    public static byte[] openTask(String terminalAddress, int jonNum) {
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(terminalAddress);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5A);

        pack.setAfn((byte) 0x04);
        pack.getSeq().setValue((byte) 0x73);
        pack.getDataBuffer().putDA(new PmPacket376DA(jonNum));
        pack.getDataBuffer().putDT(new PmPacket376DT(68));
        pack.getDataBuffer().put((byte) 0x55);

        return pack.getValue();
    }

    /**
     * 任务是否开启
     *
     * @param terminalAddress
     * @param jonNum
     * @return
     */
    public static byte[] getTaskOpenFlag(String terminalAddress, int jonNum) {
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(terminalAddress);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);

        pack.setAfn((byte) 0x0a);
        pack.getSeq().setValue((byte) 0x62);
        pack.getDataBuffer().putDA(new PmPacket376DA(jonNum));
        pack.getDataBuffer().putDT(new PmPacket376DT(68));

        return pack.getValue();
    }


    /**
     * 电压合格率日冻结上送任务
     *
     * @param terminalAddress
     * @param jonNum
     * @return
     */
    public static byte[] getTask(String terminalAddress, int jonNum) {
        // i = 3;//1-8
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(terminalAddress);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5B);

        pack.setAfn((byte) 0x0a);
        pack.getSeq().setValue((byte) 0x63);
        pack.getDataBuffer().putDA(new PmPacket376DA(jonNum));
        pack.getDataBuffer().putDT(new PmPacket376DT(66));
        return pack.getValue();
    }
}
