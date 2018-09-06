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
 * author:赵锦
 * date:2018/8/20 0020 10:51
 *
 * 平库出库-》查询条件
 */
public class DeviceOutQueryActivity extends BaseActivity{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_taskno)
    EditText etTaskno;

    private String taskno;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_out_query;
    }

    @Override
    public void initView() {
        tvTitle.setText("平库出库");
        taskno = getIntent().getStringExtra("taskno");
        etTaskno.setText(taskno);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_confirm:
                //查询
                Intent intent = new Intent();
                intent.putExtra("taskno",etTaskno.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
