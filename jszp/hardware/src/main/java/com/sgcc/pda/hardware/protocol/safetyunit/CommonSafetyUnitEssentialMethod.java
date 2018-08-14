package com.sgcc.pda.hardware.protocol.safetyunit;

import com.sgcc.pda.hardware.event.HardwareErroeEvent;
import com.sgcc.pda.hardware.hardware.ICommunicate;
import com.sgcc.pda.hardware.protocol.IBaseProtocol;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.hardware.util.EventManager;


/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/29.
 * 安全基础通讯类支持
 */
public class CommonSafetyUnitEssentialMethod extends ISafetyUnitEssentialMethod<SafetyUnitFrame> {

    public CommonSafetyUnitEssentialMethod(IBaseProtocol<SafetyUnitFrame> protocol, ICommunicate communicate) {
        this.protocol = protocol;
        this.communicate = communicate;
        this.readTimeout = 2000;
    }

    /**
     * 获取与基础通讯匹配的协议帧新实例
     * @return 协议帧实例
     */
    @Override
    public SafetyUnitFrame getFrameNewInstance() {
        return new SafetyUnitFrame(protocol);
    }

    /**
     * 向安全单元发送命令并获取安全单元返回
     * @param mainSign 主功能标识
     * @param control 命令码
     * @param data 发送命令
     * @param retData 读取到的数据
     * @return 0-成功
     *          CommonParamError-输入参数不合法
     *          CommonBufferError-返回值缓冲区为空
     *          CommonWriteError-数据写入错误
     *          SafetyConfigError-协议和帧结构定义为空
     *          SafetyUnitError-安全单元操作设备操作失败
     *          SafetyFrameError-安全单元通讯帧格式错误
     *          SafetyFrameMatchError-上下行帧不匹配
     *          SafetyGetStatusError-安全单元下行帧返回了异常帧
     */
    @Override
    synchronized public int commitDown(String mainSign, String control, String data, StringBuffer retData) {
        if (mainSign.length() != 2 || control.length() != 2) {
            EventManager.getDefault().post(new HardwareErroeEvent("向安全单元发送命令并获取安全单元返回数据失败，出现在A1BSafetyUnitModule类 read()方法中",
                    "data = "+data+" retData = "+retData+" mainSign = "+mainSign+" control = "+control));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (retData == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("向安全单元发送命令并获取安全单元返回数据失败，出现在A1BSafetyUnitModule类 read()方法中",
                    "data = "+data+" retData = "+retData+" mainSign = "+mainSign+" control = "+control));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (protocol == null || communicate == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("向安全单元发送命令并获取安全单元返回数据失败，出现在A1BSafetyUnitModule类 read()方法中",
                    "data = "+data+" retData = "+retData+" mainSign = "+mainSign+" control = "+control));
            return ErrorManager.ErrorType.CommonProtocolError.getValue();
        }
        SafetyUnitFrame upFrame = getFrameNewInstance();
        upFrame.setMainSign(mainSign);
        upFrame.setControl(control);
        upFrame.setData(data);

        String upString = upFrame.getString();
        if (upString == null) { // 无法获取上行字符串，则组帧时出现异常，返回有帧保持的异常
            EventManager.getDefault().post(new HardwareErroeEvent("向安全单元发送命令并获取安全单元返回数据失败，无法获取上行字符串upString = null，出现在A1BSafetyUnitModule类 read()方法中",
                    "data = "+data+" retData = "+retData+" mainSign = "+mainSign+" control = "+control+" upString = "+upString));
            return upFrame.getException();
        }

        int sendRet = communicate.write(upString);
        if (sendRet != 0) {
            return sendRet;
        }
        StringBuffer sb = new StringBuffer();
        int recvRet = communicate.read(sb, getReadTimeout());
        if (recvRet != 0) { // 从安全单元读取数据错误
            return recvRet;
        }

        // 判断返回的数据是否能够组成安全单元通讯下行帧
        SafetyUnitFrame downFrame = protocol.parseDown(sb.toString());
        if (downFrame.getException() != 0) {
            return downFrame.getException();
        }

        // 检测安全单元通讯下行帧是否有异常
        int check = checkDownFrameException(upFrame, downFrame);
        // 将下行帧的数据域返回
        retData.delete(0, retData.length()).append(downFrame.getData());

        if (check != 0) {
            return check;
        }

        return 0;
    }

    /**
     * 从安全单元获取请求
     * 默认的主功能标识为01否则会返回错误
     * @param mainSign 返回-主功能标识
     * @param control 返回-请求命令码
     * @param data 返回-请求数据域
     * @param status 返回-状态码
     * @return 0-成功
     *          CommonBufferError-返回值缓冲区为空
     *          CommonReadError-读取数据为空
     *          SafetyUnitError-安全单元操作设备操作失败
     *          SafetyFrameError-安全单元通讯帧格式错误
     *          SafetyFrameMatchError-上下行帧不匹配
     *          SafetyGetStatusError-安全单元下行帧返回了异常帧
     */
    synchronized public int getFrame(StringBuffer mainSign, StringBuffer control, StringBuffer status, StringBuffer data) {
        if (mainSign == null || control == null || status == null || data == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("从安全单元获取请求失败，出现在CommonSafetyUnitEssentialMethod类 getFrame()方法中",
                    "mainSign = "+mainSign+" control = "+control+" status = "+status+" data = "+data));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int recvRet = communicate.read(sb, getReadTimeout());
        if (recvRet != 0) { // 从安全单元读取数据错误
            return recvRet;
        }

        // 判断返回的数据是否能够组成安全单元通讯上行帧（请求帧）
        SafetyUnitFrame upFrame = protocol.parseUp(sb.toString());
        if (upFrame.getException() != 0) {
            return upFrame.getException();
        }

        mainSign.delete(0, mainSign.length()).append(upFrame.getMainSign());
        control.delete(0, control.length()).append(upFrame.getControl());
        status.delete(0, status.length()).append(upFrame.getStatusCode());
        data.delete(0, data.length()).append(upFrame.getData());

        return 0;
    }

