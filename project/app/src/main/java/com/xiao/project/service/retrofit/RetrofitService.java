package com.xiao.project.service.retrofit;
import com.xiao.project.bean.Poetry;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    //get请求接口
    @GET("recommendPoetry")    //指定请求类型的注解
    Call<Poetry> getPoetry();   //获取古诗的方法
}
