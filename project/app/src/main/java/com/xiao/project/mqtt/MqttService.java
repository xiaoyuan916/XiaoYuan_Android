package com.xiao.project.mqtt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;


import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;
import org.greenrobot.eventbus.EventBus;

import java.util.Locale;


/**
 * @author Administrator
 * @date 2018/7/26
 * @fileName MqttService
 * @Description 消息推送管理
 * @special 如修改此类请在下方添加备注并告知创建者，谢谢配合！
 * @special change content:
 */
public class MqttService extends Service implements MqttCallback {
    // Debug TAG
    public static final String TAG = "MQTTTAG";
    // Handler Thread ID
    private static final String MQTT_THREAD_NAME = "MqttService[" + TAG + "]";
    // Broker URL or IP Address
    //三种服务质量
    public static final int MQTT_QOS_0 = 0;//至多一次，可能会出现丢包的情况，使用在对实时性要求不高的情况，例如，将此服务质量与通信环境传感器数据一起使用。 对于是否丢失个别读取或是否稍后立即发布新的读取并不重要。
    public static final int MQTT_QOS_1 = 1;//至少一次，保证包会到达目的地，但是可能出现重包。
    public static final int MQTT_QOS_2 = 2;//刚好一次，保证包会到达目的地，且不会出现重包的现象。
    /**
     * 心跳时间，单位秒，每隔固定时间发送心跳包, 心跳间隔不得大于120s
     */
    private static final int MQTT_KEEP_ALIVE = 60000;
    // Topic format for KeepAlives
    private static final String MQTT_KEEP_ALIVE_TOPIC_FORAMT = "/users/%s/keepalive";
    // Keep Alive message to send
    private static final byte[] MQTT_KEEP_ALIVE_MESSAGE = {0};
    // Default Keepalive QOS
    private static final int MQTT_KEEP_ALIVE_QOS = MQTT_QOS_2;

    /**
     * session是否清除，这个需要注意，如果是false，代表保持登录，如果客户端离线了再次登录就可以接收到离
     */
    private static final boolean MQTT_CLEAN_SESSION = false;
    // URL Format normally don't change
    private static final String MQTT_URL_FORMAT = "tcp://%s:%d";
    // Action to start
    private static final String ACTION_START = TAG + ".START";
    // Action to stop
    private static final String ACTION_STOP = TAG + ".STOP";
    // Action to keep alive used by alarm manager
    private static final String ACTION_KEEPALIVE = TAG + ".KEEPALIVE";
    // Action to reconnect
    private static final String ACTION_RECONNECT = TAG + ".RECONNECT";

    // Device ID Format, add any prefix you'd like
    private static final String DEVICE_ID_FORMAT = "ljf_%s";
    // Note: There is a 23 character limit you will get
    // An NPE if you go over that limit
    // Is the Client started?
    private boolean mStarted = false;
    // Device ID, Secure.ANDROID_ID
    private String mDeviceId;
    // Seperate Handler thread for networking
    private Handler mConnHandler;

    /**
     * Connection Options，连接时连接选项
     * 设置心跳时间、超时时间
     */
    private MqttConnectOptions mOpts;
    // Mqtt Client
    private MqttClient mClient;
    // Instance Variable for Keepalive topic
    private MqttTopic mKeepAliveTopic;
    // Defaults to FileStore
    private MqttDefaultFilePersistence mDataStore;
    // On Fail reverts to MemoryStore
    private MemoryPersistence mMemStore;
    // Alarm manager to perform repeating tasks
    private AlarmManager mAlarmManager;
    // To check for connectivity changes
    private ConnectivityManager mConnectivityManager;
    /**
     * 订阅主题
     */
    private String connectTopic = "hbaq_topic";
//    private String connectTopic = "mqtt-test";

    //=============================下面的是向外暴露的方法==================

    /**
     * 开始连接ActiveMQ服务器
     * Start MQTT Client
     *
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionStart(Context ctx) {
        Intent i = new Intent(ctx, MqttService.class);
        i.setAction(ACTION_START);
        ctx.startService(i);
    }

    /**
     * 停止连接ActiveMQ服务器
     * Stop MQTT Client
     *
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionStop(Context ctx) {
        Intent i = new Intent(ctx, MqttService.class);
        i.setAction(ACTION_STOP);
        ctx.startService(i);
    }

    //=============================下面的是服务生命周期的方法==================

    /**
     * 服务初始化回调函数
     * Initalizes the DeviceId and most instance variables
     * Including the Connection Handler, Datastore, Alarm Manager
     * and ConnectivityManager.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mDeviceId="JavaSampleConsumer";
//        mDeviceId = String.format(DEVICE_ID_FORMAT, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
//        mDeviceId = SharepreferenceUtil.getDevsnno(getApplicationContext());
//        mDeviceId= SPUtils.getString(getApplicationContext(), Constants.USER_NAME);
        Log.d(TAG, "mDeviceId = " + mDeviceId);
        HandlerThread thread = new HandlerThread(MQTT_THREAD_NAME);
        thread.start();
        /**创建一个Handler*/
        mConnHandler = new Handler(thread.getLooper());

