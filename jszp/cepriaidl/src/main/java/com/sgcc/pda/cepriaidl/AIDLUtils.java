package com.sgcc.pda.cepriaidl;

import android.content.Context;
import android.os.RemoteException;

import com.cepri.service.IDEVBaseInterface;

/**
 * Created by xuzl on 2016/11/15.
 * aidl工具
 */
public class AIDLUtils {
    /**
     * 设置系统时钟
     *
     * @param dateTime 设置日期时间值的字符串yyyy-MM-dd HH:mm:ss，如2016-10-28 16:34:45
     * @return 成功返回 true，失败返回 false
     */
    public static boolean setDateTime(String dateTime) {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().setDateTime(dateTime);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加 APN 设置，同时设置为默认 APN
     *
     * @param apnName  APN 设置的名称标识
     * @param apn      APN 内容
     * @param userName 用户名，可为空或 null
     * @param password 密码，可为空或 null
     * @return 成功返回 true，失败返回 false
     */
    public static boolean addApn(String apnName, String apn, String userName, String password) {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().addApn(apnName, apn, userName, password);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除 APN 设置
     *
     * @param apn 要删除的 APN 设置项对应的 APN 内容
     * @return 成功返回 true，失败返回 false
     */
    public static boolean deleteApn(String apn) {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().deleteApn(apn);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取设备型号
     *
     * @return 成功返回设备型号，失败返回空字符串
     */
    public static String getDeviceModel() {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().getDeviceModel();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备序列号
     *
     * @return 成功返回设备序列号，失败返回空字符串
     */
    public static String getDeviceSn() {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().getDeviceSn();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 成功返回设备系统版本号，失败返回空字符串
     */
    public static String getOSVersion() {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().getOSVersion();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备硬件版本号
     *
     * @return 成功返回设备硬件版本号，失败返回空字符串
     */
    public static String getDeviceHardVersion() {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().getDeviceHardVersion();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备生产日期
     *
     * @return 成功返回设备生产日期，失败返回空字符串
     */
    public static String getProductionDate() {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().getProductionDate();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置USB的传输方式
     *
     * @param mode USB 通讯模式<br>
     *             0x00: 关闭所有通讯<br>
     *             0x01: ADB 通讯<br>
     *             0x02: MTP 通讯<br>
     *             0x04: 大容量存储<br>
     *             可以多种模式同时使用
     * @return 成功返回 true，失败返回 false
     */
    public static boolean setUSBMode(int mode) {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().setUSBMode(mode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 系统升级（会重启设备）
     *
     * @param filePath 升级文件的绝对路径
     * @return 成功返回 true，失败返回 false
     */
    public static boolean OSUpdate(String filePath) {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().OSUpdate(filePath);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取系统校验百分比
     *
     * @return 系统校验百分比
     */
    public static int getOSVerifyPercent() {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().getOSVerifyPercent();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取系统基础服务的版本
     *
     * @return 成功返回系统基础服务的版本，失败返回空字符串
     */
    public static String getServiceVersion() {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().getServiceVersion();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置 HOME 键的可用状态
     *
     * @param status HOME 键是否可用的状态
     *               true: 可用
     *               false: 不可用
     * @return 设置成功返回 true，失败返回 false
     */
    public static boolean setHomeKeyStatus(boolean status) {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().setHomeKeyStatus(status);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置下拉状态栏是否可下拉
     *
     * @param status 下拉状态栏是否可下拉的状态
     *               true: 可下拉
     *               false: 不可下拉
     * @return 设置成功返回 true，失败返回 false
     */
    public static boolean setStatusBarPullable(boolean status) {
        try {
            IDEVBaseInterface idevBaseInterface = CepriServiceManager.getInstance().getCepriService();
            if (null != idevBaseInterface) {
                return CepriServiceManager.getInstance().getCepriService().setStatusBarPullable(status);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 绑定AIDL服务
     * @param context
     */
    public static void bind(Context context) {
        CepriServiceManager.getInstance().bind(context);
    }

    /**
     * 解绑AIDL服务
     * @param context
     */
    public static void unbind(Context context) {
        CepriServiceManager.getInstance().unbind(context);
    }
}
