package com.sgcc.pda.jszp.activity;

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
import com.sgcc.pda.jszp.bean.ExpressConfirmResultEntity;
import com.sgcc.pda.jszp.bean.ExpressDistAutoesItem;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快递确认
 */
public class ExpressConfirmActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_DETAIL_WHAT = 1001;
    public static final int GET_CONFIRM_WHAT = 1002;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_task_no)
    TextView tv_task_no;
    @BindView(R.id.tv_kf_name)
    TextView tv_kf_name;
    @BindView(R.id.tv_receiver)
    TextView tv_receiver;
    @BindView(R.id.tv_tel)
    TextView tv_tel;
    @BindView(R.id.tv_addr)
    TextView tv_addr;
    @BindView(R.id.et_device_num)
    EditText etDeviceNum;


    private String taskNo;
    private ExpressDistAutoesItem expressDistAutoesItem;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_DETAIL_WHAT:
                    //列表数据
                    ExpressConfirmResultEntity expressConfirmResultEntity= (ExpressConfirmResultEntity) msg.obj;
                    expressDistAutoesItem =  expressConfirmResultEntity.getExpressDistAuto();
                    initUiData();
                    break;
                case GET_CONFIRM_WHAT:
                    //快递确认
                    ToastUtils.showToast(ExpressConfirmActivity.this,"确认成功");
                    Intent intent = new Intent(ExpressConfirmActivity.this, ExpressBindActivitiy.class);
                    intent.putExtra("taskNo",taskNo);
                    intent.putExtra("distAutoId",expressDistAutoesItem.getDistAutoId());
                    startActivity(intent);
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_express_confirm;
    }

    @Override
    public void initView() {
        taskNo = getIntent().getStringExtra("taskno");
    }

    @Override
    public void initData() {
        tvTitle.setText("快递确认");
        getListData();
    }

    @Override
    public void initListener() {

    }
    /**
     * 获取物流派车列表数据
     */
    private void getListData() {
        Map<String,String> map = new HashMap<>();
        map.put("taskNo",taskNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_WAIT_CONFIRM_EXPRESS_TASK_DETAIL,
                this,map,
                mHandler,GET_DETAIL_WHAT,ExpressConfirmResultEntity.class);
    }

    /**
     * 确认
     */
    private void confirm() {
        if(expressDistAutoesItem == null){
            return;
        }

        if(TextUtils.isEmpty(etDeviceNum.getText().toString())){
            ToastUtils.showToast(this,"请输入设备数量");
            return;
        }
        if(expressDistAutoesItem.getQty() != Integer.valueOf(etDeviceNum.getText().toString())){
            ToastUtils.showToast(this,"设备数量不符合，不能确认");
            return;
        }


        Map<String,String> map = new HashMap<>();
        map.put("taskNo",taskNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_EXPRESS_CONFIRM,
                this,map,
                mHandler,GET_CONFIRM_WHAT,BaseEntity.class);
    }


    @OnClick({R.id.tv_confirm})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                confirm();
                break;
        }
    }

    /**
     * 设置详情数据
     */
    private void initUiData() {
        tv_addr.setText(expressDistAutoesItem.getDpAddress());
        tv_kf_name.setText(expressDistAutoesItem.getDpName());
        tv_receiver.setText(expressDistAutoesItem.getRcver());
        tv_task_no.setText(expressDistAutoesItem.getTaskNo());
        tv_tel.setText(expressDistAutoesItem.getMobile());
    }
}
