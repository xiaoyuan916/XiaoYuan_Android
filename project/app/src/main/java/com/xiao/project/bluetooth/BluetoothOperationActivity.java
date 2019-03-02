package com.xiao.project.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.xiao.project.MainActivity;
import com.xiao.project.R;
import com.xiao.project.activity.RefrshActivity;
import com.xiao.project.bluetooth.comm.Observer;
import com.xiao.project.bluetooth.comm.ObserverManager;
import com.xiao.project.bluetooth.utils.DataTransfer;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xiao.project.bluetooth.operation.OperationActivity.KEY_DATA;

public class BluetoothOperationActivity extends AppCompatActivity implements Observer {

    @BindView(R.id.bt_get_characteristic_list)
    Button btGetCharacteristicList;
    @BindView(R.id.bt_read)
    Button btRead;

    private BleDevice bleDevice;
    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    private final static String    UUID = "00001800-0000-1000-8000-00805f9b34fb";

    /**
     * service的UUID00001800-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a00-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a01-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a04-0000-1000-8000-00805f9b34fb
     * service的UUID0000180a-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a29-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a24-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a25-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a27-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a26-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a28-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a23-0000-1000-8000-00805f9b34fb
     * characteristic的UUID00002a2a-0000-1000-8000-00805f9b34fb
     * service的UUID49535343-fe7d-4ae5-8fa9-9fafd205e455
     * characteristic的UUID49535343-6daa-4d02-abf6-19569aca69fe
     * characteristic的UUID49535343-aca3-481c-91ec-d85e28a60318
     * characteristic的UUID49535343-1e4d-4bd9-ba61-23c647249616
     * characteristic的UUID49535343-8841-43f4-a8d4-ecbe34729bb3
     * characteristic的UUID49535343-026e-3a9b-954c-97daef17e26e
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_operation);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        bleDevice = getIntent().getParcelableExtra(KEY_DATA);
        ObserverManager.getInstance().addObserver(this);
    }

    @OnClick({R.id.bt_get_characteristic_list, R.id.bt_read})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_get_characteristic_list:
                BluetoothGatt gatt = BleManager.getInstance().getBluetoothGatt(bleDevice);
//                BluetoothGattService service = gatt.getService(UUID.fromString(MY_UUID));
                List<BluetoothGattService> services = gatt.getServices();
                for (BluetoothGattService service : services) {
                    Log.d(getClass().getSimpleName(), "service的UUID" + service.getUuid().toString());
                    for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                        Log.d(getClass().getSimpleName(), "characteristic的UUID" + characteristic.getUuid().toString());
//                        readData(service.getUuid().toString(),characteristic.getUuid().toString());
                    }
                }

                break;

            case R.id.bt_read:
                readData("00001800-0000-1000-8000-00805f9b34fb",
                        "00002a00-0000-1000-8000-00805f9b34fb");
                break;
        }
    }

    private void readData(String uuid_service,
                          String uuid_read) {
        BleManager.getInstance().read(
                bleDevice,
                MY_UUID,
                MY_UUID,
                new BleReadCallback() {
                    @Override
                    public void onReadSuccess(final byte[] data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //处理数据
                                dealData(data);
                            }
                        });
                    }

                    @Override
                    public void onReadFailure(final BleException exception) {
                    }
                });
    }

    private void dealData(byte[] datum) {

        String xGetString = DataTransfer.xGetString(datum);
        Log.d(getClass().getSimpleName(), xGetString);

//        byte frequencyPointIdByte = datum[0];
//        frequencyPointIdByte = (byte)(frequencyPointIdByte >> 2);
//        frequencyPointIdByte = (byte)(frequencyPointIdByte & 63);
//        UmdFrequencyPoint frequencyPoint = UmdFrequencyPoint.ValueOfId(frequencyPointIdByte);
//        int antennaId = datum[0];
//        int totalRead = antennaId & 3;
//        UII uii = UII.getNewInstanceByBytes(datum, 1);
//        UmdRssi rssi = new UmdRssi(datum[datum.length - 1]);
//        String xGetString = DataTransfer.xGetString(uii.getBytes());
//        Log.d(getClass().getSimpleName(),xGetString);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        BleManager.getInstance().clearCharacterCallback(bleDevice);
        ObserverManager.getInstance().deleteObserver(this);
    }

    /**
     * 监听蓝牙失去连接
     *
     * @param bleDevice
     */
    @Override
    public void disConnected(BleDevice bleDevice) {

    }
}
