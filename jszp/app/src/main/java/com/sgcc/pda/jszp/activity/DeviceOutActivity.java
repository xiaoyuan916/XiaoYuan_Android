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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.DeviceOutAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DeviceOutListResultEntity;
import com.sgcc.pda.jszp.bean.DeviceOutTaskItem;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 平库出库
 */
public class DeviceOutActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_LIST_WHAT=1001;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv)
    RecyclerView rvOutTasks;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    BaseItemAdapter baseItemAdapter;
    List<DeviceOutTaskItem> mList;
    DeviceOutListResultEntity deviceOutListResultEntity;

    //分页
    int pageNo = JzspConstants.PageStart;
    private String taskNo;//任务编号

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_LIST_WHAT:
                    //列表数据
                    //接口
                    if(deviceOutListResultEntity == null) {
                        deviceOutListResultEntity = (DeviceOutListResultEntity) msg.obj;
                        mList = deviceOutListResultEntity.getDistTasks();
                        //初始化数据
                        initListUiData();
                        if(mList.size()<JzspConstants.PageSize){
                            refreshLayout.setEnableLoadmore(false);
                        }
                    }else{
                        if(pageNo == JzspConstants.PageStart){
                            //刷新
                            mList.clear();
                        }
                        List<DeviceOutTaskItem> items = ((DeviceOutListResultEntity) msg.obj).getDistTasks();
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
        tvTitle.setText("平库出库");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.search_bai);
    }

    @Override
    public void initData() {
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

    //刷新数据
    private void refreshListData(){
        refreshLayout.setEnableLoadmore(true);
        pageNo = JzspConstants.PageStart;
        getListData();
    }
    /**
     * 获取物流派车列表数据
     */
    private void getListData() {
        Map<String,String> map = new HashMap<>();
        map.put("taskNo",taskNo);//配送任务编号
        map.put("pageNo",pageNo+"");
        map.put("pageSize",JzspConstants.PageSize +"");

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_QUERY_POSITIVE_OUT_PLAN,
                this,map,
                mHandler,GET_LIST_WHAT,DeviceOutListResultEntity.class);
    }
    //获取列表数据
    private void initListUiData(){
        baseItemAdapter = new BaseItemAdapter();
        rvOutTasks.setLayoutManager(new LinearLayoutManager(this));
        rvOutTasks.addItemDecoration(new SpaceItemDecoration(1));
        DeviceOutAdapter<DeviceOutTaskItem> deviceOutAdapter = new DeviceOutAdapter(this);
        baseItemAdapter.register(DeviceOutTaskItem.class,deviceOutAdapter);
        rvOutTasks.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(mList);
    }
    @Override
    public void onIvRightClick(View v) {
        super.onIvRightClick(v);
        Intent intent = new Intent(this, DeviceOutQueryActivity.class);
        intent.putExtra("taskno",taskNo);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode){
            case 100:
                //查询条件
                taskNo = data.getStringExtra("taskno");
                refreshListData();
                break;
        }

    }
}
