package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.DeviceOutAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.DeviceOutSubTaskItem;
import com.sgcc.pda.jszp.bean.DeviceOutTaskItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DeviceOutActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_out_tasks)
    RecyclerView rvOutTasks;

    BaseItemAdapter outtaskAdapter;
    List<DeviceOutTaskItem> outTaskItems;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_out;
    }

    @Override
    public void initView() {
        tvTitle.setText("设备出库");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.search_hui_small);
        outtaskAdapter = new BaseItemAdapter();
        rvOutTasks.setLayoutManager(new LinearLayoutManager(this));
        outTaskItems = new ArrayList<>();
        outTaskItems.add(new DeviceOutTaskItem("201805221234567890"));
        outTaskItems.add(new DeviceOutTaskItem("201805221234567890"));
        outTaskItems.add(new DeviceOutTaskItem("201805221234567890"));
        outTaskItems.add(new DeviceOutTaskItem("201805221234567890"));
        outTaskItems.get(0).addSubTask(new DeviceOutSubTaskItem("201805221234567890", "江苏供电公司", 300, 40,
                "2级单相远程费控智能电能表 (载波/远程/开关内置)", 0, 0, true));
        outTaskItems.get(0).addSubTask(new DeviceOutSubTaskItem("201805221234567890", "江苏供电公司", 300, 40,
                "2级单相远程费控智能电能表 (载波/远程/开关内置)", 1, 1, false));
        outTaskItems.get(0).addSubTask(new DeviceOutSubTaskItem("201805221234567890", "江苏供电公司", 300, 40,
                "2级单相远程费控智能电能表 (载波/远程/开关内置)", 2, 2, false));
        outTaskItems.get(0).addSubTask(new DeviceOutSubTaskItem("201805221234567890", "江苏供电公司", 300, 40,
                "2级单相远程费控智能电能表 (载波/远程/开关内置)", 3, 3, false));
        outTaskItems.get(0).addSubTask(new DeviceOutSubTaskItem("201805221234567890", "上海供电公司", 300, 40,
                "2级单相远程费控智能电能表 (载波/远程/开关内置)", 4, 0, true));
        outTaskItems.get(0).addSubTask(new DeviceOutSubTaskItem("201805221234567890", "江苏供电公司", 300, 40,
                "2级单相远程费控智能电能表 (载波/远程/开关内置)", 1, 0, false));
        outtaskAdapter.register(DeviceOutTaskItem.class, new DeviceOutAdapter<DeviceOutTaskItem>(this));
        rvOutTasks.addItemDecoration(new SpaceItemDecoration(1));

        rvOutTasks.setAdapter(outtaskAdapter);
        outtaskAdapter.setDataItems(outTaskItems);



    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onIvRightClick(View v) {
        super.onIvRightClick(v);
        Intent intent = new Intent(this, ManualActivity.class);
        intent.putExtra("type", MyComment.SCAN_DEVICE_OUT);
        intent.putExtra("sub_type", -1);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;


    }
}
