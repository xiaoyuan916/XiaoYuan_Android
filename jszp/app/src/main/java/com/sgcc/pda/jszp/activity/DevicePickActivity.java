package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;

import butterknife.BindView;
import butterknife.OnClick;

public class DevicePickActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.et_start)
    EditText etStart;
    @BindView(R.id.et_end)
    EditText etEnd;
    @BindView(R.id.cb_chaoqi)
    CheckBox cbChaoqi;
    @BindView(R.id.cb_hege)
    CheckBox cbHege;
    @BindView(R.id.cb_daifenjian)
    CheckBox cbDaifenjian;
    @BindView(R.id.cb_daijianding)
    CheckBox cbDaijianding;
    @BindView(R.id.cb_daifanchang)
    CheckBox cbDaifanchang;
    @BindView(R.id.tv_leibie)
    TextView tvLeibie;
    @BindView(R.id.tv_leixing)
    TextView tvLeixing;
    @BindView(R.id.tv_jiexian)
    TextView tvJiexian;
    @BindView(R.id.tv_dianya)
    TextView tvDianya;
    @BindView(R.id.tv_dianliu)
    TextView tvDianliu;
    @BindView(R.id.tv_zhunque)
    TextView tvZhunque;
    @BindView(R.id.tv_shiduan)
    TextView tvShiduan;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_pick;
    }

    @Override
    public void initView() {
        tvTitle.setText("设备拣选");

        cbChaoqi.setOnCheckedChangeListener(this);
        cbDaifanchang.setOnCheckedChangeListener(this);
        cbDaifenjian.setOnCheckedChangeListener(this);
        cbDaijianding.setOnCheckedChangeListener(this);
        cbHege.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {

    }


    private TextView tv_select;

    @OnClick({R.id.iv_saoma, R.id.iv_end_saoma, R.id.rl_leibie, R.id.rl_leixing, R.id.rl_jiexian, R.id.rl_dianya, R.id.rl_dianliu, R.id.rl_zhunque, R.id.rl_shiduan, R.id.rl_daohuo})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, PickOptionActivity.class);
        switch (view.getId()) {
            case R.id.iv_saoma: {
                Intent intent1 = new Intent(this, ScanActivity.class);
                intent1.putExtra("type", MyComment.SCAN_DEVICE_PICK);
                intent1.putExtra("sub_type", 0);
                startActivityForResult(intent1, 101);
            }
            return;
            case R.id.iv_end_saoma: {
                Intent intent1 = new Intent(this, ScanActivity.class);
                intent1.putExtra("type", MyComment.SCAN_DEVICE_PICK);
                intent1.putExtra("sub_type", 1);
                startActivityForResult(intent1, 102);
            }
            return;
            case R.id.rl_daohuo:
                return;
            case R.id.rl_leibie:
                tv_select = tvLeibie;
                break;
            case R.id.rl_leixing:
                tv_select = tvLeixing;
                break;
            case R.id.rl_jiexian:
                tv_select = tvJiexian;
                break;
            case R.id.rl_dianya:
                tv_select = tvDianya;
                break;
            case R.id.rl_dianliu:
                tv_select = tvDianliu;
                break;
            case R.id.rl_zhunque:
                tv_select = tvZhunque;
                break;
            case R.id.rl_shiduan:
                tv_select = tvShiduan;
                break;
        }
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 100:
                String option = data.getStringExtra("option");
                if (tv_select != null) tv_select.setText(option);
                break;
            case 101: {
                String num = data.getStringExtra("number");
                etStart.setText(num);
                etStart.setSelection(etStart.getText().toString().length());
            }
            break;
            case 102: {
                String num = data.getStringExtra("number");
                etEnd.setText(num);
                etEnd.setSelection(etEnd.getText().toString().length());
            }
            break;
        }

    }

    private CompoundButton selected;

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (selected == null)
                selected = compoundButton;
            else {
                selected.setChecked(false);
                selected = compoundButton;
            }
        }
    }

}
