package com.sgcc.pda.sdk.utils.http;

import android.os.Handler;
import android.os.Looper;

public class UIHandler extends Handler {
    private static UIHandler mHandler;

    private UIHandler(Looper looper) {
        super(looper);
    }

    public static UIHandler get() {
        if (mHandler == null) {
            mHandler = new UIHandler(Looper.getMainLooper());
        }
        return mHandler;
    }
}