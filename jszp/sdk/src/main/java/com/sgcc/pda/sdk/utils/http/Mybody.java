package com.sgcc.pda.sdk.utils.http;

import com.sgcc.pda.sdk.utils.LogUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by xuzl on 2016/10/26.
 * 自定义文件上传RequestBody
 */
public class Mybody extends RequestBody {
    private BaseRequestParams params = null;
    private long length = 0;

    public Mybody(BaseRequestParams params) {
        this.params = params;

        for (ConcurrentHashMap.Entry<String, List<BaseRequestParams.FileWrapper>> entry : params.fileArrayParams.entrySet()) {
            List<BaseRequestParams.FileWrapper> fileWrapper = entry.getValue();
            for (BaseRequestParams.FileWrapper fw : fileWrapper) {
                length += fw.file.length();
                LogUtil.d("Mybody","递归计算总长度："+length);
            }
        }

        for (ConcurrentHashMap.Entry<String, BaseRequestParams.FileWrapper> entry : params.fileParams.entrySet()) {
            BaseRequestParams.FileWrapper fileWrapper = entry.getValue();
            length += fileWrapper.file.length();
            LogUtil.d("Mybody","递归计算总长度："+length);
        }
    }

    @Override
    public MediaType contentType() {
        return null;// MediaType.parse("image/jpeg");
    }
    @Override
    public long contentLength() {
        LogUtil.d("Mybody","上传文件总长度："+length);
        return length;
    }
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            for (ConcurrentHashMap.Entry<String, List<BaseRequestParams.FileWrapper>> entry : params.fileArrayParams.entrySet()) {
                List<BaseRequestParams.FileWrapper> fileWrapper = entry.getValue();
                for (BaseRequestParams.FileWrapper fw : fileWrapper) {
                    LogUtil.d("Mybody","上传文件："+fw.file.getAbsolutePath());
                    //方式1(测试可以)
                    source = Okio.source(fw.file);
                    sink.writeAll(source);
                    sink.flush();
                }
            }

            for (ConcurrentHashMap.Entry<String, BaseRequestParams.FileWrapper> entry : params.fileParams.entrySet()) {
                BaseRequestParams.FileWrapper fileWrapper = entry.getValue();
                LogUtil.d("Mybody","上传文件："+fileWrapper.file.getAbsolutePath());
                //方式1(测试可以)
                source = Okio.source(fileWrapper.file);
                sink.writeAll(source);
                sink.flush();

                //方式2(测试也可以,可以实现进度条)
//                source = Okio.source(fileWrapper.file);
//                Buffer buf = new Buffer();
//                long remaining = contentLength();
//                long current = 0;
//                for (long readCount; (readCount = source.read(buf, 1024)) != -1; ) {
//                    sink.write(buf, readCount);
//                    current += readCount;
//                    //progressCallBack(remaining, current, callBack);
//                }
//                sink.flush();
            }
        }
        finally {
            Util.closeQuietly(source);
        }

    }
}
