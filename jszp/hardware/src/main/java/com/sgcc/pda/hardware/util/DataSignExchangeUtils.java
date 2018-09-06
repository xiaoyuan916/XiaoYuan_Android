package com.sgcc.pda.hardware.util;

import android.text.TextUtils;

/**
 * @author: wgj
 * @date: 2017/2/15
 * @time: 15:01
 */

public class DataSignExchangeUtils {
    /**
     * 将电能表645规约中数据标识转换为功能码
     *
     * @param dataSign  电表数据标识
     * @param mpedIndex 电能表测量点号
     * @return da dt
     */
    public static byte[] getData(String dataSign, String mpedIndex) {
        if (TextUtils.isEmpty(dataSign) || dataSign.length() != 8 || TextUtils.isEmpty(mpedIndex)) {
            return null;
        }

        String dadt = null;
        byte[] data;

        switch (dataSign) {
            case "05060101"://（上一次）日冻结正向有功总电能数据  161 日冻结正向有功电能示值
                dadt = getJzqDA_DT(mpedIndex, "161");
                break;
            case "05060201"://（上一次）日冻结反向有功总电能数据  163 日冻结反向有功电能示值
                dadt = getJzqDA_DT(mpedIndex, "163");
                break;

        }
        data = DataConvert.toBytes(dadt);
        return data;
    }


    /**
     * 得到DA DT
     *
     * @param mpedIndex 测量点号
     * @return
     */
    public static String getJzqDA(String mpedIndex) {
        int mIndex = Integer.parseInt(mpedIndex);

        int remainder = mIndex % 8;//测量点号模8取余
        int result = mIndex / 8;//测量点除以8
        int da01 = remainder == 0 ? result : result + 1;
        int da02 = 0x01 << (remainder == 0 ? 7 : (remainder - 1));
        return DataConvert.toHexString(da02, 1) + DataConvert.toHexString(da01, 1);
    }

    /**
     * 得到DA DT
     *
     * @param mpedIndex 测量点号
     * @return
     */
    public static String getJzqDA_DT(String mpedIndex, String fn) {
        int mIndex = Integer.parseInt(mpedIndex);
        int afn = Integer.parseInt(fn);
        int remainder = mIndex % 8;//测量点号模8取余
        int result = mIndex / 8;//测量点除以8
        int da01 = remainder == 0 ? result : result + 1;
        int da02 = 0x01 << (remainder == 0 ? 7 : (remainder - 1));

        int afnRem = afn % 8;
        int afnRes = afn / 8;

        int dt01 = afnRem == 0 ? (afnRes - 1) : afnRes;
        int dt02 = 0x01 << (afnRem == 0 ? 7 : (afnRem - 1));

        String da = DataConvert.toHexString(da02, 1) + DataConvert.toHexString(da01, 1);
        String dt = DataConvert.toHexString(dt02, 1) + DataConvert.toHexString(dt01, 1);

        return da + dt;


    }
}
