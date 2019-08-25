package com.xiao.project.ui.documentpreview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tencent.smtt.sdk.TbsReaderView;
import com.xiao.project.R;

import java.io.File;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentPreviewActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback{

    @BindView(R.id.tbsView)
    RelativeLayout mRelativeLayout;
    ProgressBar web_bar;
    TbsReaderView mTbsReaderView;

    // 文件的存储路径
    private String BASE_PATH = Environment.getExternalStorageDirectory().toString() + "/Pictures/temp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_preview);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        // 在代码中添加布局，这个我也不知道什么原因，网上很多人都说在布局文件中加载会出错
        mTbsReaderView = new TbsReaderView(this, this);
        mRelativeLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(-1, -1));
        String filePath = Environment.getExternalStorageDirectory().toString() + "/Download/";
        displayFile(filePath+"姗姗表格.xlsx","姗姗表格.xlsx");
    }


    private void displayFile(String filePath, String fileName) {

        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = BASE_PATH;
        File bsReaderTempFile = new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.d("print", "准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                Log.d("print", "创建/TbsReaderTemp失败！！！！！");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", BASE_PATH);
        boolean result = mTbsReaderView.preOpen(getFileType(fileName), false);
        Log.d("print", "查看文档---" + result);
        if (result) {
            mTbsReaderView.openFile(bundle);
        } else {

        }
    }
    /**
     * 后缀名的判断
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.d("call", "==================+++++====-=-=++" + integer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
    }
}
