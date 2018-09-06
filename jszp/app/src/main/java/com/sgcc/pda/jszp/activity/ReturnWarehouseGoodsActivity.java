package com.sgcc.pda.jszp.activity;

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
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.ReturnWarehouseGoodsAdapter;

import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.ReturnWarehousGoodsResultEntity;
import com.sgcc.pda.jszp.bean.ReturnWarehouseGoodsRequestEntity;
import com.sgcc.pda.jszp.bean.ReturnWarehouseResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 返程入库返回任务商品（第jiem）
 */
public class ReturnWarehouseGoodsActivity extends BaseActivity {

    private static final int WHAT_RETURN_WAREHOUSE_GOODS_LIS=9001;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_return_warehouse_detail)
    RecyclerView rvReturnWarehouseDetail;


    ReturnWarehouseGoodsAdapter mReturnWarehouseGoodsAdapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private ReturnWarehouseResultEntity.SplitTasks mSplitTasks;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_RETURN_WAREHOUSE_GOODS_LIS:
                    //得到数据
                    ReturnWarehousGoodsResultEntity mResult = (ReturnWarehousGoodsResultEntity) msg.obj;
                    if("1".equals(mResult.getRT_F())){
                        refreshDataUI(mResult);
                    }
                    break;
            }
        }
    };

    /**
     * 数据list
     */
    ArrayList<IoTaskDets> returnItems;
    /**
     * 其他参数
     */
    BaseItemAdapter returnAdapter;
    /**
     * 查分任务单号
     */
    private String splitTaskNo;
    /**
     * 头布局控件
     */
    private TextView tv_delivery_order;
    private TextView tv_box_qty;
    private TextView tv_receiving_warehouse;


    private void refreshDataUI(ReturnWarehousGoodsResultEntity obj) {
        if (obj == null) {
            return;
        }
        splitTaskNo = obj.getSplitTask().getSplitTaskNo();
        tv_delivery_order.setText("配送单: " + obj.getSplitTask().getTaskNo());
        tv_box_qty.setText(obj.getSplitTask().getIoTaskDets().get(0).getFinishBoxQty() + "箱");
        tv_receiving_warehouse.setText("返回单位"+obj.getSplitTask().getDpName());

        returnItems.addAll(obj.getSplitTask().getIoTaskDets());

        //刷新adapter
        returnAdapter.notifyDataSetChanged();

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_return_warehouse_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText(R.string.ps10);
        rvReturnWarehouseDetail.setLayoutManager(new LinearLayoutManager(this));//初始化recycleview
        mReturnWarehouseGoodsAdapter=new ReturnWarehouseGoodsAdapter(this);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra("returnWarehouseResultEntity");
        //强转Serializable
        if (serializable instanceof ReturnWarehouseResultEntity.SplitTasks) {
            mSplitTasks = (ReturnWarehouseResultEntity.SplitTasks) serializable;
            //获取当前拆分任务的code码
            splitTaskNo = mSplitTasks.getSplitTaskNo();
        }
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(false);//启用加载
        //返程入库商品网络请求
        obtainNetData();
        //初始化数据
        initDataView();
    }

    private void initDataView() {

        rvReturnWarehouseDetail.setLayoutManager(new LinearLayoutManager(this));
        returnAdapter = new BaseItemAdapter();
        returnItems = new ArrayList<>();
        LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headview = lif.inflate(R.layout.head_return_car, rvReturnWarehouseDetail, false);

        tv_delivery_order = (TextView) headview.findViewById(R.id.tv_delivery_order);
        tv_box_qty = (TextView) headview.findViewById(R.id.tv_box_qty);
        tv_receiving_warehouse = (TextView) headview.findViewById(R.id.tv_receiving_warehouse);
        returnAdapter.addHeadView(headview);

        returnAdapter.register(IoTaskDets.class, mReturnWarehouseGoodsAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.gray_divider_1dp));
        rvReturnWarehouseDetail.addItemDecoration(divider);


        rvReturnWarehouseDetail.setAdapter(returnAdapter);
        returnAdapter.setDataItems(returnItems);
    }

    //网络请求
    private void obtainNetData() {
        ReturnWarehouseGoodsRequestEntity requestEntity = new ReturnWarehouseGoodsRequestEntity();
        requestEntity.setSplitTaskNo(splitTaskNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_RETURN_WAREHOUSE_GOODS_LIST,
                this, requestEntity,
                mHandler, WHAT_RETURN_WAREHOUSE_GOODS_LIS, ReturnWarehousGoodsResultEntity.class);
    }

    @Override
    public void initListener() {

    }



}
