// IDEVBaseInterface.aidl
package com.cepri.service;

// Declare any non-default types here with import statements

/**
* 系统基础服务接口
*/
interface IDEVBaseInterface {
/**
     * 设置系统时钟
     * @param dateTime 设置日期时间值的字符串yyyy-MM-dd HH:mm:ss，如2016-10-28 16:34:45
     * @return 成功返回 true，失败返回 false
     */
    boolean setDateTime(String dateTime);

    /**
     * 添加 APN 设置，同时设置为默认 APN
     * @param apnName APN 设置的名称标识
     * @param apn APN 内容
     * @param userName 用户名，可为空或 null
     * @param password 密码，可为空或 null
     * @return 成功返回 true，失败返回 false
     */
    boolean addApn(String apnName, String apn, String userName, String password);

    /**
     * 删除 APN 设置
     * @param apn 要删除的 APN 设置项对应的 APN 内容
     * @return 成功返回 true，失败返回 false
     */
    boolean deleteApn(String apn);

    /**
     * 获取设备型号
     * @return 成功返回设备型号，失败返回空字符串
     */
    String getDeviceModel();

    /**
     * 获取设备序列号
     * @return 成功返回设备序列号，失败返回空字符串
     */
    String getDeviceSn();

    /**
     * 获取设备系统版本号
     * @return 成功返回设备系统版本号，失败返回空字符串
     */
    String getOSVersion();

    /**
     * 获取设备硬件版本号
     * @return 成功返回设备硬件版本号，失败返回空字符串
     */
    String getDeviceHardVersion();

    /**
     * 获取设备生产日期
     * @return 成功返回设备生产日期，失败返回空字符串
     */
    String getProductionDate();

    /**
     * 设置USB的传输方式
     * @param mode USB 通讯模式<br>
     *     0x00: 关闭所有通讯<br>
     *     0x01: ADB 通讯<br>
     *     0x02: MTP 通讯<br>
     *     0x04: 大容量存储<br>
     *     可以多种模式同时使用
     * @return 成功返回 true，失败返回 false
     */
    boolean setUSBMode(int mode);

    /**
     * 系统升级（会重启设备）
     * @param filePath 升级文件的绝对路径
     * @return 成功返回 true，失败返回 false
     */
    boolean OSUpdate(String filePath);

    /**
     * 获取系统校验百分比
     * @return 系统校验百分比
     */
    int getOSVerifyPercent();

    /**
     * 获取系统基础服务的版本
     * @return 成功返回系统基础服务的版本，失败返回空字符串
     */
    String getServiceVersion();

    /**
     * 设置 HOME 键的可用状态
     * @param status HOME 键是否可用的状态
     *          true: 可用
     *          false: 不可用
     * @return 设置成功返回 true，失败返回 false
     */
    boolean setHomeKeyStatus(boolean status);

    /**
     * 设置下拉状态栏是否可下拉
     * @param status 下拉状态栏是否可下拉的状态
     *          true: 可下拉
     *          false: 不可下拉
     * @return 设置成功返回 true，失败返回 false
     */
    boolean setStatusBarPullable(boolean status);
}
