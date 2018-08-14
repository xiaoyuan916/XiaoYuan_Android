package com.sgcc.pda.sdk.dao;

import android.content.Context;

/**
 * Created by xuzl on 2016/12/7.
 * Dao工厂
 */
public class SdkDaoFactory {
    private SdkDaoFactory() {
    }

    public static ExceptionDao getExceptionDao(Context ctx) {
        return ExceptionDao.getInstance(ctx);
    }
}
