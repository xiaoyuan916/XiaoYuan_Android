package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.CodeTypeEntity;
import com.sgcc.pda.jszp.bean.DevicePickFinishRequestEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DevicePickActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.bt_sure)
//    Button btSure;
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
    //    @BindView(R.id.tv_shiduan)
//    TextView tvShiduan;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_saoma)
    ImageView ivSaoma;
    @BindView(R.id.iv_end_search)
    ImageView ivEndSearch;
    @BindView(R.id.iv_end_saoma)
    ImageView ivEndSaoma;
    @BindView(R.id.rl_leibie)
    RelativeLayout rlLeibie;
    @BindView(R.id.rl_leixing)
    RelativeLayout rlLeixing;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.rl_jiexian)
    RelativeLayout rlJiexian;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.rl_dianya)
    RelativeLayout rlDianya;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.rl_dianliu)
    RelativeLayout rlDianliu;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv_guiyue)
    TextView tvGuiyue;
    @BindView(R.id.rl_guiyue)
    RelativeLayout rlGuiyue;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv_feilv)
    TextView tvFeilv;
    @BindView(R.id.rl_feilv)
    RelativeLayout rlFeilv;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.rl_zhunque)
    RelativeLayout rlZhunque;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_device_sort)
    TextView tvDeviceSort;
    @BindView(R.id.rl_device_sort)
    RelativeLayout rlDeviceSort;

    private int position = 0;//判定设备类别

    CodeTypeEntity codeTypeEntity1;//设备类别
    CodeTypeEntity codeTypeEntity2;//类别
    CodeTypeEntity codeTypeEntity3;//类型
    CodeTypeEntity codeTypeEntity4;//接线方式
    CodeTypeEntity codeTypeEntity5;//电压
    CodeTypeEntity codeTypeEntity6;//电流
    CodeTypeEntity codeTypeEntity7;//通讯规约
    CodeTypeEntity codeTypeEntity8;//费率
    CodeTypeEntity codeTypeEntity9;//有功准确等级

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_pick;
    }

    @Override
    public void initView() {
        tvTitle.setText("设备拣选");
        tvRight.setText("完成");
        tvRight.setVisibility(View.VISIBLE);
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

    private TextView tv_select;
    private String codeType = "";

    @OnClick({R.id.iv_saoma, R.id.iv_end_saoma, R.id.rl_leibie, R.id.rl_leixing, R.id.rl_jiexian, R.id.rl_dianya, R.id.rl_dianliu, R.id.rl_zhunque,R.id.rl_guiyue,R.id.rl_feilv, R.id.rl_device_sort})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, PickOptionActivity.class);
        switch (view.getId()) {
            case R.id.iv_saoma: {
                Intent intent1 = new Intent(this, ScanActivity.class);
                intent1.putExtra("type", MyComment.SCAN_DEVICE_PICK);
                intent1.putExtra("sub_type", 0);
                startActivityForResult(intent1, 101);
            }
            break;
            case R.id.iv_end_saoma: {
                Intent intent1 = new Intent(this, ScanActivity.class);
                intent1.putExtra("type", MyComment.SCAN_DEVICE_PICK);
                intent1.putExtra("sub_type", 1);
                startActivityForResult(intent1, 102);
            }
            break;
            case R.id.rl_device_sort://设备大类别
                codeType = "equipCateg";
                tv_select = tvDeviceSort;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 100);
                break;
            case R.id.rl_leibie://类别
                if (position == 0) {
                    codeType = "meterSort";//电能表类别
                } else if (position == 1 || position==2 || position==3) {
                    codeType = "itSort";//互感器类别
                } else if (position == 4) {
                    codeType = "collTmnlType";//采集终端类型
                }
                tv_select = tvLeibie;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 104);
                break;
            case R.id.rl_leixing://类型
                if (position == 0) {
                    codeType = "meterTypeCode";//电能表类型
                } else if (position == 1 || position==2 || position==3) {
                    codeType = "itTypeCode";//互感器类型
                } else if (position == 4) {
                    codeType = "lcModeCode";//采集终端型号
                }
                tv_select = tvLeixing;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 105);
                break;
            case R.id.rl_jiexian:
                if (position == 0) {
                    codeType = "wiringMode";//接线方式
                } else if (position == 1 || position==2 || position==3) {
                    codeType = "voltRatioCode";//电压变比
                } else if (position == 4) {
                    codeType = "tmnlSpec";//设备规格
                }
                tv_select = tvJiexian;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 106);
                break;
            case R.id.rl_dianya:
                if (position == 0) {
                    codeType = "meterVolt";//电压
                } else if (position == 1 || position==2 || position==3) {
                    codeType = "currentItRatio";//电流变比
                } else if (position == 4) {
                    codeType = "ctCommType";//采集方式
                }
                tv_select = tvDianya;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 107);
                break;
            case R.id.rl_dianliu:
                if (position == 0) {
                    codeType = "meterRcSort";//电流
                } else if (position == 1 || position==2 || position==3) {
                    codeType = "itPreLevelCode";//TA准确等级
                } else if (position == 4) {
                    codeType = "commProtocolVer";//采集终端通讯规约
                }
                tv_select = tvDianliu;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 108);
                break;
            case R.id.rl_guiyue:
                if (position == 0) {
                    codeType = "MetcommVer";//通讯规约
                } else if (position == 1 || position==2 || position==3) {
                    codeType = "";
                } else if (position == 4) {
                    codeType = "commMode";//通讯方式
                }
                tv_select = tvGuiyue;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 109);
                break;
            case R.id.rl_feilv:
                if (position == 0) {
                    codeType = "equipFeeRate";//费率
                } else if (position == 1 || position==2 || position==3) {
                    codeType = "";
                } else if (position == 4) {
                    codeType = "CarrierFreq";//载波中心频点
                }
                tv_select = tvFeilv;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 1010);
                break;
            case R.id.rl_zhunque:
                if (position == 0) {
                    codeType = "meterAccuracy";//有功准确等级
                } else if (position == 1 || position==2 || position==3) {
                    codeType = "";
                } else if (position == 4) {
                    codeType = "";
                }
                tv_select = tvZhunque;
                intent.putExtra("codeType", codeType);
                startActivityForResult(intent, 1011);
                break;
        }

    }

    String codeNo="";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 100:
                codeTypeEntity1 = (CodeTypeEntity) data.getSerializableExtra("option");
                position = data.getIntExtra("position", -1);
                if (tv_select != null) tv_select.setText(codeTypeEntity1.getCodeName());
                setView();
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
            case 104:
                codeTypeEntity2 = (CodeTypeEntity) data.getSerializableExtra("option");
