package cepri.device.utils;

import android.os.Build;

import com.sgcc.pda.hardware.SOConfig;
import com.sgcc.pda.hardware.util.Constant;
import com.sgcc.pda.sdk.utils.FileUtils;
import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * Created by xuzl on 2016/10/31.
 */
public class LibInfo {
    static {

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
            System.load(Constant.ASSETS_URL+ deviceTypte+".so");

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
