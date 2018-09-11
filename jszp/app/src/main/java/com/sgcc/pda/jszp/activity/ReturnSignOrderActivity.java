package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.ReturnItemAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.JSZPEquipmentScanningResultEntity;
import com.sgcc.pda.jszp.bean.JSZPReturnReceiptRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPReturnReceiptResultEntity;
import com.sgcc.pda.jszp.bean.JSZPReturnSignOrderRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPreturnShipmentPlanRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPreturnShipmentPlanResultEntity;
import com.sgcc.pda.jszp.bean.JSZPreturnStoragePlanResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ReturnSignOrderActivity extends BaseActivity {
    /**
     * 编码code
     */
    private static final int GET_WAIT_SIGN_NEGATIVE_IN_PLAN_DET_WHAT = 8001;
    private static final int SCAN_DEV_WHAT = 8002;
    private static final int SIGN_NEGATIVE_IN_PLAN_DET_WHAT=8003;
    /**
     * 控件
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.rv_returns)
    RecyclerView rvReturns;
    /**
     * 其他参数
     */
    BaseItemAdapter returnAdapter;
    /**
     * 数据list
     */
    ArrayList<IoTaskDets> returnItems;
    /**
     * 点击item的角标
     */
    int checkposition = -1;
    /**
     * 扫描的编码
     */
    private String number;

    /**
     * handler 处理
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_WAIT_SIGN_NEGATIVE_IN_PLAN_DET_WHAT:
                    JSZPreturnShipmentPlanResultEntity resultEntity = (JSZPreturnShipmentPlanResultEntity) msg.obj;
                    upDataUI(resultEntity);
                    break;
                case SCAN_DEV_WHAT:
                    JSZPEquipmentScanningResultEntity jszpEquipmentScanningResultEntity =
                            (JSZPEquipmentScanningResultEntity) msg.obj;
                    upDataItemUI(jszpEquipmentScanningResultEntity);
                    break;
                case SIGN_NEGATIVE_IN_PLAN_DET_WHAT:
                    JSZPReturnReceiptResultEntity returnReceiptResultEntity =
                            (JSZPReturnReceiptResultEntity) msg.obj;
                    Intent intent = new Intent(ReturnSignOrderActivity.this, SignForResultActivity.class);
                    if ("1".equals(returnReceiptResultEntity.getRT_F())){
                        intent.putExtra("success", true);
                        startActivity(intent);
                        finish();
                    }else {
                        intent.putExtra("success", false);
                        startActivity(intent);
                        finish();
                    }
                    break;
            }
        }
    };
    /**
     * 查分任务单号
     */
    private String splitTaskNo;
    private JSZPreturnStoragePlanResultEntity.JSZPreturnStoragePlanSplitTask mJszPreturnStoragePlanSplitTask;

    private void upDataItemUI(JSZPEquipmentScanningResultEntity
                                      jszpEquipmentScanningResultEntity) {
        returnItems.get(checkposition - 1).setRealCount(jszpEquipmentScanningResultEntity.getScanData().getDiscoverNum());
//        returnItems.get(checkposition - 1).setSelected(true);
        returnAdapter.notifyItemChanged(checkposition);
    }

    /**
     * 头布局控件
     */
    private TextView tv_delivery_order;
    private TextView tv_box_qty;
    private TextView tv_receiving_warehouse;

    /**
     * 刷新UI
     *
     * @param resultEntity
     */
    private void upDataUI(JSZPreturnShipmentPlanResultEntity resultEntity) {
        splitTaskNo = resultEntity.getSplitTask().getSplitTaskNo();
        tv_delivery_order.setText("配送单: " + resultEntity.getSplitTask().getTaskNo());
        tv_box_qty.setText(resultEntity.getSplitTask().getIoTaskDets().get(0).getFinishBoxQty() + "箱");
        tv_receiving_warehouse.setText("接收库房："+mJszPreturnStoragePlanSplitTask.getDpName());
        returnItems.clear();
        returnItems.addAll(resultEntity.getSplitTask().getIoTaskDets());
        //刷新adapter
        returnAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_return_car;
    }

    @Override
    public void initView() {
        tvTitle.setText("返程装车");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra("jszPreturnStoragePlanSplitTask");
        //强转Serializable
        if (serializable instanceof JSZPreturnStoragePlanResultEntity.
                JSZPreturnStoragePlanSplitTask) {
            mJszPreturnStoragePlanSplitTask = (JSZPreturnStoragePlanResultEntity.JSZPreturnStoragePlanSplitTask) serializable;
            //获取当前拆分任务的code码
            splitTaskNo = mJszPreturnStoragePlanSplitTask.getSplitTaskNo();
        }
        //获取网络列表
        obtainNetData();
        //初始化list列表
        initDataView();
        //初始化提交按钮
        changeBtnStatus();
    }

    /**
     * 网络请求
     */
    private void obtainNetData() {
        JSZPreturnShipmentPlanRequestEntity requestEntity = new JSZPreturnShipmentPlanRequestEntity();
      requestEntity.setSplitTaskNo(splitTaskNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_WAIT_SIGN_NEGATIVE_IN_PLAN_DET,
                this, requestEntity,
                mHandler, GET_WAIT_SIGN_NEGATIVE_IN_PLAN_DET_WHAT,JSZPreturnShipmentPlanResultEntity.class);
    }

    /**
     * 初始化adapter
     */
    private void initDataView() {
        rvReturns.setLayoutManager(new LinearLayoutManager(this));
        returnAdapter = new BaseItemAdapter();
        returnItems = new ArrayList<>();
        LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headview = lif.inflate(R.layout.head_return_car, rvReturns, false);
//        View footview = lif.inflate(R.layout.foot_orders, rvReturns, false);
        tv_delivery_order = (TextView) headview.findViewById(R.id.tv_delivery_order);
        tv_box_qty = (TextView) headview.findViewById(R.id.tv_box_qty);
        tv_receiving_warehouse = (TextView) headview.findViewById(R.id.tv_receiving_warehouse);
        returnAdapter.addHeadView(headview);
//        returnAdapter.addFootView(footview);
        ReturnItemAdapter ria = new ReturnItemAdapter<>();
        ria.setOnChildClickCallBack(new ReturnItemAdapter.onChildClickCallBack() {

            @Override
            public void onSomaClick(BaseViewHolder baseViewHolder) {
                checkposition = baseViewHolder.getItemPosition();
                Intent intent = new Intent(ReturnSignOrderActivity.this, JSZPScanItActivity.class);
                IoTaskDets ioTaskDets = (IoTaskDets) baseViewHolder.getItemData();
                intent.putExtra("taskNo", ioTaskDets.getPlanDetNo());
                intent.putExtra("realNo", ioTaskDets.getTaskId());
                intent.putExtra("currentNum", 0);
                intent.putExtra("totalNum", ioTaskDets.getQty());
                intent.putExtra("isIo",false);
                intent.putExtra("equipCode",ioTaskDets.getEquipCode());
                startActivityForResult(intent, 101);
            }

            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                checkposition = baseViewHolder.getItemPosition();
                Intent intent = new Intent(ReturnSignOrderActivity.this, ModifierCountActivity.class);
                IoTaskDets data = (IoTaskDets) baseViewHolder.getItemData();
                intent.putExtra("plancount", data.getQty());
                intent.putExtra("realcount", data.getRealCount());
                startActivityForResult(intent, 102);
            }
        });
        returnAdapter.register(IoTaskDets.class, ria);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.gray_divider_1dp));
        rvReturns.addItemDecoration(divider);
        rvReturns.setAdapter(returnAdapter);
        returnAdapter.setDataItems(returnItems);
    }

    @Override
    public void initListener() {

    }


    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        boolean flag = false;
        //便利每一个条目真实数量和应有数量对照