    /**
     * 向安全单元发送请求响应
     * @param mainSign 发送的主功能标识
     * @param control 发送的命令码
     * @param status 状态码
     * @param data 发送数据
     * @return 0-发送成功
     *          CommonParamError-输入参数不合法
     *          CommonWriteError-数据写入错误
     *          CommonProtocolError-协议定义为空
     *          SafetyUnitError-安全单元操作设备操作失败
     *          SafetyFrameError-安全单元通讯帧格式错误
     *          SafetyFrameMatchError-上下行帧不匹配
     *
     */
    synchronized public int sendFrame(String mainSign, String control, String status, String data) {
        if (mainSign == null || mainSign.length() != 2 ||
                control == null || control.length() != 2 ||
                status == null || status.length() != 2 ||
                data == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("向安全单元发送请求响应失败，出现在CommonSafetyUnitEssentialMethod类 sendFrame()方法中",
                    "mainSign = "+mainSign+" control = "+control+" status = "+status+" data = "+data));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (protocol == null || communicate == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("向安全单元发送请求响应失败，出现在CommonSafetyUnitEssentialMethod类 sendFrame()方法中",
                    "mainSign = "+mainSign+" control = "+control+" status = "+status+" data = "+data));
            return ErrorManager.ErrorType.CommonProtocolError.getValue();
        }
        SafetyUnitFrame downFrame = getFrameNewInstance();
        downFrame.setMainSign("01");
        downFrame.setControl(control);
        downFrame.setStatusCode(status);
        downFrame.setData(data);

        String downString = downFrame.getString();
        if (downString == null) { // 无法获取发送字符串，则组帧时出现异常，返回有帧保持的异常
            EventManager.getDefault().post(new HardwareErroeEvent("向安全单元发送请求响应失败，出现在CommonSafetyUnitEssentialMethod类 sendFrame()方法中",
                    "mainSign = "+mainSign+" control = "+control+" status = "+status+" data = "+data+" downString = "+downString));
            return downFrame.getException();
        }

        return communicate.write(downString);
    }

    /**
     * 判断返回的帧是否有异常出现
     * @param upFrame 上行帧实例（请求帧）
     * @param downFrame 下行帧实例（响应帧）
     * @return 0-无异常
     *          SafetyFrameError-安全单元通讯帧格式错误
     *          SafetyFrameMatchError-上下行帧不匹配
     *          SafetyGetStatusError-安全单元下行帧返回了异常帧
     */
    public int checkDownFrameException(SafetyUnitFrame upFrame, SafetyUnitFrame downFrame) {
        // 判断输入的帧没有异常
        if (upFrame == null || downFrame == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("安全单元返回的帧有异常出现，出现在CommonSafetyUnitEssentialMethod类 checkDownFrameException()方法中",
                    "upFrame = "+upFrame+" downFrame = "+downFrame));
            return ErrorManager.ErrorType.SafetyFrameError.getValue();
        }
        if (upFrame.getException() != 0) {
            EventManager.getDefault().post(new HardwareErroeEvent("安全单元返回的帧有异常出现，出现在CommonSafetyUnitEssentialMethod类 checkDownFrameException()方法中",
                    "upFrame.getException() = "+upFrame.getException()));
            return upFrame.getException();
        }
        if (downFrame.getException() != 0) {
            EventManager.getDefault().post(new HardwareErroeEvent("安全单元返回的帧有异常出现，出现在CommonSafetyUnitEssentialMethod类 checkDownFrameException()方法中",
                    "downFrame.getException() = "+downFrame.getException()));
            return downFrame.getException();
        }

        try {
            // 判断是否为下行帧
            if (DataConvert.getMaskedValue(downFrame.getControl(), 0x80) != 0x80) {
                EventManager.getDefault().post(new HardwareErroeEvent("安全单元返回的帧有异常出现，出现在CommonSafetyUnitEssentialMethod类 checkDownFrameException()方法中",
                        "DataConvert.getMaskedValue(downFrame.getControl(), 0x80) = "+DataConvert.getMaskedValue(downFrame.getControl(), 0x80)));
                return ErrorManager.ErrorType.SafetyFrameError.getValue();
            }
            // 判断上下行帧是否匹配
            if (DataConvert.getMaskedValue(downFrame.getControl(), 0x7F) !=
                    DataConvert.getMaskedValue(upFrame.getControl(), 0x7F)) {
                EventManager.getDefault().post(new HardwareErroeEvent("安全单元返回的帧有异常出现，出现在CommonSafetyUnitEssentialMethod类 checkDownFrameException()方法中",
                        "DataConvert.getMaskedValue(downFrame.getControl(), 0x7F) = "+DataConvert.getMaskedValue(downFrame.getControl(), 0x7F)));
                return ErrorManager.ErrorType.SafetyFrameMatchError.getValue();
            }
            // 判断是否有异常信息
            if (!"00".equals(downFrame.getStatusCode())) {
                EventManager.getDefault().post(new HardwareErroeEvent("安全单元返回的帧有异常出现，出现在CommonSafetyUnitEssentialMethod类 checkDownFrameException()方法中",
                        "downFrame.getStatusCode() = "+downFrame.getStatusCode()));
                return ErrorManager.ErrorType.SafetyGetStatusError.getValue();
            }
        } catch (DataConvert.DataConvertException ex) {
            // 如果判断失败，肯定有数据错误
            return ErrorManager.ErrorType.SafetyFrameError.getValue();
        }
        return 0;
    }
}
