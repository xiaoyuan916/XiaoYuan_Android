package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.LogisticSendChooseDriveAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.AutoDocsItem;
import com.sgcc.pda.jszp.bean.LogisticsAutoDocsEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 物流派车  选择车牌号  司机姓名
 */
public class LogisticSendChooseDriveOptionActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_LIST_WHAT=1001;

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.rv_options)
    RecyclerView rvOptions;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    BaseItemAdapter baseItemAdapter;
    List<AutoDocsItem> optionBeans;

    //分页
    int pageNo = JzspConstants.PageStart;
    private String whNo,autoType;

    //结果实体
    private LogisticsAutoDocsEntity logisticsAutoDocsEntity;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_LIST_WHAT:
                    //列表数据
                    //接口
                    if(logisticsAutoDocsEntity == null) {
                        logisticsAutoDocsEntity = (LogisticsAutoDocsEntity) msg.obj;
                        optionBeans = logisticsAutoDocsEntity.getAutoDocs();
                        //初始化数据
                        initUiData();
                        if(optionBeans.size()<JzspConstants.PageSize){
                            refreshLayout.setEnableLoadmore(false);
                        }
                    }else{
                        if(pageNo == JzspConstants.PageStart){
                            //刷新
                            optionBeans.clear();
                        }
                        List<AutoDocsItem> items = ((LogisticsAutoDocsEntity) msg.obj).getAutoDocs();
                        optionBeans.addAll(items);
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
        return R.layout.activity_logistic_send_choose_drive;
    }

    @Override
    public void initView() {
        whNo= getIntent().getStringExtra("whNo");
        autoType= getIntent().getStringExtra("autoType");
    }

    @Override
    public void initData() {

        refreshListData();
    }

    @Override
    public void initListener() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(keyEvent!=null&&keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    refreshListData();
                    return true;
                }
                return false;
            }
        });

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

    @OnClick({R.id.tv_add,R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                //新增
                Intent intent = new Intent(this, AddDriveActivity.class);
                intent.putExtra("whNo",whNo);
                intent.putExtra("autoType",autoType);
                startActivityForResult(intent,101);
                break;
            case R.id.tv_cancel:
                //取消
                finish();
                break;
        }
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
        map.put("orgNo",JzspConstants.OrgNo);//组织机构   用户登录返回
        map.put("pageNo",pageNo+"");
        map.put("pageSize",JzspConstants.PageSize +"");
        map.put("auto",et_search.getText().toString());//搜索关键字

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_DRIVER_LIST,
                this,map,
                mHandler,GET_LIST_WHAT,LogisticsAutoDocsEntity.class);
    }

    //初始化界面数据
    private void initUiData(){
        initListUiData();
    }


    //获取列表数据
    private void initListUiData(){
        rvOptions.setLayoutManager(new LinearLayoutManager(this));
        baseItemAdapter = new BaseItemAdapter();
        baseItemAdapter.register(AutoDocsItem.class, new LogisticSendChooseDriveAdapter<AutoDocsItem>(this));
        rvOptions.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(optionBeans);
        rvOptions.addItemDecoration(new SpaceItemDecoration(1));
        baseItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent data = new Intent();
                data.putExtra("driver", optionBeans.get(baseViewHolder.getItemPosition()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){}
        switch (requestCode){
            case 101:
                //新增车辆
                refreshListData();
                break;
        }
    }
}