//        for (IoTaskDets ioTaskDet:returnItems){
//            if (ioTaskDet.getQty()==ioTaskDet.getRealCount()){
//                flag=true;
//            }else {
//                flag=false;
//            }
//        }
//        if (!flag) {
//            ToastUtils.showToast(this,"任务核对有差异");
//            return;
//        }
        //返程入库提交
        obtainNetLoadingData();
    }

    private void obtainNetLoadingData() {
        JSZPReturnReceiptRequestEntity requestEntity = new JSZPReturnReceiptRequestEntity();
        requestEntity.setSplitTaskNo(splitTaskNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SIGN_NEGATIVE_IN_PLAN_DET,
                this, requestEntity,
                mHandler, SIGN_NEGATIVE_IN_PLAN_DET_WHAT,JSZPEquipmentScanningResultEntity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
            case 102:
                if (resultCode != RESULT_OK) return;
                int realcount = data.getIntExtra("realcount", 0);
                returnItems.get(checkposition-1).setRealCount(realcount);
                returnAdapter.notifyItemChanged(checkposition);
                changeBtnStatus();
                break;
        }
    }

    /**
     * 请求网络获取扫描设备接口
     *
     * @param text
     */
    private void obtainNetData(String text) {
        JSZPReturnSignOrderRequestEntity requestEntity = new JSZPReturnSignOrderRequestEntity();
        requestEntity.setBarCodes(text);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SIGN_NEGATIVE_IN_PLAN_DET,
                this, requestEntity,
                mHandler, SIGN_NEGATIVE_IN_PLAN_DET_WHAT,JSZPEquipmentScanningResultEntity.class);
    }
    /**
     * 修改返回装车按钮
     */
    private void changeBtnStatus(){
        boolean isEnable = true;
        if(returnItems.size() == 0){
            isEnable = false;
        }
        for (IoTaskDets ioTaskDets:returnItems){
            if(ioTaskDets.getQty() != ioTaskDets.getRealCount()){
                isEnable = false;
                break;
            }
        }

        btSure.setTextColor(isEnable?getResources().getColor(R.color.title_green):getResources().getColor(R.color.darkgray));
        btSure.setClickable(isEnable);
    }
}
