package com.xiao.project.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.DecimalFormat;
import com.blankj.utilcode.util.FileUtils;

/**
 * @ClassName ClearCacheUtils
 * @Description TODO
 * @Author Administrator
 * @Date 2019/8/25 11:38
 * @Version 1.0
 */
public class ClearCacheUtils {
    /**
     * 清除本应用内部缓存数据(/data/data/com.xxx.xxx/cache)
     * @param context 上下文
     * @return void
     */
    public static void cleanInternalCache(Context context) {
        FileUtils.deleteAllInDir(context.getCacheDir());
    }

    /**
     * 获取App应用缓存的大小
     * @param context 上下文
     * @return String
     */
    public static String getAppClearSize(Context context) {
        long clearSize = 0;
        String fileSizeStr = "";
        DecimalFormat df = new DecimalFormat("0.00");
        //获得应用内部缓存大小
        clearSize = FileUtils.getFileLength(context.getCacheDir());
        //获得应用SharedPreference缓存数据大小
        clearSize += FileUtils.getFileLength(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
        //获得应用data/data/com.xxx.xxx/files下的内容文件大小
        clearSize += FileUtils.getFileLength(context.getFilesDir());
        //获取应用外部缓存大小
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            clearSize += FileUtils.getFileLength(context.getExternalCacheDir());
        }
        if(clearSize > 5000)  {
            //转换缓存大小Byte为MB
            fileSizeStr = df.format((double) clearSize / 1048576) + "MB";
        }
        return fileSizeStr;
    }
}
