package com.sgcc.pda.hardware.device;

import android.os.Build;

import com.sgcc.pda.hardware.SOConfig;
import com.sgcc.pda.hardware.shell.Shell;
import com.sgcc.pda.hardware.util.ByteUtils;
import com.sgcc.pda.hardware.util.NewProtocol;
import com.thinta.product.zdevice.InfraMeter;
import com.thinta.product.zdevice.ZCommand;

/**
 * Author：GuangJie_Wang
 * Date: 2016/6/12
 * Time: 12:31.
 */
public class A1ZInfra {
    private static InfraMeter infraMeter = new InfraMeter();
    private static final int BUFFER_LENGTH = 512;
    public static byte seq = 0x60;

    /**
     * 打开电力红外
     *
     * @return 成功返回 True，失败返回 False
     */
    public static boolean open() {
        if (SOConfig.useOldSO) {
            if (Build.MODEL.equalsIgnoreCase("A1-B") || Build.MODEL.equalsIgnoreCase("A1-S")) {
                return com.thinta.device.infra.Infra.open();
            } else {
                return infraMeter.open();
            }
        } else {
            int ret = Shell.IRDA_init();
            if (NewProtocol.isNewProtocol()){
                set(1200,2,8,1,0);
            }else{
                set(1200,2,8,1);
            }
            Shell.IRDA_setTimeOut(1,30000);
            return ret == 0;
        }
    }

    /**
     * 关闭电力红外
     *
     * @return 成功返回 True，失败返回 False
     */
    public static boolean close() {
        if (SOConfig.useOldSO) {
            if (Build.MODEL.equalsIgnoreCase("A1-B") || Build.MODEL.equalsIgnoreCase("A1-S")) {
                return com.thinta.device.infra.Infra.close();
            } else {
                return infraMeter.close();
            }
        } else {
            return Shell.IRDA_deInit() == 0;
        }
    }

    /**
     * 查询电力红外状态
     *
     * @return 电力红外未启用返回0，已启用返回1，查询失败返回-1
     */
    public static int status() {
        if (SOConfig.useOldSO) {
            if (Build.MODEL.equalsIgnoreCase("A1-B") || Build.MODEL.equalsIgnoreCase("A1-S")) {
                return com.thinta.device.infra.Infra.status();
            }
        }
        return 0;
    }

    /**
     * 设置电力红外通讯参数
     *
     * @param baudRate 波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity   校验位，0:无校验，1:奇校验，2:偶校验
     * @param dataBits 数据位，5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param stopBits 停止位，1:一个停止位，2:两个停止位
     * @return 成功返回 True，失败返回 False
     */
    public static boolean set(int baudRate, int parity, int dataBits,
                              int stopBits) {
        if (SOConfig.useOldSO) {
            if (Build.MODEL.equalsIgnoreCase("A1-B") || Build.MODEL.equalsIgnoreCase("A1-S")) {
                return com.thinta.device.infra.Infra.set(baudRate, parity, dataBits, stopBits);
            } else {
                return infraMeter.set(baudRate, parity, dataBits, stopBits);
            }
        } else {
            return Shell.IRDA_config(baudRate, dataBits, parity, stopBits) == 0;
        }
    }

    /**
     * 设置电力红外通讯参数
     *
     * @param baudRate 波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param parity   校验位，0:无校验，1:奇校验，2:偶校验
     * @param dataBits 数据位，5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param stopBits 停止位，1:一个停止位，2:两个停止位
     *@param blockmode 阻塞模式：0为无阻塞，1位阻塞
     * @return 成功返回 True，失败返回 False
     */
    public static boolean set(int baudRate, int parity, int dataBits,
                              int stopBits,int blockmode) {
        if (SOConfig.useOldSO) {
            if (Build.MODEL.equalsIgnoreCase("A1-B") || Build.MODEL.equalsIgnoreCase("A1-S")) {
                return com.thinta.device.infra.Infra.set(baudRate, parity, dataBits, stopBits);
            } else {
                return infraMeter.set(baudRate, parity, dataBits, stopBits);
            }
        } else {
            return Shell.IRDA_config(baudRate, dataBits, parity, stopBits,blockmode) == 0;
        }
    }

