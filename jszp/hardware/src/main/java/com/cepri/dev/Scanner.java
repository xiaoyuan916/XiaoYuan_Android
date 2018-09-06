package com.cepri.dev;

import android.os.Build;

import com.sgcc.pda.sdk.utils.FileUtils;
import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * Created by xuzl on 2016/10/31.
 */
public class Scanner {
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
            String   deviceTypte=Build.MODEL; //获取掌机设备型号
            LogUtil.d("TL","掌机中SO库不存在"+deviceTypte);
            System.load("data/data/com.sgcc.pda.jszp/lib/libcepri_"+ deviceTypte+".so");

        }
    }

    /**
     * 扫描头初始化，包括打开电源和通信端口
     *
     * @return 0:成功  其它：错误号
     */
    public native static int init();

    /**
     * 扫描头通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public native static int deInit();

    /**
     * 读取条码或者二维码
     *
     * @param timeout 超时时间
     * @param code    读取到的数据
     * @param offset  偏移量
     * @return 读取到的条码或者二维码的字节数据长度
     */
    public native static int decode(int timeout, byte[] code, int offset);

}
