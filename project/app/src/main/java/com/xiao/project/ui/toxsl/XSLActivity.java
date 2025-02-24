package com.xiao.project.ui.toxsl;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xiao.project.R;
import com.xiao.project.bean.StudentXsl;
import com.xiao.project.utils.ExcelUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XSLActivity extends AppCompatActivity {

    @BindView(R.id.bt_create_xsl)
    Button btCreateXsl;
    private ArrayList<StudentXsl> students;
    private ArrayList<ArrayList<String>> recordList;
    private static String[] title = { "编号","姓名","性别","年龄","班级","数学","英语","语文" };
    private File file;
    private String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xsl);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //模拟数据集合
        students = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            students.add(new StudentXsl("小红"+i,"女","12","1"+i,"一班","85","77","98"));
            students.add(new StudentXsl("小明"+i,"男","14","2"+i,"二班","65","57","100"));
        }
    }
    /**
     * 导出excel
     */
    public void exportExcel() {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
        ExcelUtils.initExcel(file.toString() + "/成绩表.xls", title);
        fileName = getSDPath() + "/Record/成绩表.xls";
        ExcelUtils.writeObjListToExcel(getRecordData(), fileName, this);
    }
    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     * @return
     */
    private  ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i <students.size(); i++) {
            StudentXsl student = students.get(i);
            ArrayList<String> beanList = new ArrayList<String>();
            beanList.add(student.id);
            beanList.add(student.name);
            beanList.add(student.sex);
            beanList.add(student.age);
            beanList.add(student.classNo);
            beanList.add(student.math);
            beanList.add(student.english);
            beanList.add(student.chinese);
            recordList.add(beanList);
        }
        return recordList;
    }

    private  String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }
    public  void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    @OnClick({R.id.bt_create_xsl})
    public void onClickedView(View view){
        switch (view.getId()){
            case R.id.bt_create_xsl:
                exportExcel();
                break;
        }
    }

}
