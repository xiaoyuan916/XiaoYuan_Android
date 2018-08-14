package com.sgcc.pda.hardware.frame;


import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645Address;
import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645ControlCode;
import com.sgcc.pda.hardware.srv.protocol.dl645.Gb645MeterPacket;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdDataBuffer;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;
import com.sgcc.pda.hardware.util.ByteUtils;
import com.sgcc.pda.hardware.util.Constant;
import com.sgcc.pda.hardware.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * CreateTime: 15/5/21下午2:05
 * Author: wjkjinke00@126.com
 * Description:
 */
public class Frame485Cheker {
    private Frame485Cheker() {
    }
    //07
    //68 78 04 79 11 00 00 68 91 08 34 34 33 37 37 54 38 48 4c 16

    public static boolean frame485Zero7IsOk(byte[] buf, String bjdz) {
        try {
            for (int i = 0; i < buf.length; i++) {
                if (buf[i] == (byte) 0x68 && buf[i + 7] == (byte) 0x68 && buf[i + 19] == (byte)
                        0x16) {
                    byte[] b = new byte[]{buf[i + 1], buf[i + 2], buf[i + 3], buf[i + 4], buf[i +
                            5], buf[i + 6]};
                    byte[] bjArray = BcdUtils.reverseBytes(BcdUtils.stringToByteArray(bjdz));
                    boolean equals = ByteUtils.equals(b, bjArray);
                    if (equals) {
                        if (buf[i + 8] == (byte) 0x91) {
                            return true;
                        }
                    } else {
                        continue;
                    }

                } else {
                    continue;
                }
            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }

    //97
    //68 78 04 79 11 00 00 68 81 06 43 F3 37 54 38 48 4c 16
    public static boolean frame485Nine7IsOk(byte[] buf, String bjdz) {
        try {
            for (int i = 0; i < buf.length; i++) {
                if (buf[i] == (byte) 0x68 && buf[i + 7] == (byte) 0x68 && buf[i + 17] == (byte)
                        0x16) {
                    byte[] b = new byte[]{buf[i + 1], buf[i + 2], buf[i + 3], buf[i + 4], buf[i +
                            5], buf[i + 6]};
                    byte[] bjArray = BcdUtils.reverseBytes(BcdUtils.stringToByteArray(bjdz));
                    boolean equals = ByteUtils.equals(b, bjArray);
                    if (equals) {
                        if (buf[i + 8] == (byte) 0x81) {
                            return true;
                        }
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static String getResultOfInfrCallback(int gy, byte[] buf, String bjdz) {
        int offset = Gb645MeterPacket.getMsgHeadOffset(buf, 0);
        if (offset == -1) {
            return "读取失败";
        }

        Gb645MeterPacket packet = Gb645MeterPacket.getPacket(buf, offset);
        Gb645Address address = packet.getAddress();
        String addressStr = address.getAddress();
        if (!bjdz.equals(addressStr)) {
            return "读取失败";
        }

        Gb645ControlCode controlCode = packet.getControlCode();
        byte value = controlCode.getValue();
        if (gy == 0) {
            if (value != (byte) 0x91) {
                return "读取失败";
            }
        } else {
            if (value != (byte) 0x81) {
                return "读取失败";
            }
        }

        BcdDataBuffer data = packet.getData();
        byte[] values = data.getValue();
        String tmp = BcdUtils.binArrayToString(values);
        Date dateDate = data.getDate(tmp);
        SimpleDateFormat dateFormat = DateUtil.getDateFormat();
        String format = dateFormat.format(dateDate);
        String date = format.substring(0, 4) + "-" + format.substring(5, 7) + "-" + format
                .substring(8, 10);
        int leng = values.length;
        StringBuilder sb = new StringBuilder();
        sb.append(BcdUtils.binArrayToString(new byte[]{values[leng - 1]})
        ).append(":").append
                (BcdUtils
                        .binArrayToString(new byte[]{values[leng - 2]})).append(":")
                .append(BcdUtils.binArrayToString(new byte[]{values[leng - 3]}));


        return date;
    }

    public static String getResultOf485Callback(int type, int gy, byte[] buf) {
        if (buf == null) {
            switch (type) {
                case Constant.TYPE_METER_TIME:
                    return "时钟读取失败\n";
                case Constant.TYPE_METER_9010:
                    return "正向有功总电能读取失败\n";
                case Constant.LAST_DAY_1_DATA:
                case Constant.LAST_DAY_2_DATA:
                case Constant.LAST_DAY_3_DATA:
                case Constant.LAST_DAY_4_DATA:
                case Constant.LAST_DAY_5_DATA:
                case Constant.LAST_DAY_6_DATA:
                case Constant.LAST_DAY_7_DATA:
                case Constant.LAST_DAY_8_DATA:
                case Constant.LAST_DAY_9_DATA:
                    return "日冻结正向数据读取失败\n";
                case Constant.LAST_DAY_1_TIME:
                case Constant.LAST_DAY_2_TIME:
                case Constant.LAST_DAY_3_TIME:
                case Constant.LAST_DAY_4_TIME:
                case Constant.LAST_DAY_5_TIME:
                case Constant.LAST_DAY_6_TIME:
                case Constant.LAST_DAY_7_TIME:
                case Constant.LAST_DAY_8_TIME:
                case Constant.LAST_DAY_9_TIME:
                    return "日冻结正向数据时间读取失败\n";
            }
        }

        int offset = Gb645MeterPacket.getMsgHeadOffset(buf, 0);
        if (offset == -1) {
            switch (type) {
                case Constant.TYPE_METER_TIME:
                    return "时钟读取失败\n";
                case Constant.TYPE_METER_9010:
                    return "正向有功总电能读取失败\n";
                case Constant.LAST_DAY_1_DATA:
                case Constant.LAST_DAY_2_DATA:
                case Constant.LAST_DAY_3_DATA:
                case Constant.LAST_DAY_4_DATA:
                case Constant.LAST_DAY_5_DATA:
                case Constant.LAST_DAY_6_DATA:
                case Constant.LAST_DAY_7_DATA:
                case Constant.LAST_DAY_8_DATA:
                case Constant.LAST_DAY_9_DATA:
                    return "日冻结正向数据读取失败\n";
                case Constant.LAST_DAY_1_TIME:
                case Constant.LAST_DAY_2_TIME:
                case Constant.LAST_DAY_3_TIME:
                case Constant.LAST_DAY_4_TIME:
                case Constant.LAST_DAY_5_TIME:
                case Constant.LAST_DAY_6_TIME:
                case Constant.LAST_DAY_7_TIME:
                case Constant.LAST_DAY_8_TIME:
                case Constant.LAST_DAY_9_TIME:
                    return "日冻结正向数据时间读取失败\n";
            }
        }

        Gb645MeterPacket packet = Gb645MeterPacket.getPacket(buf, offset);
        Gb645ControlCode controlCode = packet.getControlCode();
        byte value = controlCode.getValue();
        if (gy == 0) {
            if (value != (byte) 0x91) {
                switch (type) {
                    case Constant.TYPE_METER_TIME:
                        return "时钟读取失败\n";
                    case Constant.TYPE_METER_9010:
                        return "正向有功总电能读取失败\n";
                    case Constant.LAST_DAY_1_DATA:
                    case Constant.LAST_DAY_2_DATA:
                    case Constant.LAST_DAY_3_DATA:
                    case Constant.LAST_DAY_4_DATA:
                    case Constant.LAST_DAY_5_DATA:
                    case Constant.LAST_DAY_6_DATA:
                    case Constant.LAST_DAY_7_DATA:
                    case Constant.LAST_DAY_8_DATA:
                    case Constant.LAST_DAY_9_DATA:
                        return "日冻结正向数据读取失败\n";
                    case Constant.LAST_DAY_1_TIME:
                    case Constant.LAST_DAY_2_TIME:
                    case Constant.LAST_DAY_3_TIME:
                    case Constant.LAST_DAY_4_TIME:
                    case Constant.LAST_DAY_5_TIME:
                    case Constant.LAST_DAY_6_TIME:
                    case Constant.LAST_DAY_7_TIME:
                    case Constant.LAST_DAY_8_TIME:
                    case Constant.LAST_DAY_9_TIME:
                        return "日冻结正向数据时间读取失败\n";
                }
            }
        } else {
            if (value != (byte) 0x81) {
                switch (type) {
                    case Constant.TYPE_METER_TIME:
                        return "时钟读取失败\n";
                    case Constant.TYPE_METER_9010:
                        return "正向有功总电能读取失败\n";
                    case Constant.LAST_DAY_1_DATA:
                    case Constant.LAST_DAY_2_DATA:
                    case Constant.LAST_DAY_3_DATA:
                    case Constant.LAST_DAY_4_DATA:
                    case Constant.LAST_DAY_5_DATA:
                    case Constant.LAST_DAY_6_DATA:
                    case Constant.LAST_DAY_7_DATA:
                    case Constant.LAST_DAY_8_DATA:
                    case Constant.LAST_DAY_9_DATA:
                        return "日冻结正向数据读取失败\n";
                    case Constant.LAST_DAY_1_TIME:
                    case Constant.LAST_DAY_2_TIME:
                    case Constant.LAST_DAY_3_TIME:
                    case Constant.LAST_DAY_4_TIME:
                    case Constant.LAST_DAY_5_TIME:
                    case Constant.LAST_DAY_6_TIME:
                    case Constant.LAST_DAY_7_TIME:
                    case Constant.LAST_DAY_8_TIME:
                    case Constant.LAST_DAY_9_TIME:
                        return "日冻结正向数据时间读取失败\n";
                }
            }
        }

        BcdDataBuffer data = packet.getData();
        byte[] values = data.getValue();
        String tmp = BcdUtils.binArrayToString(values);
        int leng = values.length;
        StringBuilder sb = new StringBuilder();

        switch (type) {
            case Constant.TYPE_METER_TIME:
                sb.append("表计时钟:").append(BcdUtils.binArrayToString(new
                        byte[]{values[leng - 1]})
                ).append(":").append
                        (BcdUtils
                                .binArrayToString(new byte[]{values[leng - 2]})).append(":")
                        .append(BcdUtils.binArrayToString(new byte[]{values[leng - 3]}));
                break;
            case Constant.TYPE_METER_9010:
                sb.append("正向有功总电能:").append(BcdUtils.binArrayToString(new
                        byte[]{values[leng -
                        1]})).append(BcdUtils.binArrayToString(new byte[]{values[leng - 2]})).append
                        (BcdUtils.binArrayToString(new byte[]{values[leng - 3]}))
                        .append(".").append(BcdUtils.binArrayToString(new byte[]{values[leng -
                        4]}));
                break;
            case Constant.LAST_DAY_1_DATA:
            case Constant.LAST_DAY_2_DATA:
            case Constant.LAST_DAY_3_DATA:
            case Constant.LAST_DAY_4_DATA:
            case Constant.LAST_DAY_5_DATA:
            case Constant.LAST_DAY_6_DATA:
            case Constant.LAST_DAY_7_DATA:
            case Constant.LAST_DAY_8_DATA:
            case Constant.LAST_DAY_9_DATA:
                int day = type - Constant.LAST_DAY_1_DATA + 1;
                sb.append("日冻结正向数据:").append("\n").append("(上" + day + "次)").append(BcdUtils
                        .binArrayToString(new
                                byte[]{values[leng -
                                1]})).append(BcdUtils.binArrayToString(new byte[]{values[leng -
                        2]})).append
                        (BcdUtils.binArrayToString(new byte[]{values[leng - 3]}))
                        .append(".").append(BcdUtils.binArrayToString(new byte[]{values[leng -
                        4]}));
                break;
            case Constant.LAST_DAY_1_TIME:
            case Constant.LAST_DAY_2_TIME:
            case Constant.LAST_DAY_3_TIME:
            case Constant.LAST_DAY_4_TIME:
            case Constant.LAST_DAY_5_TIME:
            case Constant.LAST_DAY_6_TIME:
            case Constant.LAST_DAY_7_TIME:
            case Constant.LAST_DAY_8_TIME:
            case Constant.LAST_DAY_9_TIME:
                int day2 = type - Constant.LAST_DAY_1_TIME + 1;
                sb.append("日冻结正向数据时间:").append("\n").append("(上" + day2 + "次)").append(BcdUtils
                        .binArrayToString(new byte[]{values[leng - 1]})
                ).append("-").append
                        (BcdUtils
                                .binArrayToString(new byte[]{values[leng - 2]})).append("-")
                        .append(BcdUtils.binArrayToString(new byte[]{values[leng - 3]}));
                break;
        }
        sb.append("\n");
        return sb.toString();
    }
}
