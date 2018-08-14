package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.OptionAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.OptionItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PickOptionActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rv_options)
    RecyclerView rvOptions;

    BaseItemAdapter optionAdapter;
    List<OptionItem> optionBeans;
    private int selected = -1;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_pick_option;
    }

    @Override
    public void initView() {
        tvTitle.setText("类别");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        rvOptions.setLayoutManager(new LinearLayoutManager(this));
        optionAdapter = new BaseItemAdapter();
        optionAdapter.register(OptionItem.class, new OptionAdapter<OptionItem>(this));
        optionBeans = new ArrayList<>();
        optionBeans.add(new OptionItem("类型1"));
        optionBeans.add(new OptionItem("类型2"));
        optionBeans.add(new OptionItem("类型3"));
        optionBeans.add(new OptionItem("类型4"));
        rvOptions.setAdapter(optionAdapter);
        optionAdapter.setDataItems(optionBeans);
        rvOptions.addItemDecoration(new SpaceItemDecoration(1));
        optionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                int index = baseViewHolder.getItemPosition();
                if (selected != -1) {
                    optionBeans.get(selected).setSelected(false);
                }
                selected = index;
                optionBeans.get(selected).setSelected(true);
                optionAdapter.notifyDataSetChanged();

            }
        });


    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent data = new Intent();
        if (selected == -1) setResult(RESULT_CANCELED);
        else {
            data.putExtra("option",optionBeans.get(selected).getName());
            setResult(RESULT_OK, data);
        }
        finish();
    }
}
