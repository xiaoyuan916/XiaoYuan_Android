package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.OptionAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.CodeTypeEntity;
import com.sgcc.pda.jszp.bean.CodeTypeRequestEntity;
import com.sgcc.pda.jszp.bean.CodeTypeResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PickOptionActivity extends BaseActivity {

    private static final int URL_CODE_TYPE=1301;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rv_options)
    RecyclerView rvOptions;

    BaseItemAdapter optionAdapter;
    //List<OptionItem> optionBeans;
    private int selected = -1;

    private CodeTypeRequestEntity mCodeTypeRequestEntity;
    private CodeTypeResultEntity mCodeTypeResultEntity;
    private List<CodeTypeEntity> mList=new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case URL_CODE_TYPE:
                    mCodeTypeResultEntity = (CodeTypeResultEntity) msg.obj;
                    if (0!=mCodeTypeResultEntity.getCodes().size()){
                        mList.clear();
                        mList.addAll(mCodeTypeResultEntity.getCodes());
                        initViewData();
                    }else {
                        ToastUtils.showToast(PickOptionActivity.this,"暂无数据");
                    }

                    break;
            }
        }
    };
    //赋值
    private void initViewData() {
        optionAdapter = new BaseItemAdapter();
        optionAdapter.register(CodeTypeEntity.class, new OptionAdapter<CodeTypeEntity>(this));
 //       optionBeans = new ArrayList<>();
//        optionBeans.add(new OptionItem("电能表"));
//        optionBeans.add(new OptionItem("互感器"));
//        optionBeans.add(new OptionItem("采集终端"));
        rvOptions.setAdapter(optionAdapter);
        optionAdapter.setDataItems(mList);
        rvOptions.addItemDecoration(new SpaceItemDecoration(1));
        optionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                int index = baseViewHolder.getItemPosition();
                if (selected != -1) {
                    mList.get(selected).setSelected(false);
                }
                selected = index;
                mList.get(selected).setSelected(true);
                optionAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_pick_option;
    }

    private String tvtype = "";

    @Override
    public void initView() {
        tvTitle.setText("类别");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        rvOptions.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        tvtype = getIntent().getStringExtra("codeType");
        mCodeTypeRequestEntity=new CodeTypeRequestEntity();
                mCodeTypeRequestEntity.setCodeType(tvtype);
                obtainNetSubmit();
//        switch (tvtype) {
//            case "tvLeibie":
//                mCodeTypeRequestEntity=new CodeTypeRequestEntity();
//                mCodeTypeRequestEntity.setCodeType("meterSort");
//                obtainNetSubmit();
//                break;
//            case "tvLeixing":
//                mCodeTypeRequestEntity=new CodeTypeRequestEntity();
//                mCodeTypeRequestEntity.setCodeType("meterTypeCode");
//                obtainNetSubmit();
//                break;
//
//            case "tvJiexian"://接线方式
//                mCodeTypeRequestEntity=new CodeTypeRequestEntity();
//                mCodeTypeRequestEntity.setCodeType("wiringMode");
//                obtainNetSubmit();
//                break;
//
//            case "tvDianya"://电压
//                mCodeTypeRequestEntity=new CodeTypeRequestEntity();
//                mCodeTypeRequestEntity.setCodeType("meterVolt");
//                obtainNetSubmit();
//                break;
//
//            case "tvDianliu"://电流
//                mCodeTypeRequestEntity=new CodeTypeRequestEntity();
//                mCodeTypeRequestEntity.setCodeType("meterRcSort");
//                obtainNetSubmit();
//                break;
//
//            case "tvGuiyue"://通讯规约
//                mCodeTypeRequestEntity=new CodeTypeRequestEntity();
//                mCodeTypeRequestEntity.setCodeType("MetcommVer");
//                obtainNetSubmit();
//                break;
//            case "tvFeilv"://费率
////                mCodeTypeRequestEntity=new CodeTypeRequestEntity();
////                mCodeTypeRequestEntity.setCodeType("wiringMode ");
////                obtainNetSubmit();
//                break;
//            case "tvZhunque"://有功准确等级
//                mCodeTypeRequestEntity=new CodeTypeRequestEntity();
//                mCodeTypeRequestEntity.setCodeType("meterAccuracy");
//                obtainNetSubmit();
//                break;
//
//        }
    }

    /**
     * 请求数据
     */
    private void obtainNetSubmit() {
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_CODE_TYPE, this,
                mCodeTypeRequestEntity, mHandler,
                URL_CODE_TYPE, CodeTypeResultEntity.class);
    }


    @Override
    public void initListener() {

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent data = new Intent();
        if (selected == -1) setResult(RESULT_CANCELED);
        else {
            data.putExtra("option", mList.get(selected));
//            data.putExtra("codeNo",mList.get(selected).getCode());
            data.putExtra("position", selected);
            setResult(RESULT_OK, data);
        }
        finish();
    }
}
