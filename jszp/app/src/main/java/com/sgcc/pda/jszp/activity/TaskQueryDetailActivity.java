package com.sgcc.pda.jszp.activity;

import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;

/**
 * author:赵锦
 * date:2018/9/4 0004 15:12
 *
 * 出库查询 详情
 */
public class TaskQueryDetailActivity extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_task_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText("出库详情");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
