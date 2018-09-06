package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.sdk.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ManualActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.et_tiaoxingma)
    EditText etOrdernum;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    private int type;
    private int sub_type;//子分类
    private int position;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_manual;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", -1);
        //0是配送装车，1是返回装车，2是返回装车清点，3是配送签收，4是配送扫码签收和口令签收
        sub_type = getIntent().getIntExtra("sub_type", 0);

        position =  getIntent().getIntExtra("position", 0);
        switch (type) {
            case MyComment.SCAN_DELIVERY:
                tvTitle.setText("配送装车");
                break;
            case MyComment.SCAN_RETURN:
                tvTitle.setText("返回装车");
                break;
            case MyComment.RETURN_CHECK:
                tvTitle.setText("返回设备");
                tvHead.setText("请输入返回任务编号");
                break;
            case MyComment.SCAN_SIGN_FOR:
                tvTitle.setText("配送签收");
                tvHead.setText("请输入签收订单编号");
                break;
            case MyComment.SIGN_FOR:
                tvTitle.setText("配送签收");
                btSure.setText("签收");
                if (sub_type == 0) {
                    tvHead.setText("请输入动态口令");
                } else {
                    tvHead.setText("请输入配送任务编号");
                }
                break;
            case MyComment.SCAN_RETURN_SIGN:
                tvTitle.setText("返程签收");
                if (sub_type == 0)
                    tvHead.setText("请输入返回任务编号");
                else
                    tvHead.setText("请输入返回配送单编号");
                break;
            case MyComment.SCAN_DEVICE_OUT:
                tvTitle.setText("设备出库查询");
                if (sub_type == -1) {
                    btSure.setText("查询");
                    tvHead.setText("请输入配送任务编号");
                } else if (sub_type == 1) {
                    tvHead.setText("请输入资产编号");
                }
                break;
            case MyComment.SCAN_DEVICE_IN:
                tvTitle.setText("入库补库");
                tvHead.setText("请输入设备编号");
                break;
            case MyComment.SCAN_DEVICE_PICK:
                tvTitle.setText("设备拣选");
                tvHead.setText("请输入设备编号");
                break;
            case MyComment.QUERY_DEVICE:
                tvTitle.setText(R.string.cx1);
                tvHead.setText("请输入设备编号");
                break;
            case MyComment.SCAN_EXPRESS_DEVICE:
                if (sub_type == 0) {
                    tvTitle.setText("设备条码输入");
                    tvHead.setText("请输入设备条码");
                }else if (sub_type == 1) {
                    tvTitle.setText("快递单号输入");
                    tvHead.setText("请输入快递单号");
                }
                break;

        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(etOrdernum.getText().toString())){
            ToastUtils.showToast(ManualActivity.this,"请输入单据的编号");
            return;
        }
        intent.putExtra("number", etOrdernum.getText().toString());
        switch (type) {
            case MyComment.SCAN_DELIVERY:
            case MyComment.SCAN_RETURN:
            case MyComment.RETURN_CHECK:
            case MyComment.SCAN_SIGN_FOR:
            case MyComment.SCAN_RETURN_SIGN:
            case MyComment.SCAN_DEVICE_PICK:
            case MyComment.SCAN_DEVICE_IN:
            case MyComment.QUERY_DEVICE:
            case MyComment.SCAN_IN_DEVICE:
                setResult(RESULT_OK, intent);
                break;
            case MyComment.SIGN_FOR: {
                    setResult(RESULT_OK, intent);
            }
            break;
            case MyComment.SCAN_DEVICE_OUT:
                if (sub_type == 1) {
                    setResult(RESULT_OK, intent);
                } else {

                }
                break;
            case MyComment.SCAN_EXPRESS_DEVICE:
                intent.putExtra("position",position);
                setResult(RESULT_OK, intent);
                break;
        }
        finish();
    }

    @Override
    public void returnback(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }


}
