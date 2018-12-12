package com.xiao.tensorflow.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xiao.tensorflow.MainActivity;
import com.xiao.tensorflow.R;
import com.xiao.tensorflow.classifier.Classifier;
import com.xiao.tensorflow.classifier.TensorFlowImageClassifier;
import com.xiao.tensorflow.utils.FileUtil;
import com.xiao.tensorflow.utils.GlideApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TensorFlowPhotoDistinguishActivity extends AppCompatActivity {

    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.tv_classifier_info)
    TextView tvClassifierInfo;
    @BindView(R.id.iv_choose_picture)
    ImageView ivChoosePicture;
    @BindView(R.id.iv_take_photo)
    ImageView ivTakePhoto;

    public static final String TAG = TensorFlowPhotoDistinguishActivity.class.getSimpleName();

    private static final int OPEN_SETTING_REQUEST_COED = 110;
    private static final int TAKE_PHOTO_REQUEST_CODE = 120;
    private static final int PICTURE_REQUEST_CODE = 911;
    private static final int PERMISSIONS_REQUEST = 108;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 119;
    private static final String CURRENT_TAKE_PHOTO_URI = "currentTakePhotoUri";
    private static final String PACKAGE_URL_SCHEME = "package:";
    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 117;
    private static final float IMAGE_STD = 1;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";
    private static final String MODEL_FILE = "file:///android_asset/tensorflow_inception_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    private Executor executor;
    private Uri currentTakePhotoUri;
    private Classifier classifier;
    /**
     * 主线程消息队列空闲时（视图第一帧绘制完成时）处理耗时事件
     */
    MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {

            if (classifier == null) {
                // 创建 Classifier
                classifier = TensorFlowImageClassifier.create(TensorFlowPhotoDistinguishActivity.this.getAssets(),
                        MODEL_FILE, LABEL_FILE, INPUT_SIZE, IMAGE_MEAN, IMAGE_STD, INPUT_NAME, OUTPUT_NAME);
            }
            // 初始化线程池
            executor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    thread.setName("ThreadPool-ImageClassifier");
                    return thread;
                }
            });
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tensor_flow_photo_distinguish);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        // 避免耗时任务占用 CPU 时间片造成UI绘制卡顿，提升启动页面加载速度
        Looper.myQueue().addIdleHandler(idleHandler);
    }


    @OnClick({R.id.iv_choose_picture,R.id.iv_take_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_picture:
                choosePicture();
                break;
            case R.id.iv_take_photo:
                takePhoto();
                break;
            default:
                break;
        }
    }

    /**
     * 选择一张图片并裁剪获得一个小图
     */
    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICTURE_REQUEST_CODE);
    }

    /**
     * 使用系统相机拍照
     */
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {
            openSystemCamera();
        }
    }

    /**
     * 打开系统相机
     */
    private void openSystemCamera() {
        //调用系统相机
        Intent takePhotoIntent = new Intent();
        takePhotoIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        //这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
        if (takePhotoIntent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "当前系统没有可用的相机应用", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = "TF_" + System.currentTimeMillis() + ".jpg";
        File photoFile = new File(FileUtil.getPhotoCacheFolder(), fileName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //通过FileProvider创建一个content类型的Uri
            currentTakePhotoUri = FileProvider.getUriForFile(this, "gdut.bsx.tensorflowtraining.fileprovider", photoFile);
            //对目标应用临时授权该 Uri 所代表的文件
            takePhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            currentTakePhotoUri = Uri.fromFile(photoFile);
        }

        //将拍照结果保存至 outputFile 的Uri中，不保留在相册中
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentTakePhotoUri);
        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICTURE_REQUEST_CODE) {
                // 处理选择的图片
                handleInputPhoto(data.getData());
            } else if (requestCode == OPEN_SETTING_REQUEST_COED){
//                requestMultiplePermissions();
            } else if (requestCode == TAKE_PHOTO_REQUEST_CODE) {
                // 如果拍照成功，加载图片并识别
                handleInputPhoto(currentTakePhotoUri);
            }
        }
    }

    /**
     * 处理图片
     * @param imageUri
     */
    private void handleInputPhoto(Uri imageUri) {
        // 加载图片
        GlideApp.with(TensorFlowPhotoDistinguishActivity.this).asBitmap().listener(new RequestListener<Bitmap>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                Log.d(TAG,"handleInputPhoto onLoadFailed");
                Toast.makeText(TensorFlowPhotoDistinguishActivity.this, "图片加载失败", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                Log.d(TAG,"handleInputPhoto onResourceReady");
                startImageClassifier(resource);
                return false;
            }
        }).load(imageUri).into(ivPicture);

        tvClassifierInfo.setText("Processing...");
    }

    /**
     * 开始图片识别匹配
     * @param bitmap
     */
    private void startImageClassifier(final Bitmap bitmap) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, Thread.currentThread().getName() + " startImageClassifier");
                    Bitmap croppedBitmap = getScaleBitmap(bitmap, INPUT_SIZE);

                    final List<Classifier.Recognition> results = classifier.recognizeImage(croppedBitmap);
                    Log.i(TAG, "startImageClassifier results: " + results);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvClassifierInfo.setText(String.format("results: %s", results));
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, "startImageClassifier getScaleBitmap " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 对图片进行缩放
     * @param bitmap
     * @param size
     * @return
     * @throws IOException
     */
    private static Bitmap getScaleBitmap(Bitmap bitmap, int size) throws IOException {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) size) / width;
        float scaleHeight = ((float) size) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
