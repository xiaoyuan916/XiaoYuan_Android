package com.cepri.dev;

import android.os.Build;

import com.sgcc.pda.sdk.utils.FileUtils;
import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * Created by xuzl on 2016/10/31.
 */
public class SecurityUnit {
    static {
//        if (Build.MODEL.equals("A1-X") || Build.MODEL.equals("R100A")
//                || Build.MODEL.equals("R700A") || Build.MODEL.equals("M1-M50")) {//瑞士发
//            System.load("data/data/com.sgcc.pda/lib/libcepri_zz.so");
//        } else if (Build.MODEL.equals("HT380D") || Build.MODEL.equals("JB-HT380D")) {//捷宝
////            System.loadLibrary("cepri_jb");
//            System.load("data/data/com.sgcc.pda/lib/libcepri_jb.so");
//        } else if (Build.MODEL.equals("esky8735_tb_l1") || Build.MODEL.equals("MST-701")) {//科曼大屏
//            System.load("data/data/com.sgcc.pda/lib/libcepri.so");
////            System.loadLibrary("cepri");
//        } else if (Build.MODEL.contains("A1-35") || Build.MODEL.contains("P2-55") ||
//                Build.MODEL.contains("P1-70")) {//振中
//            System.load("data/data/com.sgcc.pda/lib/libcepri_zz.so");
//        } else if (Build.MODEL.equals("MST-4")) {//科曼小屏
//            System.load("data/data/com.sgcc.pda/lib/libcepri_skm.so");
//        } else if (Build.MODEL.equalsIgnoreCase("CL7202G")) {
//            System.load("data/data/com.sgcc.pda/lib/libcepri_zz.so");
//        } else if (Build.MODEL.equalsIgnoreCase("MST-VI")) {
//            System.load("data/data/com.sgcc.pda/lib/libcepri_zz.so");
//        } else if (Build.MODEL.equalsIgnoreCase("X520")) {
//            System.load("data/data/com.sgcc.pda/lib/libcepri_zz.so");
//        }

        /**
         * 优化后 最终加载SO 的代码
         */
        if(FileUtils.fileIsExists("system/lib/libcepri.so")){
            //优先加载掌机中SO
            LogUtil.d("TL","掌机中SO库存在");
            System.loadLibrary("cepri");
        }else{
            //掌机中没有 从程序中加载
            String deviceTypte = Build.MODEL; //获取掌机设备型号
            LogUtil.d("TL","掌机中SO库不存在"+deviceTypte);
            System.load("data/data/com.sgcc.pda.jszp/lib/libcepri_"+ deviceTypte+".so");

        }
    }

    /**
     * 安全单元初始化，包括打开电源和通信
     *
     * @return 0:成功 其它：错误号
     */
    public native static int init();

    /**
     * 安全单元通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public native static int deInit();

    /**
     * 清空安全单元的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public native static int clearSendCache();

    /**
     * 清空安全单元的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public native static int clearRevCache();

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位 0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位 0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public native static int config(int baudrate, int databits, int parity, int stopbits);

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public native static int sendData(byte[] data, int offset, int length);

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public native static int recvData(byte[] data, int offset);

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public native static int setTimeOut(int direction, int timeout);

}
