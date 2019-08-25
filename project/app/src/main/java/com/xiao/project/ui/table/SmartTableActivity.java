package com.xiao.project.ui.table;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bin.david.form.core.SmartTable;
import com.xiao.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmartTableActivity extends AppCompatActivity {

    @BindView(R.id.table)
    SmartTable table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_table);
        ButterKnife.bind(this);
    }


}
