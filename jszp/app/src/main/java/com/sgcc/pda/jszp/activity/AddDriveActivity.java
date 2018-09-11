package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增车辆
 *
 * 物流派车 选择车辆时  可以新增
 */
public class AddDriveActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int SAVE_WHAT=1001;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.et_car_no)
    EditText et_car_no;
    @BindView(R.id.et_driver_name)
    EditText et_driver_name;
    @BindView(R.id.et_driver_tel)
    EditText et_driver_tel;

    private String whNo,autoType;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SAVE_WHAT:
                    //保存成功
                    ToastUtils.showToast(AddDriveActivity.this,"保存成功");
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_drive;
    }

    @Override
    public void initView() {
        tvTitle.setText("新增车辆");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("保存");

        whNo= getIntent().getStringExtra("whNo");
        autoType= getIntent().getStringExtra("autoType");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        saveData();
    }

    @OnClick({R.id.iv_driver_tel_clear, R.id.iv_driver_name_clear, R.id.iv_car_no_clear})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, LogisticSendChooseDriveOptionActivity.class);
        switch (view.getId()) {
            case R.id.iv_driver_tel_clear:
                et_driver_tel.setText("");
                break;
            case R.id.iv_driver_name_clear:
                et_driver_name.setText("");
                break;
            case R.id.iv_car_no_clear:
                et_car_no.setText("");
                break;
        }
    }


    /**
     * 保存
     */
    private void saveData() {
        if(TextUtils.isEmpty( et_car_no.getText().toString())){
            ToastUtils.showToast(this,"请输入车牌号码");
            return;
        }
        if(TextUtils.isEmpty( et_driver_name.getText().toString())){
            ToastUtils.showToast(this,"请输入司机名称");
            return;
        }
        if(TextUtils.isEmpty( et_driver_tel.getText().toString())){
            ToastUtils.showToast(this,"请输入司机电话");
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("orgNo", JzspConstants.OrgNo);//组织机构   用户登录返回
        map.put("whNo",whNo);//配送库房编号
        map.put("autoType",autoType);
        map.put("autoBrandNo", et_car_no.getText().toString());
        map.put("staffName", et_driver_name.getText().toString());
        map.put("phoneNo", et_driver_tel.getText().toString());

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_AUTO_SAVE,
                this,map,
                mHandler,SAVE_WHAT, BaseEntity.class);
    }

}
