package com.sgcc.pda.sdk.ac;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

import com.sgcc.pda.sdk.dao.SdkDaoFactory;

/**
 * Created by xuzl on 2016/12/8.
 * 异常信息展示
 */
public class ExceptionShowUI extends BaseUI {
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = new ListView(this);
        setContentView(listView);
        cursor = SdkDaoFactory.getExceptionDao(this).queryAllException();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"ctime", "detail"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
        super.onDestroy();
    }

}
