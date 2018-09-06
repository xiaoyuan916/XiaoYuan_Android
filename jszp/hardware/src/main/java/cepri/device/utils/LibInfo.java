package cepri.device.utils;

import android.os.Build;

import com.sgcc.pda.hardware.SOConfig;
import com.sgcc.pda.sdk.utils.FileUtils;
import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * Created by xuzl on 2016/10/31.
 */
public class LibInfo {
    static {
//       if (Build.MODEL.equals("HE5001-II")||Build.MODEL.equals("TCHN-105W")||Build.MODEL.equals("SC3402A")){
//            System.load("data/data/com.sgcc.pda/lib/libcepri_dev.so");
//        }else if (Build.MODEL.equals("TCHN-106")){
//           System.load("data/data/com.sgcc.pda/lib/libcepri_tchn.so");
//       }else if (Build.MODEL.equals("7202G3A")||Build.MODEL.equals("7202G")){
//           System.load("data/data/com.sgcc.pda/lib/libcepri_kl.so");
////           System.loadLibrary("cepri_dev");
//       }else if(Build.MODEL.equals("HT380")){
//           System.loadLibrary("cepri_tchn_tc");
//       }

        /**
         * 优化后 最终加载SO 的代码
         */
        if(FileUtils.fileIsExists("system/lib/libcepri_dev.so")){
            //优先加载掌机中SO
            LogUtil.d("TL","掌机中SO库存在");
            System.loadLibrary("cepri_dev");
        }else{
            //掌机中没有 从程序中加载
            String   deviceTypte=Build.MODEL; //获取掌机设备型号
            LogUtil.d("TL","掌机中SO库不存在"+deviceTypte);
            if (SOConfig.isTC()){
                System.loadLibrary("cepri_HT380");
            }
            System.load("/data/data/com.sgcc.pda.jszp/lib/libcepri_"+ deviceTypte+".so");

        }
    }

    /**
     * 获取so库的版本序号
     *
     * @return So库的版本序号
     */
    public native static int getVersion();

    /**
     * 获取so库的厂家编码
     *
     * @return So库的厂家编码
     */
    public native static int getCompany();
}
