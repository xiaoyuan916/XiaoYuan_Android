package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.ExpressConfirmAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.ExpressDistAutoesItem;
import com.sgcc.pda.jszp.bean.ExpressConfirmListResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 快递确认
 */
public class ExpressConfirmListActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_LIST_WHAT=1001;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv)
    RecyclerView rvExpress;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    BaseItemAdapter baseItemAdapter;
//    ExpressConfirmAdapter<ExpressDistAutoesItem> expressConfirmAdapter;
    ArrayList<ExpressDistAutoesItem> mList;


    private int pageNo = JzspConstants.PageStart;
    ExpressConfirmListResultEntity expressDistAutoesResultEntity;//请求结果集

    private String taskNo,dpName;//查询条件
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_LIST_WHAT:
                    //列表数据
                    if(expressDistAutoesResultEntity == null) {
                        expressDistAutoesResultEntity = (ExpressConfirmListResultEntity) msg.obj;
                        mList.addAll(expressDistAutoesResultEntity.getExpressDistAutoes());
                        //初始化数据
                        initUiData();
                        if(mList.size()<JzspConstants.PageSize){
                            refreshLayout.setEnableLoadmore(false);
                        }
                    }else{
                        if(pageNo == JzspConstants.PageStart){
                            //刷新
                            mList.clear();
                        }
                        List<ExpressDistAutoesItem> items = ((ExpressConfirmListResultEntity) msg.obj).getExpressDistAutoes();
                        mList.addAll(items);
                        baseItemAdapter.notifyDataSetChanged();
                        if(items.size()<JzspConstants.PageSize){
                            refreshLayout.setEnableLoadmore(false);
                        }
                    }
                    break;
            }
        }
    };


    @Override
    public int getLayoutResId() {
        return R.layout.activity_refresh_list;
    }

    @Override
    public void initView() {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.search_bai);

        mList = new ArrayList<>();
    }

    @Override
    public void initData() {
        tvTitle.setText("快递确认");
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
                pageNo++;
                getListData();
                refreshlayout.finishLoadmore();
            }
        });
    }

    //初始化界面数据
    private void initUiData(){
        initListUiData();
    }
    //初始化列表数据
    private void initListUiData(){
        rvExpress.setLayoutManager(new LinearLayoutManager(this));
        baseItemAdapter = new BaseItemAdapter();
        ExpressConfirmAdapter<ExpressDistAutoesItem> expressConfirmAdapter = new ExpressConfirmAdapter(this);
        baseItemAdapter.register(ExpressDistAutoesItem.class, expressConfirmAdapter);
        rvExpress.addItemDecoration(new SpaceItemDecoration(1));
        rvExpress.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(mList);
        baseItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                //这个列表只会有三种状态  已出库 用户确认 已绑定
                //已出库状态在这个列表上就是待确认  点击之后进入快递确认页面
                //用户确认状态在这个列表上就是已确认  点击之后进入快递绑定页面
                //已绑定状态不可点击
                ExpressDistAutoesItem expressDistAutoesItem =   ((ExpressDistAutoesItem)baseViewHolder.getItemData());
                if(JzspConstants.Delivery_Task_Status_yck.equals( expressDistAutoesItem.getTaskStatus())){
                    //待确认
                    Intent intent=new Intent(ExpressConfirmListActivity.this,ExpressConfirmActivity.class);
                    intent.putExtra("taskno",((ExpressDistAutoesItem)baseViewHolder.getItemData()).getTaskNo());
                    startActivityForResult(intent,101);
                }else if(JzspConstants.Delivery_Task_Status_yhqr.equals( expressDistAutoesItem.getTaskStatus())){
                    //已确认
                    Intent intent=new Intent(ExpressConfirmListActivity.this,ExpressBindActivitiy.class);
                    intent.putExtra("taskNo",((ExpressDistAutoesItem)baseViewHolder.getItemData()).getTaskNo());
                    intent.putExtra("distAutoId",((ExpressDistAutoesItem)baseViewHolder.getItemData()).getDistAutoId());
                    startActivityForResult(intent,103);
                }

            }
        });
    }
    private void refreshListData(){
        refreshLayout.setEnableLoadmore(true);
        pageNo =JzspConstants.PageStart;
        getListData();
    }
    /**
     * 获取物流派车列表数据
     */
    private void getListData() {
        Map<String,String> map = new HashMap<>();
        map.put("taskNo",taskNo);
        map.put("dpName",dpName);
        map.put("pageNo",pageNo+"");
        map.put("pageSize",JzspConstants.PageSize+"");
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_WAIT_CONFIRM_EXPRESS_TASK,
                this,map,
                mHandler,GET_LIST_WHAT,ExpressConfirmListResultEntity.class);
    }

    @Override
    public void onIvRightClick(View v) {
        super.onIvRightClick(v);
        Intent intent = new Intent(this,ExpressQueryActivity.class);
        intent.putExtra("dpname",dpName);
        startActivityForResult(intent,102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 102:
                    //查询
                    dpName = data.getStringExtra("dpname");
                    refreshListData();
                    break;
                case 101:
                    //确认成功
                    refreshListData();
                    break;
                case 103:
                    //绑定成功
                    refreshListData();
                    break;
            }
        }
    }
}
