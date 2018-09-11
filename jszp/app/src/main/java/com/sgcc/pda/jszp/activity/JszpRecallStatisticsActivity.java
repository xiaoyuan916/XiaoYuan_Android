package com.sgcc.pda.jszp.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JszpBoxStatisticsAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.JszpBoxRecallDetEntity;
import com.sgcc.pda.jszp.util.AppManager;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class JszpRecallStatisticsActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_recall_box_head)
    RelativeLayout rlRecallBoxHead;
    @BindView(R.id.ll_box_recall_success)
    LinearLayout llBoxRecallSuccess;
    @BindView(R.id.tv_recall_box_date)
    TextView tvRecallBoxDate;
    @BindView(R.id.tv_recall_box_count)
    TextView tvRecallBoxCount;
    @BindView(R.id.tv_head_box_recall_count)
    TextView tvHeadBoxRecallCount;
    @BindView(R.id.tv_box_recall_go_on)
    TextView tvBoxRecallGoOn;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    /**
     * box分类的HashMap
     */
    private HashMap<String, List<JszpBoxRecallDetEntity>> mHashMapBox = new HashMap();
    /**
     * 适配器
     */
    private JszpBoxStatisticsAdapter boxStatisticsAdapter;
    private String mBoxCount;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_jszp_recall_statistics;
    }

    @Override
    public void initView() {
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new SpaceItemDecoration(5));
        boxStatisticsAdapter = new JszpBoxStatisticsAdapter();
        rv.setAdapter(boxStatisticsAdapter);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        //初始化头部布局
        initHeadUI(intent);

        Serializable serializable = intent.getSerializableExtra("mTurnoverBoxRecallDets");
        if ((serializable != null) && (serializable instanceof List)) {
            List<JszpBoxRecallDetEntity> mTurnoverBoxRecallDets = (List<JszpBoxRecallDetEntity>) serializable;
            //统计当前物料
            initBoxsData(mTurnoverBoxRecallDets);
        }
    }

    private void initHeadUI(Intent intent) {
        if (intent.getBooleanExtra("isSubmitRecall", false)) {
            rlRecallBoxHead.setVisibility(View.GONE);
            llBoxRecallSuccess.setVisibility(View.VISIBLE);
            tvTitle.setText("周转箱召回");
            ivReturn.setBackgroundResource(R.mipmap.return_bai_h);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,
                    30);//两个400分别为添加图片的大小
            ivReturn.setLayoutParams(params);
            tvTitle.setTextColor(0xff292929);
            tvBoxRecallGoOn.setVisibility(View.VISIBLE);
            rlHead.setBackgroundResource(R.color.white);
        } else {
            rlRecallBoxHead.setVisibility(View.VISIBLE);
            llBoxRecallSuccess.setVisibility(View.GONE);
            tvTitle.setText("统计");
            tvTitle.setTextColor(0Xffffffff);
            tvBoxRecallGoOn.setVisibility(View.GONE);
            rlHead.setBackgroundResource(R.color.title_green);
        }
        mBoxCount = intent.getStringExtra("mBoxCount");
        tvHeadBoxRecallCount.setText(mBoxCount+ "箱");
        tvRecallBoxCount.setText(mBoxCount);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        tvRecallBoxDate.setText(format.format(new Date()));
    }

    /**
     * 初始化统计信息
     *
     * @param mTurnoverBoxRecallDets
     */
    private void initBoxsData(List<JszpBoxRecallDetEntity> mTurnoverBoxRecallDets) {
        //分类算法
        for (JszpBoxRecallDetEntity box : mTurnoverBoxRecallDets) {
            List<JszpBoxRecallDetEntity> boxlist = mHashMapBox.get(box.getSortCode());
            if (boxlist == null) {
                boxlist = new ArrayList<>();
                boxlist.add(box);
                mHashMapBox.put(box.getSortCode(), boxlist);
            } else {
                boxlist.add(box);
            }
        }
        //再次聚合展示
        List<JszpBoxRecallDetEntity> mList = new ArrayList<>();
        for (Map.Entry<String, List<JszpBoxRecallDetEntity>> entry : mHashMapBox.entrySet()) {
            JszpBoxRecallDetEntity jszpBoxRecallDetEntity = entry.getValue().get(0);
            jszpBoxRecallDetEntity.setSumQty(entry.getValue().size());
            mList.add(jszpBoxRecallDetEntity);
        }
        //更新UI
        updaUI(mList);
    }

    private void updaUI(List<JszpBoxRecallDetEntity> mList) {
        boxStatisticsAdapter.updataUI(mList);
    }

    @Override
    public void initListener() {

    }
    @OnClick({R.id.tv_box_recall_go_on,R.id.iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_box_recall_go_on:
                AppManager.getAppManager().finishActivity(JszpTurnoverBoxRecallActivity.class);
                AppManager.getAppManager().finishActivity(JszpBoxsManualActivity.class);
                Intent intent2 = new Intent(this, JszpTurnoverBoxScanActivity.class);
//                intent2.putExtra("mBoxCount",mBoxCount);
                startActivity(intent2);
                finish();
                break;
            case R.id.iv_return:
                if (!getIntent().getBooleanExtra("isSubmitRecall", false)){
                    onBackPressed();
                    return;
                }
                AppManager.getAppManager().finishActivity(JszpTurnoverBoxRecallActivity.class);
                AppManager.getAppManager().finishActivity(JszpTurnoverBoxScanActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!getIntent().getBooleanExtra("isSubmitRecall", false)){
            return;
        }
        AppManager.getAppManager().finishActivity(JszpTurnoverBoxRecallActivity.class);
        AppManager.getAppManager().finishActivity(JszpTurnoverBoxScanActivity.class);
        AppManager.getAppManager().finishActivity(JszpBoxsManualActivity.class);
    }
}