    /**
     * 通过电力红外发送数据
     *
     * @param buffer 发送的数据字节数组
     * @param offset 发送数据在字节数组中的起始位置
     * @param length 发送数据的长度
     * @return 实际发送的字节数
     */
    public static int send(byte[] buffer, int offset, int length) {
        return send(buffer, offset, length, true);
    }

    /**
     * 通过电力红外发送数据
     *
     * @param buffer
     * @param offset
     * @param length
     * @param flag   是否重新计算序列号
     * @return
     */
    public static int send(byte[] buffer, int offset, int length, boolean flag) {
        if (flag) {
            //序列号从0x60 -- 0x6F变化
            buffer[13] = seq;
            buffer[buffer.length - 2] = ByteUtils
                    .getCheckCode(buffer, 6, buffer.length - 2);
            if ((byte) 0x6F == seq) {
                seq = (byte) 0x60;
            } else {
                seq = (byte) (seq + 1);
            }
        }
        if (SOConfig.useOldSO) {
            if (Build.MODEL.equalsIgnoreCase("A1-B") || Build.MODEL.equalsIgnoreCase("A1-S")) {
                return com.thinta.device.infra.Infra.send(buffer, offset, length);
            } else {
                ZCommand zCommand = new ZCommand();
                zCommand.systemSet(ZCommand.SYSTEM_INFRA_TOTAL_TIMEOUT, "30000");
                int sendLen = infraMeter.send(buffer, 0, buffer.length);
                if (sendLen < 8) {
                    return 0;
                } else {
                    return sendLen - 8;
                }
            }
        } else {
            Shell.IRDA_clearSendCache();
            return Shell.IRDA_sendData(buffer, offset, length);
        }
    }
    /**
     * 通过电力红外发送数据
     *
     * @param buffer
     * @param offset
     * @param length
     * @param flag   是否重新计算序列号
     * @return
     */
    public static int send(byte[] buffer, int offset, int length, boolean flag,String timeout) {
        if (flag) {
            //序列号从0x60 -- 0x6F变化
            buffer[13] = seq;
            buffer[buffer.length - 2] = ByteUtils
                    .getCheckCode(buffer, 6, buffer.length - 2);
            if ((byte) 0x6F == seq) {
                seq = (byte) 0x60;
            } else {
                seq = (byte) (seq + 1);
            }
        }
        if (SOConfig.useOldSO) {
            if (Build.MODEL.equalsIgnoreCase("A1-B") || Build.MODEL.equalsIgnoreCase("A1-S")) {
                return com.thinta.device.infra.Infra.send(buffer, offset, length);
            } else {
                ZCommand zCommand = new ZCommand();
                zCommand.systemSet(ZCommand.SYSTEM_INFRA_TOTAL_TIMEOUT, timeout);
                int sendLen = infraMeter.send(buffer, 0, buffer.length);
                if (sendLen < 8) {
                    return 0;
                } else {
                    return sendLen - 8;
                }
            }
        } else {
            Shell.IRDA_clearSendCache();
            return Shell.IRDA_sendData(buffer, offset, length);
        }
    }
    /**
     * 通过电力红外接收数据，单次最大接收512字节
     *
     * @param timeout     接收超时时间，单位毫秒
     * @param partTimeout 数据中断间隔超时，单位毫秒，为0表示使用默认值
     * @return 接收到的数据内容
     */
    public static byte[] receive(int timeout, int partTimeout) {
        if (SOConfig.useOldSO) {
            if (Build.MODEL.equalsIgnoreCase("A1-B") || Build.MODEL.equalsIgnoreCase("A1-S")) {
                return com.thinta.device.infra.Infra.receive(timeout, partTimeout);
            } else {
                byte[] buffer = infraMeter.receive(BUFFER_LENGTH, timeout, partTimeout);
                return buffer;
            }
        } else {
            try {
                int len;
                byte[] temp = new byte[1024];
                Shell.IRDA_clearRevCache();
                if (NewProtocol.isNewProtocol()){
                    //新协议
                    len = Shell.IRDA_recvData(temp, 0, 1024);
                }else{
                    len = Shell.IRDA_recvData(temp, 0);
                }

                Shell.IRDA_setTimeOut(1, timeout);
                if (len < 1) {
                    return null;
                } else {
                    byte ret[] = new byte[len];
                    System.arraycopy(temp, 0, ret, 0, len);
                    return ret;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
