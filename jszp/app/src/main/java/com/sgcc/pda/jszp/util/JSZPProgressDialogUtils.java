package com.sgcc.pda.jszp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;


public class JSZPProgressDialogUtils {
    private ProgressDialog dialog;
    //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static JSZPProgressDialogUtils instance;


    //方法同步
    public static synchronized JSZPProgressDialogUtils getInstance(){
        if(instance==null){
            instance=new JSZPProgressDialogUtils();
        }
        return instance;
    }
    public void initDialog(Activity activity) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }
    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
