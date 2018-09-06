package com.sgcc.pda.jszp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.adapter.logisticsSendAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;
import com.sgcc.pda.jszp.bean.LogisticsSendRequestEntity;
import com.sgcc.pda.jszp.bean.LogisticsSendResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 物流派车
 */
public class LogisticsSendActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_LIST_WHAT=1001;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_not_send_count)
    TextView tvNotSendCount;
    @BindView(R.id.tv_sended_count)
    TextView tvSendedCount;
    @BindView(R.id.rv_cars)
    RecyclerView rvCars;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    BaseItemAdapter taskAdapter;
    List<LogisticsDistAutoesItem> mList;
    private boolean isFirst = true;

    Calendar ca = Calendar.getInstance();
    int mYear = ca.get(Calendar.YEAR);
    int mMonth = ca.get(Calendar.MONTH);
    int mDay = ca.get(Calendar.DAY_OF_MONTH);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

    LogisticsSendRequestEntity logisticsSendRequestEntity;//请求实体
    LogisticsSendResultEntity logisticsSendResultEntity;//查询结果集


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_LIST_WHAT:
                    //列表数据
                    logisticsSendResultEntity = (LogisticsSendResultEntity) msg.obj;
                    if(logisticsSendRequestEntity.getPageNo() == JzspConstants.PageStart){
                        //刷新
                        mList.clear();
                        initUiData();
                    }
                    List<LogisticsDistAutoesItem> items = ((LogisticsSendResultEntity) msg.obj).getResult().getLogisticsDistAutoes();
                    mList.addAll(items);
                    taskAdapter.notifyDataSetChanged();
                    if(items.size()<JzspConstants.PageSize){
                        refreshLayout.setEnableLoadmore(false);
                    }
                    break;
            }
        }
    };


    @Override
    public int getLayoutResId() {
        return R.layout.activity_logistics_send;
    }

    @Override
    public void initView() {
        tvTitle.setText("物流派车");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("筛选");


        logisticsSendRequestEntity = new LogisticsSendRequestEntity();

    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        tvDate.setText(DateUtil.toDateString(new Date(), sdf));
        logisticsSendRequestEntity.setEffectDate(tvDate.getText().toString());
        initListUiData();

        refreshListData();
    }

    @Override
    public void initListener() {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshListData();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                logisticsSendRequestEntity.setPageNo(logisticsSendRequestEntity.getPageNo()+1);
                getListData();
                refreshlayout.finishLoadmore();
            }
        });
    }

    //初始化界面数据
    private void initUiData(){
        tvNotSendCount.setText(logisticsSendResultEntity.getResult().getNotSendNum()+"辆");
        tvSendedCount.setText(logisticsSendResultEntity.getResult().getSendNum()+"辆");
    }
    //初始化列表数据
    private void initListUiData(){
        rvCars.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new BaseItemAdapter();
        taskAdapter.register(LogisticsDistAutoesItem.class, new logisticsSendAdapter<LogisticsDistAutoesItem>(this));
        rvCars.addItemDecoration(new SpaceItemDecoration(7));

        rvCars.setAdapter(taskAdapter);
        taskAdapter.setDataItems(mList);
        taskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                LogisticsDistAutoesItem logisticsDistAutoesItem = mList.get(baseViewHolder.getItemPosition());
                Intent intent = new Intent(LogisticsSendActivity.this, LogisticDetailActivity.class);
                intent.putExtra("distAutoId",logisticsDistAutoesItem.getDistAutoId());
                intent.putExtra("taskNo",logisticsDistAutoesItem.getTaskNo());
                startActivityForResult(intent, 101);
            }
        });

    }
    private void refreshListData(){
        logisticsSendRequestEntity.setPageNo(JzspConstants.PageStart);
        logisticsSendRequestEntity.setPageSize(JzspConstants.PageSize);
        refreshLayout.setEnableLoadmore(true);
        getListData();
    }
    /**
     * 获取物流派车列表数据
     */
    private void getListData() {
//        logisticsSendRequestEntity.setEffectDate("20180820");
//        logisticsSendRequestEntity.setPageNo(2);
//        logisticsSendRequestEntity.setPageSize(2);
//        Map<String,String> map = new HashMap<>();
//        map.put("effectDate",logisticsSendRequestEntity.getEffectDate());
//        map.put("autoType",logisticsSendRequestEntity.getAutoType());
//        map.put("status",logisticsSendRequestEntity.getStatus());
//        map.put("pageNo",logisticsSendRequestEntity.getPageNo()+"");
//        map.put("pageSize",logisticsSendRequestEntity.getPageSize()+"");
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_LOGISTIC_SEND_LIST,
                this,logisticsSendRequestEntity,
                mHandler,GET_LIST_WHAT,LogisticsSendResultEntity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面消失取消掉网络请求
        JSZPOkgoHttpUtils.cancelHttp(this);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent intent = new Intent(this, LogisticScanActivity.class);
        intent.putExtra("autoType",logisticsSendRequestEntity.getAutoType());
        intent.putExtra("status",logisticsSendRequestEntity.getStatus());
        startActivityForResult(intent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        switch (requestCode) {
            case 100:
                //查询条件
                logisticsSendRequestEntity.setAutoType(data.getStringExtra("cart_type"));
                logisticsSendRequestEntity.setStatus(data.getStringExtra("cart_status"));
                refreshListData();
                break;
            case 101:
                //列表
                refreshListData();
                break;
        }
    }


    @OnClick({R.id.tv_date, R.id.tv_previous, R.id.tv_next})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
                new DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.tv_previous:
                try {
                    Date date = sdf.parse(tvDate.getText().toString());
                    ca.setTime(date);
                    int day = ca.get(Calendar.DATE);
                    ca.set(Calendar.DATE, day - 1);
                    mYear = ca.get(Calendar.YEAR);
                    mMonth = ca.get(Calendar.MONTH);
                    mDay = ca.get(Calendar.DAY_OF_MONTH);
                    String dayAfter = sdf.format(ca.getTime());
                    tvDate.setText(dayAfter);
                    //刷新数据
                    logisticsSendRequestEntity.setEffectDate(dayAfter);
                    refreshListData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_next:
                try {
                    Date date = sdf.parse(tvDate.getText().toString());
                    ca.setTime(date);
                    int day = ca.get(Calendar.DATE);
                    ca.set(Calendar.DATE, day + 1);
                    mYear = ca.get(Calendar.YEAR);
                    mMonth = ca.get(Calendar.MONTH);
                    mDay = ca.get(Calendar.DAY_OF_MONTH);
                    String dayAfter = sdf.format(ca.getTime());
                    tvDate.setText(dayAfter);

                    //刷新数据
                    logisticsSendRequestEntity.setEffectDate(dayAfter);
                    refreshListData();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            tvDate.setText(days);

            //刷新数据
            logisticsSendRequestEntity.setEffectDate(days);
            refreshListData();
        }
    };
}
