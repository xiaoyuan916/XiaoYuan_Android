package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JszpOrderDetailsTabPagerAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.JszpOrderRequestEntity;
import com.sgcc.pda.jszp.bean.JszpOrderResultEntity;
import com.sgcc.pda.jszp.bean.JszpQueryOrderResultEntity;
import com.sgcc.pda.jszp.fragment.JszpOrderDetailsFragment;
import com.sgcc.pda.jszp.fragment.JszpOrderStatusFragment;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JszpDateUtils;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class JszpOrderDetailsActivity extends BaseActivity {
    private static final int GET_DIST_APP_WHAT = 2101;
    /**
     * 控件
     */
    @BindView(R.id.tb_order_details)
    TabLayout tb_order_details;
    @BindView(R.id.vp_order_details)
    ViewPager vp_order_details;
    @BindView(R.id.iv_return)
    ImageView iv_return;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_query_order_state)
    ImageView iv_query_order_state;
    @BindView(R.id.tv_query_order_number)
    TextView tv_query_order_number;
    @BindView(R.id.tv_query_order_qty)
    TextView tv_query_order_qty;
    @BindView(R.id.tv_query_order_address)
    TextView tv_query_order_address;
    @BindView(R.id.tv_query_order_check)
    TextView tv_query_order_check;
    @BindView(R.id.tv_order_distribution_warehouse)
    TextView tv_order_distribution_warehouse;
    @BindView(R.id.tv_order_presentation_date)
    TextView tv_order_presentation_date;
    @BindView(R.id.tv_order_residual_time)
    TextView tv_order_residual_time;

    /**
     * fragment及列表
     */
    private List<Fragment> mList = new ArrayList<>();
    /**
     * 两个状态和详情的fragmen
     */
    private JszpOrderDetailsFragment mDetailsFragment;
    private JszpOrderStatusFragment mStatusFragment;
    /**
     * 关联的adapter
     */
    private JszpOrderDetailsTabPagerAdapter mTabPagerAdapter;
    /**
     * hanlder 处理各种UI和逻辑
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_DIST_APP_WHAT:
                    JszpOrderResultEntity obj = (JszpOrderResultEntity) msg.obj;
                    //更新UI
                    updataUI(obj);
                    break;
            }
        }
    };


    /**
     * 更新UI
     * @param obj
     */
    private void updataUI(JszpOrderResultEntity obj) {
        updataTopUI(obj);
        updataFragmentUI(obj);
    }

    /**
     * 更新fragment的ui
     * @param obj
     */
    private void updataFragmentUI(JszpOrderResultEntity obj) {
        mStatusFragment.updataUI(obj.getDistApp().getTracks());
        mDetailsFragment.updataUI(obj.getDistApp());
        mTabPagerAdapter.notifyDataSetChanged();
    }

    /**
     * 更新头布局的UI
     * @param obj
     */
    private void updataTopUI(JszpOrderResultEntity obj) {
        if (obj == null||obj.getDistApp()==null) return;
        switch (obj.getDistApp().getSgAppType()) {
            case JzspConstants.SG_APP_TYPE_EXPANSION_INDUSTRY:
                iv_query_order_state.setBackgroundResource(R.mipmap.jszp_expansion_industry);
                break;
            case JzspConstants.SG_APP_TYPE_ENGINEERING:
                iv_query_order_state.setBackgroundResource(R.mipmap.jszp_engineering);
                break;
            case JzspConstants.SG_APP_TYPE_MAINTAIN:
                iv_query_order_state.setBackgroundResource(R.mipmap.jszp_maintain);
                break;
        }
        tv_query_order_number.setText("订单编号：" + obj.getDistApp().getAppNo());
        tv_query_order_address.setText(obj.getDistApp().getHandleOrgNo());
        tv_query_order_check.setText(obj.getDistApp().getAppStatus());
        tv_order_distribution_warehouse.setText(obj.getDistApp().getWhNo());
        tv_order_presentation_date.setText(obj.getDistApp().getAppDate());
        //剩余时间
        tv_order_residual_time.setText(JszpDateUtils.getResidualTime(obj.getDistApp().getAppPlanDate()));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jszp_order_details;
    }

    @Override
    public void initView() {
        tv_title.setText("查询详情");
        mStatusFragment = new JszpOrderStatusFragment();
        mDetailsFragment = new JszpOrderDetailsFragment();
        mList.add(mStatusFragment);
        mList.add(mDetailsFragment);
        mTabPagerAdapter = new JszpOrderDetailsTabPagerAdapter(getSupportFragmentManager(), mList);
        vp_order_details.setAdapter(mTabPagerAdapter);
        tb_order_details.setupWithViewPager(vp_order_details);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String appNo = intent.getStringExtra("appNo");
        if (!TextUtils.isEmpty(appNo)) {
            obtainData(appNo);
        }
    }

    /**
     * 获取订单数据
     *
     * @param appNo
     */
    private void obtainData(String appNo) {
        JszpOrderRequestEntity requestEntity = new JszpOrderRequestEntity();
        requestEntity.setAppNo(appNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_DIST_APP,
                this, requestEntity,
                mHandler, GET_DIST_APP_WHAT, JszpOrderResultEntity.class);

    }

    @Override
    public void initListener() {

    }

    /**
     * view的点击事件
     * @param view
     */
    @OnClick({R.id.iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_return:
                onBackPressed();
                break;
        }
    }
}
