package com.sgcc.pda.hardware.protocol;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 协议通用接口
 */
public interface IBaseProtocol<T extends BaseFrame> {
    // 定义特殊值，标记计算帧校验码与实际计算结果不同
    String CHECK_ERROR = "com.thinta.eaadp.check_error";
    /**
     * 获取传输数据
     * @param t 通讯帧实例
     * @return 通讯帧的HEX字符串
     */
    String getSendData(T t);

    /**
     * 获取上行帧实例
     * @param frameString 待解析的HEX字符串
     * @return 通讯上行帧实例
     */
    T parseUp(String frameString);

    /**
     * 获取下行帧实例
     * @param frameString 从硬件接口模块获取的HEX字符串
     * @return 下行帧实例
     */
    T parseDown(String frameString);

    /**
     * 计算校验值
     * @return 校验值HEX字符串
     */
    String computeCheckValue(T t);

    /**
     * 计算数据域长度
     * @return 数据长度的HEX字符串
     */
    String computeDataLength(T t);

    /**
     * 检查帧数据是否存在错误
     * @param t 帧实例
     * @return 0-不存在错误 其它-错误码
     */
    int checkFrameError(T t);

    /**
     * 获取帧起始码
     * @return 帧起始码
     */
    String getFrameHead();

    /**
     * 获取帧结束码
     * @return 帧结束码
     */
    String getFrameTail();
}
