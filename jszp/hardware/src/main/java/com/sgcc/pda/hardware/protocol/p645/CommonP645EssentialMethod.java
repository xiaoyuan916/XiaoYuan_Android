package com.sgcc.pda.hardware.protocol.p645;


import com.sgcc.pda.hardware.hardware.ICommunicate;
import com.sgcc.pda.hardware.protocol.IBaseProtocol;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 默认红外基本操作类
 */
public class CommonP645EssentialMethod extends IP645EssentialMethod<P645Frame> {
    private static final String WAKE_UP = "FEFEFEFE";

    public CommonP645EssentialMethod(IBaseProtocol<P645Frame> protocol, ICommunicate communicate) {
        this.protocol = protocol;
        this.communicate = communicate;
        this.readTimeout = 5000;
    }

    @Override
    public P645Frame getFrameNewInstance() {
        return new P645Frame(getProtocol());
    }

    /**
     * 向设备发送一次P645请求，并读取设备返回数据
     *
     * @param downFrame 请求HEX字符串
     * @param upFrame   返回-设备返回的HEX字符串
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedErrorValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    private int commitDown(String upFrame, StringBuffer downFrame) {
        if (upFrame == null || upFrame.length() <= 0) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (downFrame == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        // 清空缓冲区，放置外来数据的干扰
        downFrame.delete(0, downFrame.length());

        // 判断红外通讯基本数据设置是否成功
        if (protocol == null || communicate == null) {
            return ErrorManager.ErrorType.CommonProtocolError.getValue();
        }

        // 处理请求帧，在帧前增加唤醒帧，并消除前后空格的影响
        upFrame = WAKE_UP + upFrame.trim();

        // 通过硬件通讯接口发送数据
        int sendRet = communicate.write(upFrame);
        if (sendRet != 0) {
            return sendRet;
        }


        // 通过硬件通讯接口读取返回的数据
        int readRet = communicate.read(downFrame, getReadTimeout());

        if (readRet != 0) {
            LogUtil.d("XianChangBuChaoASyncTask", "communicate.read readRet = " + readRet);
            return readRet;
        }

        return 0;
    }

    /**
     * 向设备发送一次P645协议数据，不从设备端获取返回数据
     *
     * @param upFrame 要发送的上行帧
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonWriteError-数据写入错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645DataLengthError-数据长度不符合规约要求
     */
    private int commitDownNoAnswer(String upFrame) {
        if (upFrame == null || upFrame.length() <= 0) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        // 判断红外通讯基本数据设置是否成功
        if (protocol == null || communicate == null) {
            return ErrorManager.ErrorType.CommonProtocolError.getValue();
        }

        // 处理请求帧，在帧前增加唤醒帧，并消除前后空格的影响
        upFrame = WAKE_UP + upFrame.trim();
        // 通过硬件通讯接口发送数据
        int sendRet = communicate.write(upFrame);
        // 在通讯完成后关闭端口
        if (sendRet != 0) {
            return sendRet;
        }
        return 0;
    }

