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
                for (BluetoothGattService service :services){
                    Log.d(getClass().getSimpleName(),"service的UUID"+service.getUuid().toString());
                    for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                        Log.d(getClass().getSimpleName(),"characteristic的UUID"+characteristic.getUuid().toString());
                    }
                }

                break;

            case R.id.bt_read:
                BleManager.getInstance().read(
                        bleDevice,
//                        characteristic.getService().getUuid().toString(),
//                        characteristic.getUuid().toString(),
                        null,null,
                        new BleReadCallback() {

                            @Override
                            public void onReadSuccess(final byte[] data) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String xGetString = DataTransfer.xGetString(data);
                                        Log.d(getClass().getSimpleName(),xGetString);
//                                        addText(txt, xGetString);
                                    }
                                });
                            }

                            @Override
                            public void onReadFailure(final BleException exception) {
                            }
                        });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        BleManager.getInstance().clearCharacterCallback(bleDevice);
        ObserverManager.getInstance().deleteObserver(this);
    }

    /**
     * 监听蓝牙失去连接
     * @param bleDevice
     */
    @Override
    public void disConnected(BleDevice bleDevice) {

    }
}
