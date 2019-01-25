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
