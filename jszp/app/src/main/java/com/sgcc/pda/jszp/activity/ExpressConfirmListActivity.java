package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.ExpressConfirmAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.adapter.sendCheckAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DriverItem;
import com.sgcc.pda.jszp.bean.TaskItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 快递确认
 */
public class ExpressConfirmListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv)
    RecyclerView rvExpress;

    BaseItemAdapter baseItemAdapter;
    ExpressConfirmAdapter<TaskItem> expressConfirmAdapter;
    ArrayList<TaskItem> expressConfirmItems;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView() {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.search_bai);

        //初始化列表
        rvExpress.setLayoutManager(new LinearLayoutManager(this));
        baseItemAdapter = new BaseItemAdapter();
        expressConfirmItems = new ArrayList<>();
        expressConfirmItems.add(new TaskItem("201805221234567890", "", 0, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        expressConfirmItems.add(new TaskItem("201805221234567890", "", 0, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        expressConfirmItems.add(new TaskItem("201805221234567890", "", 1, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        expressConfirmItems.add(new TaskItem("201805221234567890", "", 1, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        expressConfirmItems.add(new TaskItem("201805221234567890", "", 1, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        expressConfirmAdapter = new ExpressConfirmAdapter(this);
        baseItemAdapter.register(TaskItem.class, expressConfirmAdapter);
        rvExpress.addItemDecoration(new SpaceItemDecoration(1));
        rvExpress.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(expressConfirmItems);
        baseItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent intent=new Intent(ExpressConfirmListActivity.this,ExpressConfirmActivity.class);
                intent.putExtra("task",((TaskItem)baseViewHolder.getItemData()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        tvTitle.setText("快递确认");
    }

    @Override
    public void initListener() {

    }
}
