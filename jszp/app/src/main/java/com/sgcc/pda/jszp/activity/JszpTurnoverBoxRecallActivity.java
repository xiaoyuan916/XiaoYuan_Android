package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 周转箱召回
 */
public class JszpTurnoverBoxRecallActivity extends BaseActivity {

    @BindView(R.id.iv_statistics)
    ImageView iv_statistics;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jszp_turnover_box_recall;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
    @OnClick({R.id.iv_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_statistics:
                Intent intent = new Intent(this, JszpRecallStatisticsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
