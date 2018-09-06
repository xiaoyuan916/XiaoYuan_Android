package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 装车确认  查询条件页面
 */
public class DeliveryQueryActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_kufang)
    EditText et_kufang;
    @BindView(R.id.et_driver_name)
    EditText et_driver_name;
    @BindView(R.id.et_car_no)
    EditText et_car_no;
    /**
     * 筛选条件字段
     */
    private String carNo;
    private String driverName;
    private String kufang;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_delivery_query;
    }

    @Override
    public void initView() {
        tvTitle.setText("查询");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        carNo = intent.getStringExtra("carNo");
        driverName = intent.getStringExtra("driverName");
        kufang = intent.getStringExtra("kufang");
        et_car_no.setText(carNo);
        et_driver_name.setText(driverName);
        et_kufang.setText(kufang);
    }

    @Override
    public void initListener() {

    }
    @OnClick({R.id.btn_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_confirm:
                //查询
                Intent intent = new Intent();
                intent.putExtra("kufang",et_kufang.getText().toString());
                intent.putExtra("carNo",et_car_no.getText().toString());
                intent.putExtra("driverName",et_driver_name.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