    /**
     * 进行一次通讯交互
     *
     * @param meterAddress 目标电表表地址或表地址掩码
     * @param control      控制字
     * @param data         待发送数据
     * @param retData      返回-通讯返回的数据域
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedErrorValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int commonCommunicate(String meterAddress, String control, String data, StringBuffer retData) {
        if (retData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        // 进行输入参数的合法性判断
        if (control == null || control.length() != 2) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        // 消除外来数据的影响
        retData.delete(0, retData.length());

        P645Frame upFrame = new P645Frame(protocol);
        upFrame.setMeterAddress(meterAddress);
        upFrame.setControl(control);
        upFrame.setData(data);

        String upFrameString = upFrame.getString();
        if (upFrameString == null) { // 如果帧返回发送数据，说明帧数据有问题，直接读取帧本身异常返回
            return upFrame.getException();
        }

        StringBuffer sb = new StringBuffer();
        int ret = commitDown(upFrameString, sb);
        if (ret != 0) {
            return ret;
        }
        P645Frame downFrame = protocol.parseDown(sb.toString());
        // 返回下行帧异常信息，并返回通讯数据域
        ret = checkDownFrameException(upFrame, downFrame);
        retData.append(downFrame.getData());
        return ret;
    }

    /**
     * 根据自定义数据标识读取数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retData      读取到的数据
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedErrorValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int readData(String meterAddress, String dataSign, StringBuffer retData) {
        if (retData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        // 进行输入参数的合法性判断
        if (dataSign == null || dataSign.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        // 将数据标识进行反向
        dataSign = DataConvert.strReverse(dataSign, 0, dataSign.length());

        // 消除外来数据的影响
        retData.delete(0, retData.length());

        P645Frame upFrame = new P645Frame(protocol);
        upFrame.setMeterAddress(meterAddress);
        upFrame.setControl("11"); // 读取电表命令标识为11
        upFrame.setData(dataSign);

        String upFrameString = upFrame.getString();
        if (upFrameString == null) { // 如果帧返回发送数据，说明帧数据有问题，直接读取帧本身异常返回
            return upFrame.getException();
        }

        StringBuffer sb = new StringBuffer();
        int ret = commitDown(upFrameString, sb);
        if (ret != 0) {
            LogUtil.d("XianChangBuChaoASyncTask", "commitDown ret = " + ret);
            return ret;
        }
        P645Frame downFrame = protocol.parseDown(sb.toString());
        // 返回下行帧异常信息，并返回通讯数据域
        ret = checkDownFrameException(upFrame, downFrame);
        if (ret != 0) {
            LogUtil.d("XianChangBuChaoASyncTask", "checkDownFrameException ret = " + ret);
        }
        retData.append(downFrame.getData());
        return ret;
    }

    @Override
    public int readData(String meterAddress, String dataSign, StringBuffer retData, boolean isMeter97) {
        if (retData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        // 进行输入参数的合法性判断
        if (isMeter97) {
            if (dataSign == null || dataSign.length() != 4) {
                return ErrorManager.ErrorType.CommonParamError.getValue();
            }
        } else {
            if (dataSign == null || dataSign.length() != 8) {
                return ErrorManager.ErrorType.CommonParamError.getValue();
            }
        }

        // 将数据标识进行反向
        dataSign = DataConvert.strReverse(dataSign, 0, dataSign.length());

        // 消除外来数据的影响
        retData.delete(0, retData.length());

        P645Frame upFrame = new P645Frame(protocol);
        upFrame.setMeterAddress(meterAddress);
        if (isMeter97) {
            upFrame.setControl("01"); // 读取电表命令标识为01
        } else {
            upFrame.setControl("11"); // 读取电表命令标识为11
        }
        upFrame.setData(dataSign);

        String upFrameString = upFrame.getString();
        if (upFrameString == null) { // 如果帧返回发送数据，说明帧数据有问题，直接读取帧本身异常返回
            return upFrame.getException();
        }

        StringBuffer sb = new StringBuffer();
        int ret = commitDown(upFrameString, sb);
        if (ret != 0) {
            return ret;
        }
        P645Frame downFrame = protocol.parseDown(sb.toString());
        // 返回下行帧异常信息，并返回通讯数据域
        ret = checkDownFrameException(upFrame, downFrame);
        retData.append(downFrame.getData());
        return ret;
    }

    /**
     * 向电表写入数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param keyLevel     密级
     * @param password     密码
     * @param operator     操作员编号
     * @param data         写入数据内容
     * @param mac          安全单元计算MAC
     * @param retData      写入数据后电表的响应
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedErrorValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int writeData(String meterAddress, String dataSign, String keyLevel,
                         String password, String operator, String data, String mac, StringBuffer retData) {
        if (retData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        // 进行输入参数的合法性判断
        if (dataSign == null || dataSign.length() != 8 ||
                keyLevel == null || keyLevel.length() != 2) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        // 处理其它字段长度
        password = DataConvert.getFixedStringWithChar(password, 6, false, '0');
        operator = DataConvert.getFixedStringWithChar(operator, 8, false, '0');

        // 消除外来数据对数据接收的影响
        retData.delete(0, retData.length());

        P645Frame upFrame = new P645Frame(protocol);
        upFrame.setMeterAddress(meterAddress);
        upFrame.setControl("14");
        // 根据645协议规定，密级为98密文+MAC，需要将数据分别倒序并增加33H发送
        // 密级为99明文+MAC，将数据分别倒序并增加33H发送
        if ("99".equals(keyLevel) || "98".equals(keyLevel)) {
            if (data != null) {
                //处理电价调整 数据块分别倒序
                if (data.endsWith("-")) {
                    data = data.substring(0, data.length() - 1);
                    if (data.length() == 32) { //4套费率
                        String data1 = data.substring(0, 8);
                        String data2 = data.substring(8, 16);
                        String data3 = data.substring(16, 24);
                        String data4 = data.substring(24, 32);
                        data = DataConvert.strReverse(data1, 0, data1.length()) +
                                DataConvert.strReverse(data2, 0, data2.length()) +
                                DataConvert.strReverse(data3, 0, data3.length()) +
                                DataConvert.strReverse(data4, 0, data4.length());
                    } else if (data.length() == 64) { //8套费率
                        String data1 = data.substring(0, 8);
                        String data2 = data.substring(8, 16);
                        String data3 = data.substring(16, 24);
                        String data4 = data.substring(24, 32);
                        String data5 = data.substring(32, 40);
                        String data6 = data.substring(40, 48);
                        String data7 = data.substring(48, 56);
                        String data8 = data.substring(56, 64);
                        data = DataConvert.strReverse(data1, 0, data1.length()) +
                                DataConvert.strReverse(data2, 0, data2.length()) +
                                DataConvert.strReverse(data3, 0, data3.length()) +
                                DataConvert.strReverse(data4, 0, data4.length()) +
                                DataConvert.strReverse(data5, 0, data4.length()) +
                                DataConvert.strReverse(data6, 0, data4.length()) +
                                DataConvert.strReverse(data7, 0, data4.length()) +
                                DataConvert.strReverse(data8, 0, data4.length());
                    } else if (data.length() == 96) { //12套费率
                        String data1 = data.substring(0, 8);
                        String data2 = data.substring(8, 16);
                        String data3 = data.substring(16, 24);
                        String data4 = data.substring(24, 32);
                        String data5 = data.substring(32, 40);
                        String data6 = data.substring(40, 48);
                        String data7 = data.substring(48, 56);
                        String data8 = data.substring(56, 64);
                        String data9 = data.substring(64, 72);
                        String data10 = data.substring(72, 80);
                        String data11 = data.substring(80, 88);
                        String data12 = data.substring(88, 96);
                        data = DataConvert.strReverse(data1, 0, data1.length()) +
                                DataConvert.strReverse(data2, 0, data2.length()) +
                                DataConvert.strReverse(data3, 0, data3.length()) +
                                DataConvert.strReverse(data4, 0, data4.length()) +
                                DataConvert.strReverse(data5, 0, data5.length()) +
                                DataConvert.strReverse(data6, 0, data6.length()) +
                                DataConvert.strReverse(data7, 0, data7.length()) +
                                DataConvert.strReverse(data8, 0, data8.length()) +
                                DataConvert.strReverse(data9, 0, data9.length()) +
                                DataConvert.strReverse(data10, 0, data10.length()) +
                                DataConvert.strReverse(data11, 0, data11.length()) +
                                DataConvert.strReverse(data12, 0, data12.length());
                    } else {
                        data = DataConvert.strReverse(data, 0, data.length());
                    }
                } else {
                    data = DataConvert.strReverse(data, 0, data.length());
                }
            }
            if (mac != null) {
                mac = DataConvert.strReverse(mac, 0, mac.length());
            }
        }

        dataSign = DataConvert.strReverse(dataSign, 0, dataSign.length());

        password = DataConvert.strReverse(password, 0, password.length());

        operator = DataConvert.strReverse(operator, 0, operator.length());

        upFrame.setData(dataSign + keyLevel + password + operator +
                (data == null ? "" : data.trim()) +
                (mac == null ? "" : mac.trim()));

        String upFrameString = upFrame.getString();
        if (upFrameString == null) { // 如果帧返回发送数据，说明帧数据有问题，直接读取帧本身异常返回
            return upFrame.getException();
        }

        StringBuffer sb = new StringBuffer();
        int ret = commitDown(upFrameString, sb);
        if (ret != 0) {
            return ret;
        }
        P645Frame downFrame = protocol.parseDown(sb.toString());
        // 返回下行帧异常信息，并返回通讯数据域
        ret = checkDownFrameException(upFrame, downFrame);
        retData.append(downFrame.getData());
        return ret;
    }

    /**
     * 电表进行远程控制
     *
     * @param meterAddress      表通讯地址
     * @param keyLevel          密级
     * @param operator          操作员编号
     * @param password          密码
     * @param cipherOperateData 安全单元返回的控制命令密文
     * @param retData           返回-电表返回的信息
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedErrorValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int powerOperate(String meterAddress, String keyLevel, String operator,
                            String password, String cipherOperateData, StringBuffer retData) {
        if (retData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        // 进行输入参数的合法性判断
        if (keyLevel == null || keyLevel.length() != 2) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        // 处理其它字段长度
        password = DataConvert.getFixedStringWithChar(password, 6, false, '0');
        operator = DataConvert.getFixedStringWithChar(operator, 8, false, '0');

        // 消除外来数据对数据接收的影响
        retData.delete(0, retData.length());

        P645Frame upFrame = new P645Frame(protocol);
        upFrame.setMeterAddress(meterAddress);
        upFrame.setControl("1C"); // 控制命令码为1C
        upFrame.setData(keyLevel + password + operator +
                (cipherOperateData == null ? "" : cipherOperateData));

        String upFrameString = upFrame.getString();
        if (upFrameString == null) { // 如果帧返回发送数据，说明帧数据有问题，直接读取帧本身异常返回
            return upFrame.getException();
        }

        StringBuffer sb = new StringBuffer();
        int ret = commitDown(upFrameString, sb);
        if (ret != 0) {
            return ret;
        }
        P645Frame downFrame = protocol.parseDown(sb.toString());
        // 返回下行帧异常信息，并返回通讯数据域
        ret = checkDownFrameException(upFrame, downFrame);
        retData.append(downFrame.getData());
        return ret;
    }

    /**
     * 电表安全认证指令
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param operator     操作员编号
     * @param data         认证数据
     * @param retData      返回-电表安全认证后的返回指令
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedErrorValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int safetyCertification(String meterAddress, String dataSign, String operator,
                                   String data, StringBuffer retData) {
        if (retData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        // 进行输入参数的合法性判断
        if (dataSign == null || dataSign.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        // 处理其它字段长度
        operator = DataConvert.getFixedStringWithChar(operator, 8, false, '0');

        // 消除外来数据对数据接收的影响
        retData.delete(0, retData.length());

        P645Frame upFrame = new P645Frame(protocol);
        upFrame.setMeterAddress(meterAddress);
        upFrame.setControl("03"); // 安全认证指令命令码为03
        upFrame.setData(dataSign + operator + (data == null ? "" : data.trim()));

        String upFrameString = upFrame.getString();
        if (upFrameString == null) { // 如果帧返回发送数据，说明帧数据有问题，直接读取帧本身异常返回
            return upFrame.getException();
        }

        StringBuffer sb = new StringBuffer();
        int ret = commitDown(upFrameString, sb);
        if (ret != 0) {
            return ret;
        }
        P645Frame downFrame = protocol.parseDown(sb.toString());
        // 返回下行帧异常信息，并返回通讯数据域
        ret = checkDownFrameException(upFrame, downFrame);
        retData.append(downFrame.getData());
        return ret;
    }

    /**
     * 广播发送命令
     *
     * @param data 要广播的数据
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonWriteError-数据写入错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int broadcast(String data) {
        P645Frame frame = new P645Frame(protocol);
        frame.setMeterAddress("999999999999");
        frame.setControl("08"); // ?广播命令码为08
        frame.setData((data == null ? "" : data.trim()));

        String upFrame = frame.getString();
        if (upFrame == null) { // 如果帧不能返回发送字符串，证明帧有错误，直接返回帧异常信息
            return frame.getException();
        } else {
            return commitDownNoAnswer(upFrame);
        }
    }

    /**
     * 判断返回的帧是否有异常出现
     *
     * @param upFrame   上行帧实例（请求帧）
     * @param downFrame 下行帧实例（响应帧）
     * @return 0-无异常
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedErrorValue-P645接收了一个包含异常信息的返回帧
     */
    public int checkDownFrameException(P645Frame upFrame, P645Frame downFrame) {
        // 判断输入的帧没有异常
        if (upFrame == null || downFrame == null) {
            return ErrorManager.ErrorType.P645FrameError.getValue();
        }
        if (upFrame.getException() != 0) {
            return upFrame.getException();
        }
        if (downFrame.getException() != 0) {
            return downFrame.getException();
        }

        try {
            // 判断是否为下行帧
            if (DataConvert.getMaskedValue(downFrame.getControl(), 0x80) != 0x80) {
                return ErrorManager.ErrorType.P645FrameError.getValue();
            }
            // 判断是否有异常信息
            if (DataConvert.getMaskedValue(downFrame.getControl(), 0xC0) == 0xC0) {
                return ErrorManager.ErrorType.P645ReceivedErrorValue.getValue();
            }
            // 判断上下行帧是否匹配
            if (DataConvert.getMaskedValue(downFrame.getControl(), 0x1F) !=
                    DataConvert.getMaskedValue(upFrame.getControl(), 0x1F)) {
                return ErrorManager.ErrorType.P645FrameMatchError.getValue();
            }
        } catch (DataConvert.DataConvertException ex) {
            // 如果判断失败，肯定有数据错误
            return ErrorManager.ErrorType.P645FrameError.getValue();
        }
        return 0;
    }

}
