package com.sgcc.pda.jszp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JSZPDeliveryReceipIoPlanDetsAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.adapter.deliverySignAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.DeviceItem;
import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptResultEntity;
import com.sgcc.pda.jszp.bean.OrderItem;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JSZPProgressDialogUtils;
import com.sgcc.pda.jszp.util.JszpNumberUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 配送签收界面
 */
public class DeliverySignActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET_WHAT=1001;
    /**
     * 控件UI
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.bt_kouling)
//    Button btKouling;
    @BindView(R.id.bt_saoma)
    Button btSaoma;
    @BindView(R.id.tv_task_num)
    TextView tvTaskNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    /**
     * 界面handler
     */
    private  Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET_WHAT:
                    //得到数据
                    JSZPProgressDialogUtils.getInstance(DeliverySignActivity.this).onFinish();
                    JSZPDeliveryReceiptResultEntity obj = (JSZPDeliveryReceiptResultEntity) msg.obj;
                    //初始化显示UI
                    initUi(obj);
                    break;
            }
        }
    };

    /**
     * 将数据和UI绑定
     * @param obj
     */
    private void initUi(JSZPDeliveryReceiptResultEntity obj) {
        tvTaskNum.setText(obj.getSplitTask().getTaskNo());
        tvAddress.setText(obj.getSplitTask().getDpName());
        tvCount.setText(JszpNumberUtils.sumNumber(obj.getSplitTask().getIoPlanDets()));
        initListViewUI(obj.getSplitTask().getIoPlanDets());
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_pssignfor;
    }

    @Override
    public void initView() {
        tvTitle.setText("配送签收");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        obtainNetData(number);
    }

    /**
     * 获取网络数据
     * @param number 单据的编号
     */
    private void obtainNetData(String number) {
        JSZPProgressDialogUtils.getInstance(DeliverySignActivity.this).show();
        JSZPDeliveryReceiptRequestEntity jszpDeliveryReceiptRequestEntity = new JSZPDeliveryReceiptRequestEntity();
        jszpDeliveryReceiptRequestEntity.setBaseNo(number);
        JSZPOkgoHttpUtils.post(JSZPUrls.URL_GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET,
                this,jszpDeliveryReceiptRequestEntity,
                mHandler,GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET_WHAT);
    }

    /**
     * 初始化list的ui
     * @param ioPlanDets
     */
    private void initListViewUI(ArrayList<JSZPDeliveryReceiptResultEntity.JSZPDeliveryReceiptResultSplitTaskEntity
            .JSZPDeliveryReceiptResultIoPlanDetsEntity> ioPlanDets) {
        rvOrders.setNestedScrollingEnabled(false);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.addItemDecoration(new SpaceItemDecoration(15));
        rvOrders.setAdapter(new JSZPDeliveryReceipIoPlanDetsAdapter(ioPlanDets));

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.bt_saoma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.bt_kouling: {
//                Intent intent = new Intent(this, ManualActivity.class);
//                intent.putExtra("type", MyComment.SIGN_FOR);
//                intent.putExtra("sub_type", 0);
//                startActivityForResult(intent, 101);
//            }
//            break;
            case R.id.bt_saoma: {
                Intent intent = new Intent(this, ScanActivity.class);
                intent.putExtra("type", MyComment.SIGN_FOR);
                startActivityForResult(intent, 102);
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(this, SignForResultActivity.class);

        if (resultCode == RESULT_OK) {
            intent.putExtra("success", true);
            startActivity(intent);
            finish();
        } else {
            intent.putExtra("success", false);
            startActivity(intent);
        }
    }
}