        try {
            /**新建一个本地临时存储数据的目录，该目录存储将要发送到服务器的数据，直到数据被发送到服务器*/
            mDataStore = new MqttDefaultFilePersistence(getCacheDir().getAbsolutePath());
        } catch (MqttPersistenceException e) {
            /**新建一个内存临时存储数据的目录*/
            mDataStore = null;
            mMemStore = new MemoryPersistence();
        }
        /**连接的参数选项*/
        mOpts = new MqttConnectOptions();
        /**
         * 删除以前的Session，是否是持久化订阅者
         * true：清除缓存，也就是非持久化订阅者；false：服务器保留客户端的连接记录，但此时也不一定是持久化订阅者
         */
        mOpts.setCleanSession(MQTT_CLEAN_SESSION);
        mOpts.setConnectionTimeout(90);
        mOpts.setKeepAliveInterval(90);
//        mOpts.setUserName("SUPERADMIN");
        //因为是消息消费者，不是消息生产者，不往ActiveMQ发送消息，所以new byte[0]就可以了。
        //第二个参数设成false非常重要，否则会源源不断地收到重复的消息。
//        mOpts.setWill(topic, new byte[0], 0, false);

        // Do not set keep alive interval on mOpts we keep track of it with alarm's
        /**定时器用来实现心跳*/
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        /**管理网络连接*/
        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    /**
     * 服务器启动的回调的函数
     * Service onStartCommand
     * Handles the action passed via the Intent
     *
     * @return START_REDELIVER_INTENT
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        String action = intent.getAction();

        Log.d(TAG, "onStartCommand---->Received action of " + action);

        if (action == null) {
            Log.d(TAG, "onStartCommand---->Starting service with no action\n Probably from a crash");
        } else {
            if (action.equals(ACTION_START)) {
                Log.d(TAG, "onStartCommand---->Received ACTION_START");
                /**开始连接*/
                start();
            } else if (action.equals(ACTION_STOP)) {
                /**停止连接*/
                stop();
            } else if (action.equals(ACTION_KEEPALIVE)) {
                /**保持连接*/
                keepAlive();
            } else if (action.equals(ACTION_RECONNECT)) {
                if (isNetworkAvailable()) {
                    /**重新连接*/
                    reconnectIfNecessary();
                }
            }
        }

        return START_REDELIVER_INTENT;
    }

    //=============================下面的三个方法是实现了MqttCallback接口重写的方法==================

    /**
     * 连接失败
     * Connectivity Lost from broker
     */
    @Override
    public void connectionLost(Throwable arg0) {
        stopKeepAlives();
        Log.d(TAG, "消息连接失败：" + arg0.getMessage());
        mClient = null;
        /**检查网络是否可用*/
        if (isNetworkAvailable()) {
            reconnectIfNecessary();
        }
    }

    /**
     * 接收到服务器推送来的消息，收到消息后的MqttCallback回调
     */
    @Override
    public void messageArrived(MqttTopic topic, MqttMessage message) {
        String msg = null;
        try {
            msg = new String(message.getPayload());
            // 接收到推送消息
            Log.d(TAG, "收到服务器端的消息推送");
            Log.d(TAG, "  Topic:\t" + topic.getName() +
                    "  Message:\t" + msg +
                    "  QoS:\t" + message.getQos());

            EventBus.getDefault().postSticky(message);

        } catch (MqttException e) {
            Log.d(TAG, "messageArrived接收消息异常MqttException：" + e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "messageArrived接收消息异常Exception：" + e.getMessage());
        }

    }


    /**
     * 消息发送完成
     * Publish Message Completion
     */
    @Override
    public void deliveryComplete(MqttDeliveryToken arg0) {
        Log.d(TAG, "消息发送完成");
    }

    /**
     * 发送通知
     */
