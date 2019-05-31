package com.xiao.project.ui.filedownloader;

import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;

public class SingleFileDownloadListener extends FileDownloadListener {
    private static String TAG = "SingleFileDownloadListener";

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.d(TAG, "pending: soFarBytes-> "+soFarBytes+" totalBytes-> "+totalBytes);
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.d(TAG, "progress: soFarBytes-> "+soFarBytes+" totalBytes-> "+totalBytes);
    }

    @Override
    protected void completed(BaseDownloadTask task) {
        Log.d(TAG, "completed: ");
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.d(TAG, "paused: ");
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        Log.d(TAG, "error: ");
    }

    @Override
    protected void warn(BaseDownloadTask task) {
        Log.d(TAG, "warn: ");
    }
}
