package com.xiao.project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiao.project.MainActivity;
import com.xiao.project.R;
import com.xiao.project.bean.Poetry;
import com.xiao.project.service.retrofit.RetrofitService;
import com.xiao.project.utils.LogUtils;
import com.xiao.project.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @BindView(R.id.bt_obtain)
    Button btObtain;
    private RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()  //创建Retrofit实例
                .baseUrl("https://api.apiopen.top")    //这里需要传入url的域名部分
                .addConverterFactory(GsonConverterFactory.create()) //返回的数据经过转换工厂转换成我们想要的数据，最常用的就是Gson
                .build();   //构建实例
        service = retrofit.create(RetrofitService.class);   //使用retrofit的create()方法实现请求接口类
    }

    @OnClick({R.id.bt_obtain})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_obtain:
                request();
                break;
        }
    }

    //请求古诗的方法
    private void request() {
        Call<Poetry> call = service.getPoetry();   //通过getPoetry()方法获取Call对象
        call.enqueue(new Callback<Poetry>() {   //call入列发送请求，传入回调Callback，重写成功onRespons()与失败onFailure()方法，并编写相应逻辑
            /**
             * 请求成功
             * */
            @Override
            public void onResponse(Call<Poetry> call, Response<Poetry> response) {  //返回的response中包含了所有信息，其中response.body就是响应主体
                LogUtils.d(response.body().getResult().getContent());
                ToastUtil.show(RetrofitActivity.this,response.body().getResult().getContent());
            }

            /**
             * 请求失败
             * */
            @Override
            public void onFailure(Call<Poetry> call, Throwable t) {
                Log.d(getClass().getSimpleName(), t.getMessage());  //控制台打印报错原因
            }
        });

    }

}
