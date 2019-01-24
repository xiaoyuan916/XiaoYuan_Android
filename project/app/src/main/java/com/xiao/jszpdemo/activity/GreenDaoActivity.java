package com.xiao.jszpdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xiao.jszpdemo.MainActivity;
import com.xiao.jszpdemo.R;
import com.xiao.jszpdemo.bean.Student;
import com.xiao.jszpdemo.db.StudentDaoOpe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreenDaoActivity extends AppCompatActivity {

    @BindView(R.id.bt_greendao_insert)
    Button btGreendaoInsert;
    @BindView(R.id.bt_greendao_query)
    Button btGreendaoQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_greendao_insert, R.id.bt_greendao_query})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_greendao_insert:
                Student student = new Student();
                student.setAge(12);
                student.setName("zhangsan");
                student.setNum("001");
                StudentDaoOpe.insertData(this, student);
                break;
            case R.id.bt_greendao_query:
                List<Student> students = StudentDaoOpe.queryAll(this);
                for (Student student1 : students) {
                    Log.d(getClass().getSimpleName(), student1.toString());
                }
                break;
        }
    }
}
