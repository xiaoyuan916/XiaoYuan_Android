package com.sgcc.pda.hardware.frame.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 蓝牙处理
 *
 * @Author: GuangJie-Wang
 * @Date: 2016/9/22
 * @Time: 16:57
 */
public class BlueToothUtil {


    public static int REC_CMD_NUM = 0;
    public static int SET_INFRA_COMM_PARAM = -1;// 0：设置失败，1：设置成功
    public static int GET_INFRA_COMM_PARAM = -1;// 0：设置失败，1：设置成功
    public static String INFRA_PARAMS = "";// 红外参数
    public static Map<String, byte[]> INFRA_REC = new HashMap<String, byte[]>();
    public static int SET_OVER_TIME = -1;
    public static String PIN_ID = "8260";//连接密码

    public static int LINK_STATE = 0;// 0:未连接 1：已连接

    public static String CUR_VERSION = "1";//当前系统版本号
    public static int CUR_GET_SYS_PARAM;//当前读取的哪个系统参数
    public final static String FLAG_SYS_PLATE_NUM = "81";//系统版号
    //盒子端是否返回数据的标识
    public static boolean isBack = false;
    public static String SELECT_VERSION = "";//所选择的升级包的版本

    public final static int SEND_UPGRADE_PATCH = 62;//发送升级包（0X3E）
    public final static int REC_UPGRADE_PATCH_RIGHT = -66;//发送升级包正常应答（0XBE）
    public final static int REC_UPGRADE_PATCH_WRONG = -2;//发送升级包异常应答（0XFE）

    public final static int SEND_UPGRADE = 63;//发送执行升级的命令	（0X3F）
    public final static int REC_UPGRADE_RIGHT = -65;//执行升级正常应答（0XBF）
    public final static int REC_UPGRADE_WRONG = -1;//执行升级异常应答（0XFF）

    /**
     * 与设备配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public boolean createBond(Class btClass, BluetoothDevice btDevice)
            throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    /**
     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public boolean removeBond(Class btClass, BluetoothDevice btDevice)
            throws Exception {
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    static public boolean setPin(Class btClass, BluetoothDevice btDevice,
                                 String str) throws Exception {
        Boolean returnValue = false;
        try {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin",
                    new Class[] { byte[].class });
            returnValue = (Boolean) removeBondMethod.invoke(btDevice,
                    new Object[] { str.getBytes() });
            Log.e("returnValue", "" + returnValue);
        } catch (SecurityException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    // 取消用户输入
    static public boolean cancelPairingUserInput(Class btClass,
                                                 BluetoothDevice device)

            throws Exception {
        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
        // ClsUtils.cancelBondProcess(btClass, device);
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    // 取消配对
    static public boolean cancelBondProcess(Class btClass,
                                            BluetoothDevice device)

            throws Exception {
        Method createBondMethod = btClass.getMethod("cancelBondProcess");
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    /**
     *
     * @param clsShow
     */
    static public void printAllInform(Class clsShow) {
        try {
            // 取得所有方法
            Method[] hideMethod = clsShow.getMethods();
            int i = 0;
            for (; i < hideMethod.length; i++) {
                Log.e("method name", hideMethod[i].getName() + ";and the i is:"
                        + i);
            }
            // 取得所有常量
            Field[] allFields = clsShow.getFields();
            for (i = 0; i < allFields.length; i++) {
                Log.e("Field name", allFields[i].getName());
            }
        } catch (SecurityException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public boolean pair(String strAddr, String strPsw) {
        boolean result = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        bluetoothAdapter.cancelDiscovery();

        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }

        if (!BluetoothAdapter.checkBluetoothAddress(strAddr)) { // 检查蓝牙地址是否有效

            Log.d("mylog", "devAdd un effient!");
        }

        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(strAddr);

        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
            try {
                Log.d("mylog", "NOT BOND_BONDED");
                BlueToothUtil.setPin(device.getClass(), device, strPsw); // 手机和蓝牙采集器配对
                BlueToothUtil.createBond(device.getClass(), device);
                // remoteDevice = device; // 配对完毕就把这个设备对象传给全局的remoteDevice
                result = true;
            } catch (Exception e) {

                Log.d("mylog", "setPiN failed!");
                e.printStackTrace();
            } //

        } else {
            Log.d("mylog", "HAS BOND_BONDED");
            try {
                BlueToothUtil.createBond(device.getClass(), device);
                BlueToothUtil.setPin(device.getClass(), device, strPsw); // 手机和蓝牙采集器配对
                BlueToothUtil.createBond(device.getClass(), device);
                // remoteDevice = device; // 如果绑定成功，就直接把这个设备对象传给全局的remoteDevice
                result = true;
            } catch (Exception e) {
                Log.d("mylog", "setPiN failed!");
                e.printStackTrace();
            }
        }
        return result;
    }

}
