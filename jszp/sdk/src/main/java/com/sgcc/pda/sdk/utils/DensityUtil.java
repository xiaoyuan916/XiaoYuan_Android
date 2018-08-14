package com.sgcc.pda.sdk.utils;

import android.content.Context;

public class DensityUtil 
{
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		return context.getResources().getDisplayMetrics().widthPixels;
//		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//
//		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
//
//		int W = mDisplayMetrics.widthPixels;
//
//		int H = mDisplayMetrics.heightPixels;
//
//		LogUtil.d(Tags.SCREEN, "屏幕宽："+W+" px，高："+H+" px, 1px="+(mDisplayMetrics.densityDpi/160f)+" dp, density="+mDisplayMetrics.density);
	}
	/**
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		return context.getResources().getDisplayMetrics().heightPixels;
//		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//
//		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
//
//		int W = mDisplayMetrics.widthPixels;
//
//		int H = mDisplayMetrics.heightPixels;
//
//		LogUtil.d(Tags.SCREEN, "屏幕宽："+W+" px，高："+H+" px, 1px="+(mDisplayMetrics.densityDpi/160f)+" dp, density="+mDisplayMetrics.density);
	}
}
