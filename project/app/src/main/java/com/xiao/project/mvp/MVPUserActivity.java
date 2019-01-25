package com.xiao.project.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiao.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视图层(View)
 * 负责绘制UI元素、与用户进行交互，对应于xml、Activity、Fragment、Adapter
 * 模型层(Model)
 * 负责存储、检索、操纵数据，一般包含网络请求，数据库处理，I/O流。
 * 控制层(Presenter)
 * Presenter是整个MVP体系的控制中心，作为View与Model交互的中间纽带，处理View于Model间的交互和业务逻辑。
 */
public class MVPUserActivity extends AppCompatActivity implements IUserView {

    @BindView(R.id.id_txv)
    TextView idTxv;
    @BindView(R.id.id_edt)
    EditText idEdt;
    @BindView(R.id.first_name_txv)
    TextView firstNameTxv;
    @BindView(R.id.first_name_edt)
    EditText firstNameEdt;
    @BindView(R.id.last_name_txv)
    TextView lastNameTxv;
    @BindView(R.id.last_name_edt)
    EditText lastNameEdt;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.loadButton)
    Button loadButton;

    private UserPresenter mUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpuser);
        ButterKnife.bind(this);
        mUserPresenter = new UserPresenter(this);
    }


    @OnClick({R.id.saveButton, R.id.loadButton})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                mUserPresenter.saveUser(getID(), getFristName(),
                        getLastName());
                break;
            case R.id.loadButton:
                mUserPresenter.loadUser(getID());
                break;
            default:
                break;
        }
    }

    @Override
    public void setFirstName(String firstName) {
        firstNameEdt.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        lastNameEdt.setText(lastName);
    }

    @Override
    public int getID() {
        return Integer.parseInt(idEdt.getText().toString());
    }

    @Override
    public String getFristName() {
        return firstNameEdt.getText().toString();
    }

    @Override
    public String getLastName() {
        return lastNameEdt.getText().toString();
    }
}