//                position = data.getIntExtra("position", -1);
                if (tv_select != null) tv_select.setText(codeTypeEntity1.getCodeName());
                break;
            case 105:
                break;
            case 106:
                break;
            case 107:
                break;
            case 108:
                break;
            case 109:
                break;
            case 1010:
                break;
            case 1011:
                break;
        }
    }

    /**
     * 根据设备类别动态设置UI
     */
    private void setView() {
        if (position == 0) {
            rlFeilv.setVisibility(View.VISIBLE);
            rlZhunque.setVisibility(View.VISIBLE);
            rlGuiyue.setVisibility(View.VISIBLE);
            tv3.setText("接线方式");
            tv4.setText("电压");
            tv5.setText("电流");
            tv6.setText("通讯规约");
            tv7.setText("费率");
            tv8.setText("有功准确等级");
        } else if (position == 1 || position==2 || position==3) {
            tv3.setText("电压变化");
            tv4.setText("电流变化");
            tv5.setText("TA精确度");
//            tv6.setText("规约");
            rlGuiyue.setVisibility(View.GONE);
            rlFeilv.setVisibility(View.GONE);
            rlZhunque.setVisibility(View.GONE);
//        tv7.setText("费率");
//        tv8.setText("有功准确等级");
        } else if (position == 4) {
            tv3.setText("设备规格");
            tv4.setText("采集方式");
            tv5.setText("规约");
            tv6.setText("通讯方式");
            tv7.setText("载波中心频点");
            rlGuiyue.setVisibility(View.VISIBLE);
            rlFeilv.setVisibility(View.VISIBLE);
            rlZhunque.setVisibility(View.GONE);
//        tv8.setText("有功准确等级");
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

    //设备拣选完成
    @OnClick(R.id.tv_right)
    public void onViewClicked() {
        Intent intent = new Intent(this, ScanDevicePickFinshActivity.class);
        DevicePickFinishRequestEntity mDevicePickFinishRequestEntity =getDevicePickFinishRequestEntity();
        intent.putExtra("DevicePickFinishRequestEntity",mDevicePickFinishRequestEntity);
        startActivity(intent);

    }

    private DevicePickFinishRequestEntity getDevicePickFinishRequestEntity (){
        DevicePickFinishRequestEntity mDevicePickFinishRequestEntity =new DevicePickFinishRequestEntity();
        mDevicePickFinishRequestEntity.setBeginBarCode(etStart.getText().toString());
        mDevicePickFinishRequestEntity.setEndBarCode(etEnd.getText().toString());
        mDevicePickFinishRequestEntity.setEquipCateg(tvDeviceSort.getText().toString());//设备类别
        mDevicePickFinishRequestEntity.setTypeCode(tvLeibie.getText().toString());//互感器类别
        mDevicePickFinishRequestEntity.setSortCode(tvLeixing.getText().toString());//互感器类型
        switch (position) {
            case 0:
//                mDevicePickFinishRequestEntity.setEquipCateg(tvDeviceSort.getText().toString());//设备类别
//                mDevicePickFinishRequestEntity.setTypeCode(tvLeibie.getText().toString());//电能表类别
//                mDevicePickFinishRequestEntity.setSortCode(tvLeixing.getText().toString());//电能表类型
                mDevicePickFinishRequestEntity.setWiringMode(tvJiexian.getText().toString());//接线方式
                mDevicePickFinishRequestEntity.setVoltCode(tvDianya.getText().toString());//电压
                mDevicePickFinishRequestEntity.setRatedCurrent(tvDianliu.getText().toString());//电流
                mDevicePickFinishRequestEntity.setCommPortCode(tvGuiyue.getText().toString());//通讯规约
                mDevicePickFinishRequestEntity.setEquipRate(tvFeilv.getText().toString());//费率
                mDevicePickFinishRequestEntity.setApPreLevelCode(tvZhunque.getText().toString());//有功准确度等级

                break;
            case 1:
                mDevicePickFinishRequestEntity.setVoltRatioCode(tvJiexian.getText().toString());//电压变比
                mDevicePickFinishRequestEntity.setRcRatioCode(tvDianya.getText().toString());//电流变比
                mDevicePickFinishRequestEntity.setTaPreCode(tvDianliu.getText().toString());//TA准确度等级
                break;
            case 2:
                mDevicePickFinishRequestEntity.setSpecCode(tvJiexian.getText().toString());//设备规格
                mDevicePickFinishRequestEntity.setCollMode(tvDianya.getText().toString());//采集方式
                mDevicePickFinishRequestEntity.setProtocolCode(tvDianliu.getText().toString());//采集终端规约
                mDevicePickFinishRequestEntity.setCommMode(tvGuiyue.getText().toString());//通讯方式
                mDevicePickFinishRequestEntity.setCarrierWaveCenterFre(tvFeilv.getText().toString());//载波中心频点
                break;
        }
        return mDevicePickFinishRequestEntity;
    }

}
