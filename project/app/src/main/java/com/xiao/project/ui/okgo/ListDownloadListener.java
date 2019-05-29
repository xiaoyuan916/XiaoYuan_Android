package com.xiao.project.ui.okgo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.lzy.okgo.model.Progress;
import com.lzy.okserver.download.DownloadListener;

import java.io.File;

/**
 * author:xuxiaoyuan
 * date:2019/1/23
 */
public class ListDownloadListener extends DownloadListener {

    private Context mContext;

    public ListDownloadListener(Object tag, Context mContext) {
        super(tag);
        this.mContext = mContext;
    }

    @Override
    public void onStart(Progress progress) {
    }

    @Override
    public void onProgress(Progress progress) {
        Log.d(toString(), progress.fraction + "");
    }

    @Override
    public void onError(Progress progress) {
        Throwable throwable = progress.exception;
        if (throwable != null) throwable.printStackTrace();
    }

    @Override
    public void onFinish(File file, Progress progress) {
        Log.d(toString(), "onFinish: " + file.getAbsolutePath());
    }

    @Override
    public void onRemove(Progress progress) {

    }
}