//    private void sendNotify(String title, String time) {
//        int smallIcon = R.mipmap.lunch_logo;
//        String ticker = "收到一条测试消息";
//
//        //实例化工具类，并且调用接口
//        NotifyUtil notify1 = new NotifyUtil(this);
//        notify1.notifyEmpty(smallIcon, ticker, title, time, true, true, false);
//    }

    //=============================下面的三个方法是联网请求重新获取UID之后的处理==================

//    @Subscribe
//    public void onEventMainThread(final GetUidSucessEvent event) {
//        // 接收到获取UID和身份认证成功的事件
//        Log.d(TAG, "接收到获取UID和身份认证成功的事件");
//
//    }

    //=============================下面的方法是其它的方法==================

    /**
     * Attempts connect to the Mqtt Broker
     * and listen for Connectivity changes
     * via ConnectivityManager.CONNECTVITIY_ACTION BroadcastReceiver
     */
    private synchronized void start() {
        /**判断是否已经连接到服务器*/
        if (mStarted) {
            Log.d(TAG, "Attempt to start while already started");
            return;
        }

        /**判断是否还在保持长连接*/
        if (hasScheduledKeepAlives()) {
            Log.d(TAG, "正在保持长连接");
            /**断开长连接*/
            stopKeepAlives();
        }

        connect();
        /**注册一个监听网路连接改变的方法*/
        registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * Attempts to stop the Mqtt client
     * as well as halting all keep alive messages queued
     * in the alarm manager
     */
    private synchronized void stop() {
        if (!mStarted) {
            Log.d(TAG, "Attemtpign to stop connection that isn't running");
            return;
        }

        if (mClient != null) {
            mConnHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 取消连接
//                        mClient.disconnect(3000,new DisConnectCallBackHandler());
                        if (mClient != null) {
                            mClient.disconnect();
                        }
                    } catch (MqttException ex) {
                        Log.d(TAG, "取消连接MqttException--->" + ex.getMessage());
                    }
                    mClient = null;
                    mStarted = false;
                    /**停止保持心跳，长连接*/
                    stopKeepAlives();
                }
            });
        }

        unregisterReceiver(mConnectivityReceiver);
    }


    /**
     * 启动订阅，并开始定时器
     * Connects to the broker with the appropriate datastore
     */
    private synchronized void connect() {
        // 创建一个url
//        String messageIp = SharepreferenceUtil.get(getApplicationContext());
//        int messagePort = SharepreferenceUtil.getMessagePort(getApplicationContext());
        String messageIp="192.168.11.107";
        int messagePort =1893;
        Log.d("server", "消息服务地址：" + messageIp + "；端口：" + messagePort);
        String url = String.format(Locale.US, MQTT_URL_FORMAT, messageIp, messagePort);
        Log.d(TAG, "Connecting with URL: " + url);
        try {
            // 创建一个MqttClient
            if (mDataStore != null) {
                Log.d(TAG, "Connecting with DataStore");
                mClient = new MqttClient(url, mDeviceId, mDataStore);
            } else {
                Log.d(TAG, "Connecting with MemStore");
                mClient = new MqttClient(url, mDeviceId, mMemStore);
            }
            mClient = new MqttClient(url, mDeviceId, new MemoryPersistence());
        } catch (MqttException e) {
            Log.d(TAG, "创建一个MqttClient,MqttException--->" + e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "创建一个MqttClient,connect--->" + e.getCause() + "；错误信息：" + e.getMessage());
        }

        mConnHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mClient.connect(mOpts);
                    // 开始订阅；topic要与消息生产者的订阅主题要保持一致，才能收到消息
                    mClient.subscribe(connectTopic, 2);
                    // 订阅回调，//MqttCallbackHandler实现了MqttCallback接口，负责接收订阅的消息
                    mClient.setCallback(MqttService.this);
                    // Service is now connected
                    mStarted = true;

                    Log.d(TAG, "Successfully connected and subscribed starting keep alives");
                    // 保持长连接
                    startKeepAlives();
                } catch (MqttException e) {
                    Log.d(TAG, "订阅MqttException--->" + e.getReasonCode() + "；" + e.getCause() + "；" + e.getMessage());
                }
            }
        });
    }

    /**
     * 开始定时器保持长连接
     */
    private void startKeepAlives() {
        Intent i = new Intent();
        i.setClass(this, MqttService.class);
        i.setAction(ACTION_KEEPALIVE);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + MQTT_KEEP_ALIVE,
                MQTT_KEEP_ALIVE, pi);
        Log.d("MQTTTAG", "连接成功");
    }

    /**
     * 取消定时
     * Cancels the Pending Intent
     * in the alarm manager
     */
    private void stopKeepAlives() {
        Intent i = new Intent();
        i.setClass(this, MqttService.class);
        i.setAction(ACTION_KEEPALIVE);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        mAlarmManager.cancel(pi);
    }

    /**
     * 保持长连接
     */
    private synchronized void keepAlive() {
        /**如果还在与ActiveMQ服务器连接*/
        if (isConnected()) {
            try {
                // 发送一个心跳包，保持与服务器长连接
                sendKeepAlive();
            } catch (MqttConnectivityException ex) {
                Log.d(TAG, "发送心跳包MqttConnectivityException--->" + ex.getMessage());
                // 如果保持长连接出现异常，重新连接
                reconnectIfNecessary();
            } catch (MqttPersistenceException ex) {
                Log.d(TAG, "发送心跳包MqttPersistenceException--->" + ex.getMessage());
                stop();
            } catch (MqttException ex) {
                Log.d(TAG, "发送心跳包MqttException--->" + ex.getMessage());
                stop();
            }
        }
    }

    /**
     * 检查连接情况，如果没有连接就自动连接
     * Checkes the current connectivity
     * and reconnects if it is required.
     */
    private synchronized void reconnectIfNecessary() {
        if (mStarted && mClient == null) {
            connect();
        }
    }

    /**
     * Sends a Keep Alive message to the specified topic
     * 发送一个心跳包，保持长连接
     *
     * @return MqttDeliveryToken specified token you can choose to wait for completion
     * //     * @see MQTT_KEEP_ALIVE_MESSAGE
     * //     * @see MQTT_KEEP_ALIVE_TOPIC_FORMAT
     */
    private synchronized MqttDeliveryToken sendKeepAlive()
            throws MqttConnectivityException, MqttPersistenceException, MqttException {
        if (!isConnected()) {
            throw new MqttConnectivityException();
        }

        if (mKeepAliveTopic == null) {
            mKeepAliveTopic = mClient.getTopic(
                    String.format(Locale.US, MQTT_KEEP_ALIVE_TOPIC_FORAMT, mDeviceId));
        }

//        String messageIp = SharepreferenceUtil.getMessageIp(getApplicationContext());
//        Log.d(TAG, "Sending Keepalive to " + messageIp);

        MqttMessage message = new MqttMessage(MQTT_KEEP_ALIVE_MESSAGE);
        message.setQos(MQTT_KEEP_ALIVE_QOS);
        // 发送一个心跳包给服务器，然后回调到messageArrived方法中
        return mKeepAliveTopic.publish(message);
    }

    /**
     * 检查网络是否可用
     * Query's the NetworkInfo via ConnectivityManager
     * to return the current connected state
     *
     * @return boolean true if we are connected false otherwise
     */
    private boolean isNetworkAvailable() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

        return (info == null) ? false : info.isConnected();
    }

    /**
     * 检查与AcitiveMQ服务器的网络连接状态
     * Verifies the client State with our local connected state
     *
     * @return true if its a match we are connected false if we aren't connected
     */
    private boolean isConnected() {
        if (mStarted && mClient != null && !mClient.isConnected()) {
            Log.d(TAG, "Mismatch between what we think is connected and what is connected");
        }

        if (mClient != null) {
            return (mStarted && mClient.isConnected()) ? true : false;
        }

        return false;
    }

    /**
     * 监听网络连接改变的广播
     * Receiver that listens for connectivity chanes
     * via ConnectivityManager
     */
    private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Connectivity Changed...");
            /**检查网络是否可用*/
            if (!mStarted && isNetworkAvailable()) {
                reconnectIfNecessary();
            }
        }
    };


    /**
     * Query's the AlarmManager to check if there is
     * a keep alive currently scheduled
     *
     * @return true if there is currently one scheduled false otherwise
     */
    private synchronized boolean hasScheduledKeepAlives() {
        Intent i = new Intent();
        i.setClass(this, MqttService.class);
        i.setAction(ACTION_KEEPALIVE);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_NO_CREATE);

        return (pi != null) ? true : false;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * Send a KeepAlive Message
     *
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionKeepalive(Context ctx) {
        Intent i = new Intent(ctx, MqttService.class);
        i.setAction(ACTION_KEEPALIVE);
        ctx.startService(i);
    }

    /**
     * MqttConnectivityException Exception class
     */
    private class MqttConnectivityException extends Exception {
        private static final long serialVersionUID = -7385866796799469420L;
    }

}
