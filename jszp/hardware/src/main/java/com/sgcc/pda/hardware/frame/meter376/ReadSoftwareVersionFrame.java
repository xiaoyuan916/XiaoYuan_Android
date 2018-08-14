package com.sgcc.pda.hardware.frame.meter376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.DataTypeA20;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketData;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376.PmPacket376DT;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * CreateTime: 2015-12-22下午3:03
 * Author: wjkjinke00@126.com
 * Description:读取版本号
 */
public class ReadSoftwareVersionFrame extends BaseMeter376Frame {

    public static final int VERSION_NOW = 0x11;
    public static final int HARDWARE_VERSION_NOW = 0x12;

    @Override
    public byte[] encode(HashMap<Integer, Object> args) {
        String zdljdz = (String) args.get(ZDLJDZ);
        PmPacket376 pack = new PmPacket376();
        pack.getAddress().setRtua(zdljdz);
        pack.getAddress().getValue()[4] = (byte) 0x14;
        pack.getControlCode().setValue((byte) 0x5b);

        pack.getSeq().setValue((byte) 0x70);
        pack.setAfn((byte) 0x09);
        pack.getDataBuffer().putDA(new PmPacket376DA(0));
        pack.getDataBuffer().putDT(new PmPacket376DT(1));
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
                databuffer.getAscii(4);
                databuffer.getAscii(8);
                String version = databuffer.getAscii(4);

                DataTypeA20 date = databuffer.getA20();
                Date dateDate = date.getDate();
                SimpleDateFormat dateFormat = DateUtil.getDateFormat();
                String format = dateFormat.format(dateDate).substring(0, 11);
                databuffer.getAscii(11);
                databuffer.getAscii(4);
                String hardware_version = databuffer.getAscii(4);

//                resultShow.append("厂商代号:"+csdh+"\n");
//                resultShow.append("设备编号:"+sbbh+"\n");
//                resultShow.setText("当前软件版本:" + version + "\n");
//                resultShow.append("最新软件版本:" + "\n");
//                resultShow.append("软件安装时间:" + "\n");
//                resultShow.append("当前硬件版本:" + hardware_version + "\n");

                results.put(VERSION_NOW, version);
                results.put(HARDWARE_VERSION_NOW, hardware_version);
                return results;
            } catch (Exception e) {
                e.printStackTrace();
                return results;
            }
        }

        return results;
    }
}
