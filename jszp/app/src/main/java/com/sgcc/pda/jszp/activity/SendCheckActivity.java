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
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.adapter.sendCheckAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DriverItem;
import com.sgcc.pda.jszp.bean.TaskItem;

import java.util.ArrayList;

import butterknife.BindView;

public class SendCheckActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rv_sendtasks)
    RecyclerView rvSendtasks;

    BaseItemAdapter sendAdapter;
    ArrayList<TaskItem> SendItems;
    sendCheckAdapter<TaskItem> sendcheckAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_send_check;
    }

    @Override
    public void initView() {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("多选");
        tvTitle.setText(R.string.ps5);

        rvSendtasks.setLayoutManager(new LinearLayoutManager(this));
        sendAdapter = new BaseItemAdapter();
        SendItems = new ArrayList<>();
        SendItems.add(new TaskItem("201805221234567890", "", 0, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        SendItems.add(new TaskItem("201805221234567890", "", 0, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        SendItems.add(new TaskItem("201805221234567890", "", 1, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        SendItems.add(new TaskItem("201805221234567890", "", 1, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        SendItems.add(new TaskItem("201805221234567890", "", 1, new DriverItem("李文泽", "豫A86F2C", "15638749576")));
        sendcheckAdapter = new sendCheckAdapter(this);
        sendAdapter.register(TaskItem.class, sendcheckAdapter);
        rvSendtasks.addItemDecoration(new SpaceItemDecoration(1));
        rvSendtasks.setAdapter(sendAdapter);
        sendAdapter.setDataItems(SendItems);
        sendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent intent=new Intent(SendCheckActivity.this,SendCheckDetailActivity.class);
                intent.putExtra("task",((TaskItem)baseViewHolder.getItemData()));
                startActivity(intent);
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
        if (sendcheckAdapter.isMulti_select()) {
            tvRight.setText("多选");
            sendcheckAdapter.setMulti_select(false);
        } else {
            tvRight.setText("取消");
            sendcheckAdapter.setMulti_select(true);
        }
        sendAdapter.notifyDataSetChanged();
    }
}
