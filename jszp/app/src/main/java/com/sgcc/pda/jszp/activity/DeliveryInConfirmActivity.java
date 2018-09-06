package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.devicetypeItemAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DeviceItem;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesDetsItem;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 装车确认列表--》装车确认详情--》录入确认
 */
public class DeliveryInConfirmActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_address)
    TextView tvOrderAddress;
    @BindView(R.id.et_boxcount)
    EditText etBoxcount;
//    @BindView(R.id.rv_types)
//    RecyclerView rvTypes;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText("录入确认");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("确认");

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        LogisticsDistAutoesDetsItem detsItem = (LogisticsDistAutoesDetsItem)
                intent.getSerializableExtra("LogisticsDistAutoesDetsItem");
        initUI(detsItem);
    }

    /**
     * 初始化UI
     * @param detsItem
     */
    private void initUI(LogisticsDistAutoesDetsItem detsItem) {
        tvOrderNum.setText(detsItem.getDistAutoDetId());
        tvOrderAddress.setText(detsItem.getDpName());
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        if (TextUtils.isEmpty(etBoxcount.getText())){
            ToastUtils.showToast(DeliveryInConfirmActivity.this,"请输入数量");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("realcount",etBoxcount.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
