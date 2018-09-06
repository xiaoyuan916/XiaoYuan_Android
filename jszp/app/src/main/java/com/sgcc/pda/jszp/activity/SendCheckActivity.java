package com.sgcc.pda.jszp.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.SendCheckAdapter;
import com.sgcc.pda.jszp.adapter.SendExpressAdapter;
import com.sgcc.pda.jszp.adapter.SendSelfAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.TaskConfirmRequestEntity;
import com.sgcc.pda.jszp.bean.TaskConfirmResultEntity;
import com.sgcc.pda.jszp.bean.TaskExpressRequestEntity;
import com.sgcc.pda.jszp.bean.TaskExpressResultEntity;
import com.sgcc.pda.jszp.bean.TaskItemRequestEntity;
import com.sgcc.pda.jszp.bean.TaskItemResultEntity;
import com.sgcc.pda.jszp.bean.TaskSelfRequestEntity;
import com.sgcc.pda.jszp.bean.TaskSelfResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 出发确认数据列表
 */
public class SendCheckActivity extends BaseActivity {

    private static final int GET_LOGISTICS_WHAT = 4001;
    private static final int GET_EXPRESS_WHAT = 4002;
    private static final int GET_SELF_WHAT = 4003;

    private static final int GET_LOGISTICS_SURE_WHAT = 4004;
    private static final int GET_EXPRESS_SURE_WHAT = 4005;
    private static final int GET_SELF_SURE_WHAT = 4006;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    private View mFooterView;//添加footer

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rv_send_tasks)
    RecyclerView rvSendtasks;
    @BindView(R.id.rv_express_tasks)
    RecyclerView rvExpressTasks;
    @BindView(R.id.rv_self_tasks)
    RecyclerView rvSelfTasks;
    @BindView(R.id.tl_send)
    TabLayout tlSend;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.express_refreshLayout)
    SmartRefreshLayout expressRefreshLayout;
    @BindView(R.id.self_refreshLayout)
    SmartRefreshLayout selfRefreshLayout;

    ArrayList<TaskItemResultEntity> SendItems;
    SendCheckAdapter sendCheckAdapter;
    SendExpressAdapter mSendExpressAdapter;
    SendSelfAdapter mSendSelfAdapter;

    TaskItemRequestEntity mTaskItemRequestEntity;//物流网络请求实体类
    TaskExpressRequestEntity mTaskExpressRequestEntity;//快递网络请求实体类
    TaskSelfRequestEntity mTaskSelfRequestEntity;//自提网络请求实体类


    ArrayList<TaskItemResultEntity.LogisticsDistAutoes> logisticsList = new ArrayList<>();
    ArrayList<TaskExpressResultEntity.ExpressDistAutoes> expressList = new ArrayList<>();
    ArrayList<TaskSelfResultEntity.TakeDistAutoes> selfList = new ArrayList<>();


    private int postion = 0;//判断标识
    private int pageNo1 = JzspConstants.PageStart;
    private int pageNo2 = JzspConstants.PageStart;
    private int pageNo3 = JzspConstants.PageStart;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_LOGISTICS_WHAT:
                    if (logisticsList.size() != 0) {
                        initViewData(logisticsList);
                    } else {
                        if (null != ((TaskItemResultEntity) msg.obj).getLogisticsDistAutoes()) {
                            logisticsList.addAll(((TaskItemResultEntity) msg.obj).getLogisticsDistAutoes());
                            initViewData(logisticsList);
                            sendCheckAdapter.notifyDataSetChanged();
                            if (logisticsList.size() < JzspConstants.PageSize) {
                                refreshLayout.setEnableLoadmore(false);
                            }
                        } else {
                            return;
                        }
                    }
                    break;
                case GET_EXPRESS_WHAT:
                    if (expressList.size() != 0) {
                        initExpressViewData(expressList);
                    } else {
                        if (null != ((TaskExpressResultEntity) msg.obj).getExpressDistAutos()) {
                            expressList.addAll(((TaskExpressResultEntity) msg.obj).getExpressDistAutos());
                            initExpressViewData(expressList);
                            mSendExpressAdapter.notifyDataSetChanged();
                            if (expressList.size() < JzspConstants.PageSize) {
                                expressRefreshLayout.setEnableLoadmore(false);
                            }
                        } else {
                            return;
                        }
                    }
                    break;
                case GET_SELF_WHAT:
                    if (selfList.size() != 0) {
                        initSelfData(selfList);
                    } else {
                        if (null != ((TaskSelfResultEntity) msg.obj).getTakeDistAutoes()) {
                            selfList.addAll(((TaskSelfResultEntity) msg.obj).getTakeDistAutoes());
                            initSelfData(selfList);
                            mSendSelfAdapter.notifyDataSetChanged();
                            if (selfList.size() < JzspConstants.PageSize) {
                                selfRefreshLayout.setEnableLoadmore(false);
                            }
                        } else {
                            return;
                        }
                    }
                    break;
                case GET_LOGISTICS_SURE_WHAT://物流确认出发
                    TaskConfirmResultEntity mTaskConfirmResultEntity = (TaskConfirmResultEntity) msg.obj;
                    if ("1".equals(mTaskConfirmResultEntity.getRT_F())) {
                        Toast.makeText(SendCheckActivity.this, "发车成功!", Toast.LENGTH_SHORT).show();
                        refreshListData();
                        setView();
                    }
                    break;
                case GET_EXPRESS_SURE_WHAT://快递确认出发
                    TaskConfirmResultEntity mExptess = (TaskConfirmResultEntity) msg.obj;
                    if ("1".equals(mExptess.getRT_F())) {
                        Toast.makeText(SendCheckActivity.this, "发车成功!", Toast.LENGTH_SHORT).show();
                        refreshExpressListData();
                        setView();
                    }
                    break;
                case GET_SELF_SURE_WHAT://自提确认出发
                    TaskConfirmResultEntity mSelf = (TaskConfirmResultEntity) msg.obj;
                    if ("1".equals(mSelf.getRT_F())) {
                        Toast.makeText(SendCheckActivity.this, "发车成功!", Toast.LENGTH_SHORT).show();
                        refreshSelfListData();
                        setView();
                    }
                    break;
            }
        }
    };

    /**
     * 重新设置view状态
     */
    private void setView() {
        tvRight.setText("多选");
        tvSure.setVisibility(View.GONE);
    }

    //绑定网络请求数据
    public void initViewData(ArrayList<TaskItemResultEntity.LogisticsDistAutoes> obj) {
        rvSendtasks.setLayoutManager(new LinearLayoutManager(this));
        sendCheckAdapter = new SendCheckAdapter(SendCheckActivity.this, obj);
        rvSendtasks.setAdapter(sendCheckAdapter);
    }

    //绑定快递请求数据
    public void initExpressViewData(ArrayList<TaskExpressResultEntity.ExpressDistAutoes> obj) {
        rvExpressTasks.setLayoutManager(new LinearLayoutManager(this));//初始化recycleview
        mSendExpressAdapter = new SendExpressAdapter(SendCheckActivity.this, obj);
        rvExpressTasks.setAdapter(mSendExpressAdapter);
    }

    //绑定自提出发请求数据
    public void initSelfData(ArrayList<TaskSelfResultEntity.TakeDistAutoes> obj) {
        rvSelfTasks.setLayoutManager(new LinearLayoutManager(this));//初始化recycleview
        mSendSelfAdapter = new SendSelfAdapter(SendCheckActivity.this, obj);
        rvSelfTasks.setAdapter(mSendSelfAdapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_send_check;
    }

    @Override
    public void initView() {
        rvSendtasks.setLayoutManager(new LinearLayoutManager(this));//初始化recycleview
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("多选");
        tvTitle.setText(R.string.ps5);
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(false);//启用加载
    }

    @Override
    public void initData() {
        mTaskItemRequestEntity = new TaskItemRequestEntity();
        mTaskExpressRequestEntity = new TaskExpressRequestEntity();
        mTaskSelfRequestEntity = new TaskSelfRequestEntity();
        //获取网络列表
//        getListData();
        refreshListData();
        tlSend.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {//选中的tab
                if (0 == tab.getPosition()) {
                    postion = 0;
                    setView();
                    expressRefreshLayout.setVisibility(View.GONE);
                    selfRefreshLayout.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);

                    rvSendtasks.setVisibility(View.VISIBLE);
                    rvExpressTasks.setVisibility(View.GONE);
                    rvSelfTasks.setVisibility(View.GONE);

                    tvSure.setVisibility(View.GONE);
                    if (selfList.size() != 0) {
                        initSelfData(selfList);
                    } else {
                        refreshListData();
                    }
                } else if (1 == tab.getPosition()) {
                    postion = 1;
                    setView();
                    expressRefreshLayout.setVisibility(View.VISIBLE);
                    selfRefreshLayout.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.GONE);
                    rvSendtasks.setVisibility(View.GONE);
                    rvExpressTasks.setVisibility(View.VISIBLE);
                    rvSelfTasks.setVisibility(View.GONE);
                    tvSure.setVisibility(View.GONE);
                    if (expressList.size() != 0) {
                        initExpressViewData(expressList);
                    } else {
                        refreshExpressListData();
                    }
                } else if (2 == tab.getPosition()) {
                    postion = 2;
                    setView();
                    expressRefreshLayout.setVisibility(View.GONE);
                    selfRefreshLayout.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                    tvSure.setVisibility(View.GONE);
                    rvSendtasks.setVisibility(View.GONE);
                    rvExpressTasks.setVisibility(View.GONE);
                    rvSelfTasks.setVisibility(View.VISIBLE);
                    if (selfList.size() != 0) {
                        initSelfData(selfList);
                    } else {
                        refreshSelfListData();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public void initListener() {
        //物流刷新
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
                pageNo1++;
                mTaskItemRequestEntity.setPageNo(pageNo1);
                mTaskItemRequestEntity.setPageSize(10);
                getListData();
                refreshlayout.finishLoadmore();
            }
        });

        //快递刷新
        expressRefreshLayout.setEnableRefresh(true);
        expressRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshExpressListData();
                refreshlayout.finishRefresh();
            }
        });
        expressRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNo2++;
                mTaskItemRequestEntity.setPageNo(pageNo2);
                mTaskItemRequestEntity.setPageSize(10);
                getExpressListData();
                refreshlayout.finishLoadmore();
            }
        });
        //自提出发刷新
        selfRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshSelfListData();
                refreshlayout.finishRefresh();
            }
        });
        selfRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNo3++;
                mTaskItemRequestEntity.setPageNo(pageNo3);
                mTaskItemRequestEntity.setPageSize(10);
                getSelfListData();
                refreshlayout.finishLoadmore();
            }
        });
    }

    //自提刷新
    private void refreshSelfListData() {
        mTaskSelfRequestEntity.setPageNo(JzspConstants.PageStart);
        mTaskSelfRequestEntity.setPageSize(JzspConstants.PageSize);
        selfRefreshLayout.setEnableLoadmore(true);
        getSelfListData();
    }

    //快递刷新
    private void refreshExpressListData() {
        mTaskExpressRequestEntity.setPageNo(JzspConstants.PageStart);
        mTaskExpressRequestEntity.setPageSize(JzspConstants.PageSize);
        expressRefreshLayout.setEnableLoadmore(true);
        getExpressListData();
    }

    //
    private void refreshListData() {
        mTaskItemRequestEntity.setPageNo(JzspConstants.PageStart);
        mTaskItemRequestEntity.setPageSize(JzspConstants.PageSize);
        refreshLayout.setEnableLoadmore(true);
        getListData();
    }


    //物流配送网络请求
    private void getListData() {
        JSZPOkgoHttpUtils.<TaskItemRequestEntity>postString(JSZPUrls.URL_GET_TASKITEM,
                this, mTaskItemRequestEntity,
                mHandler, GET_LOGISTICS_WHAT, TaskItemResultEntity.class);
    }

    //快递配送网络请求
    private void getExpressListData() {
        JSZPOkgoHttpUtils.<TaskExpressResultEntity>postString(JSZPUrls.URL_POST_EXPRESS_GO,
                SendCheckActivity.this, mTaskExpressRequestEntity,
                mHandler, GET_EXPRESS_WHAT, TaskExpressResultEntity.class);
    }

    //自提配送网络请求
    private void getSelfListData() {
        JSZPOkgoHttpUtils.<TaskSelfResultEntity>postString(JSZPUrls.URL_POST_SELF_GO,
                SendCheckActivity.this, mTaskSelfRequestEntity,
                mHandler, GET_SELF_WHAT, TaskSelfResultEntity.class);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        if (postion == 0) {
            if (sendCheckAdapter.isMulti_select()) {
                tvRight.setText("多选");
                sendCheckAdapter.setMulti_select(false);
                tvSure.setVisibility(View.GONE);
            } else {
                tvRight.setText("取消");
                sendCheckAdapter.setMulti_select(true);
                tvSure.setVisibility(View.VISIBLE);
            }
            sendCheckAdapter.notifyDataSetChanged();
        } else if (postion == 1) {
            if (mSendExpressAdapter.isMulti_select()) {
                tvRight.setText("多选");
                mSendExpressAdapter.setMulti_select(false);
                tvSure.setVisibility(View.GONE);
            } else {
                tvRight.setText("取消");
                mSendExpressAdapter.setMulti_select(true);
                tvSure.setVisibility(View.VISIBLE);
            }
            mSendExpressAdapter.notifyDataSetChanged();

        } else if (postion == 2) {
            if (mSendSelfAdapter.isMulti_select()) {
                tvRight.setText("多选");
                mSendSelfAdapter.setMulti_select(false);
                tvSure.setVisibility(View.GONE);
            } else {
                tvRight.setText("取消");
                mSendSelfAdapter.setMulti_select(true);
                tvSure.setVisibility(View.VISIBLE);
            }
            mSendSelfAdapter.notifyDataSetChanged();
        }
    }

    private TaskConfirmRequestEntity mTaskConfirmRequestEntity = new TaskConfirmRequestEntity();

    String taskNo = "";

   private int choseCount=0;
    private int choseCount1=0;
    private int choseCount2=0;
    @OnClick(R.id.tv_sure)
    public void onViewClicked() {//多选发车按钮
        if (0 == postion) {//物流出发
            for (int i = 0; i < logisticsList.size(); i++) {
                if (true == logisticsList.get(i).isSelected()) {
                    taskNo += logisticsList.get(i).getTaskNo() + ",";
                    choseCount++;
                    tvSure.setText("确认出发"+"("+choseCount+")");

                }
            }
            if ("" != taskNo) {
                taskNo = taskNo.substring(0, taskNo.length() - 1);
                mTaskConfirmRequestEntity.setTaskNos(taskNo);
                JSZPOkgoHttpUtils.<TaskItemRequestEntity>postString(JSZPUrls.URL_POST_CONFIRM_GO,
                        this, mTaskConfirmRequestEntity,
                        mHandler, GET_LOGISTICS_SURE_WHAT, TaskConfirmResultEntity.class);
            } else {
                ToastUtils.showToast(this, "未选中发货");
            }

        } else if (1 == postion) {//快递出发
            for (int i = 0; i < expressList.size(); i++) {
                if (true == expressList.get(i).isSelected()) {
                    taskNo += expressList.get(i).getTaskNo() + ",";
                    choseCount1++;
                    tvSure.setText("确认出发"+"("+choseCount1+")");
                }
            }

            if ("" != taskNo) {
                taskNo = taskNo.substring(0, taskNo.length() - 1);
                mTaskConfirmRequestEntity.setTaskNos(taskNo);
                JSZPOkgoHttpUtils.<TaskItemRequestEntity>postString(JSZPUrls.URL_POST_CONFIRM_GO,
                        this, mTaskConfirmRequestEntity,
                        mHandler, GET_EXPRESS_SURE_WHAT, TaskConfirmResultEntity.class);
            } else {
                ToastUtils.showToast(this, "未选中发货");
            }

        } else if (2 == postion) {//自提出发
            for (int i = 0; i < selfList.size(); i++) {
                if (true == selfList.get(i).isSelected()) {
                    taskNo += selfList.get(i).getTaskNo() + ",";
                    choseCount2++;
                    tvSure.setText("确认出发"+"("+choseCount2+")");
                }
            }

            if ("" != taskNo) {
                taskNo = taskNo.substring(0, taskNo.length() - 1);
                mTaskConfirmRequestEntity.setTaskNos(taskNo);
                JSZPOkgoHttpUtils.<TaskItemRequestEntity>postString(JSZPUrls.URL_POST_CONFIRM_GO,
                        this, mTaskConfirmRequestEntity,
                        mHandler, GET_SELF_SURE_WHAT, TaskConfirmResultEntity.class);
            } else {
                ToastUtils.showToast(this, "未选中发货");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 401:
                    //确认成功
                    refreshListData();
                    break;
                case 402:
                    refreshExpressListData();
                    break;
                case 403:
                    refreshSelfListData();
                    break;

            }
        }
    }
}
