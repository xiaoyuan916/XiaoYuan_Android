package com.xiao.project.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GlideUtils {
    private static final String TAG = "GlideUtils";

    /**
     * 正常加载图片
     *
     * @param context
     * @param resource
     * @param imageView
     */
    public static void loadUtils(Context context, int resource, ImageView imageView) {
        Log.d(TAG, "loadUtils: resource" + resource);
        Glide.with(context).load(resource).into(imageView);
    }

    /**
     * 加载帧动画的url
     *
     * @param context
     * @param resource
     * @param imageView
     */
    public static void loadAnimationDrawableUtils(Context context, int resource, ImageView imageView) {
        AnimationDrawable animationDrawable = initAnimationDrawable();
        imageView.setBackground(animationDrawable);
        // 设置为循环播放
        animationDrawable.setOneShot(false);
        animationDrawable.start();
    }


    /**
     * 返回一个帧动画
     *
     * @return AnimationDrawable
     */
    private static AnimationDrawable initAnimationDrawable() {
        AnimationDrawable animationDrawable = null;
//        int id = context.getResources().getIdentifier("sample_", "mipmap", context.getPackageName());
//        Drawable drawable = context.getResources().getDrawable(id);
        String themeFilePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/multimedia_theme_dir";
        File desDir = new File(themeFilePath);
        File[] files = desDir.listFiles();
        if (files == null) {
            Log.e("error", "空目录");
            return animationDrawable;
        }
        animationDrawable = new AnimationDrawable();
        for (File file : files) {
            if (file.getName().contains(".png")) {
                animationDrawable.addFrame(bitmap2Drawable(file.getAbsolutePath()), 500);
            }
        }
        return animationDrawable;
    }

    /**
     * bitmap转换为Drawable
     *
     * @param filePath
     * @return
     */
    private static Drawable bitmap2Drawable(String filePath) {
        return new BitmapDrawable(filePath);
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
