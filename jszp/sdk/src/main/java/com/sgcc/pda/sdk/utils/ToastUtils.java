package com.sgcc.pda.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sgcc.pda.sdk.R;

/**
 * toast提示工具类
 */
public class ToastUtils {
    public static void showToast(Context context, String msg) {
        if (!ActivityCheckUtil.isActive(context) || TextUtils.isEmpty(msg)) return;

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId) {
        if (!ActivityCheckUtil.isActive(context))
            return;
        if (TextUtils.isEmpty(context.getString(resId)))
            return;

        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * “带图标的”提示
     *
     * @param context
     * @param msg       提示信息
     * @param resIconId icon资源的id
     * @param duration  时间间隙
     * @param gravity   位置
     */
    public static void showToast(Context context, String msg, int resIconId, int duration, int gravity) {
        if (!ActivityCheckUtil.isActive(context) || TextUtils.isEmpty(msg)) return;

        if (context instanceof Activity) {
            Activity ac = ((Activity) context);

            LayoutInflater inflater = ac.getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_no_title, (ViewGroup) ac.findViewById(R.id.ll_toast));
            ImageView image = (ImageView) layout.findViewById(R.id.iv_icon);
            image.setImageResource(resIconId);
            TextView text = (TextView) layout.findViewById(R.id.tv_toast);
            text.setText(msg);
            Toast toast = new Toast(context);
            toast.setGravity(gravity, 0, 0);
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * “带图标的”提示
     *
     * @param context
     * @param title
     * @param msg       提示信息
     * @param resIconId icon资源的id
     * @param duration  时间间隙
     * @param gravity   位置
     */
    public static void showToast(Context context, String title, String msg, int resIconId, int duration, int gravity) {
        if (!ActivityCheckUtil.isActive(context) || TextUtils.isEmpty(msg)) return;

        if (context instanceof Activity) {
            Activity ac = ((Activity) context);

            LayoutInflater inflater = ac.getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast, (ViewGroup) ac.findViewById(R.id.ll_toast));
            ImageView image = (ImageView) layout.findViewById(R.id.iv_icon);
            image.setImageResource(resIconId);
            TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
            tv_title.setText(title);
            TextView text = (TextView) layout.findViewById(R.id.tv_toast);
            text.setText(msg);
            Toast toast = new Toast(context);
            toast.setGravity(gravity, 0, 0);
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
