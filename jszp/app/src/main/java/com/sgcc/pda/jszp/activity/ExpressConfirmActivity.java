package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快递确认
 */
public class ExpressConfirmActivity extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_express_confirm;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        tvTitle.setText("快递确认");
    }

    @Override
    public void initListener() {

    }
    @OnClick({R.id.tv_confirm})
   void  OnClick(View view){
        switch (view.getId()){
            case R.id.tv_confirm:
                Intent intent = new Intent(this,ExpressBindActivitiy.class);
                startActivity(intent);
                break;
        }
    }
}
