package com.xiao.project.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.xiao.project.R;
import com.xiao.project.utils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemGlideActivity extends AppCompatActivity {

    @BindView(R.id.iv_res1)
    ImageView ivRes1;
    @BindView(R.id.iv_res2)
    ImageView ivRes2;
    @BindView(R.id.iv_res3)
    ImageView ivRes3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_glide);
        ButterKnife.bind(this);
        initImageView();
    }

    private void initImageView() {
//        // 加载本地图片
//        File file = new File(getExternalCacheDir() + "/image.jpg");
//        Glide.with(this).load(file).into(imageView);
//
//        // 加载应用资源
//        int resource = R.drawable.image;
//        Glide.with(this).load(resource).into(imageView);
//
//        // 加载二进制流
//        byte[] image = getImageBytes();
//        Glide.with(this).load(image).into(imageView);
//
//        // 加载Uri对象
//        Uri imageUri = getImageUri();
//        Glide.with(this).load(imageUri).into(imageView);

        GlideUtils.loadAnimationDrawableUtils(ThemGlideActivity.this,
                R.mipmap.ic_launcher,ivRes1);
    }
}
