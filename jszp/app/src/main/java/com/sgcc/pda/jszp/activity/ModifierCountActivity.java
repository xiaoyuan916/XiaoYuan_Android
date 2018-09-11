package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;

public class ModifierCountActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.et_real_count)
    EditText etRealCount;



    @Override
    public int getLayoutResId() {
        return R.layout.activity_modifier_count;
    }

    @Override
    public void initView() {
        tvTitle.setText("返回数量修改");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("确定");
        int count=getIntent().getIntExtra("plancount",0);
        int realcount = getIntent().getIntExtra("realcount",0);
        tvCount.setText(count+"只");
//        if(realcount!=0) {
//            etRealCount.setText(realcount + "");
//        }
        etRealCount.setSelection(etRealCount.getText().length());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void returnback(View v) {
        setResult(RESULT_CANCELED);
        super.returnback(v);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent data=new Intent();
        int realcount=0;
        try {
             realcount=Integer.parseInt(etRealCount.getText().toString());
             data.putExtra("realcount",realcount);
             setResult(RESULT_OK,data);
        } catch (NumberFormatException e) {
            setResult(RESULT_OK, data);
            e.printStackTrace();
        }

        finish();
    }
}
