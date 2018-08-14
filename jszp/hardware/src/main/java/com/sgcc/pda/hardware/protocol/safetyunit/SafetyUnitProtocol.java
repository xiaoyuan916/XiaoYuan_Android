package com.sgcc.pda.hardware.protocol.safetyunit;

import com.sgcc.pda.hardware.event.HardwareErroeEvent;
import com.sgcc.pda.hardware.protocol.IBaseProtocol;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.hardware.util.EventManager;

;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 安全单元协议实现
 */
public class SafetyUnitProtocol implements IBaseProtocol<SafetyUnitFrame> {
    private static final String FRAME_HEAD = "E9";
    private static final String FRAME_TAIL = "E6";

    /**
     * 从字符串中获取上行帧实例
     * @param data 待解析字符串
     * @return 通讯帧实例 getException()==null-正确 getException()!=null-错误
     */
    @Override
    public SafetyUnitFrame parseUp(String data) {
        SafetyUnitFrame frame = new SafetyUnitFrame(this);
        // 获取有效的通讯字符串
        String frameString = getFrameString(data);
        if (frameString == null ) {
            // 如果返回的错误值，则将错误值设置到实例的异常中
            EventManager.getDefault().post(new HardwareErroeEvent("从字符串中获取上行帧实例失败，出现在CommonSafetyUnitEssentialMethod类 SafetyUnitProtocol()方法中",
                    " data = "+data+" frameString = "+frameString));
            frame.setException(ErrorManager.ErrorType.SafetyFrameError.getValue());
        } else if (frameString.equals(CHECK_ERROR)) {
            // 如果返回了特殊值校验码错误，则将错误值设置到实例的异常中
            EventManager.getDefault().post(new HardwareErroeEvent("从字符串中获取上行帧实例失败，出现在CommonSafetyUnitEssentialMethod类 SafetyUnitProtocol()方法中",
                    " data = "+data+" frameString = "+frameString));
            frame.setException(ErrorManager.ErrorType.SafetyCheckValueError.getValue());
        } else {
            // 保存主功能标识
            frame.setMainSign(frameString.substring(6, 8));
            // 保存命令码
            frame.setControl(frameString.substring(8, 10));
            // 保存数据域
            frame.setData(frameString.substring(10, frameString.length() - 4));
            // 保持校验码
            frame.setCheck(frameString.substring(frameString.length() - 4, frameString.length() - 2));
            /*
             由于设置主功能标识，命令码和数据域
             都会重新计算帧长度，所以最后进行设置
             */
            frame.setDataLength(frameString.substring(2, 6));
            // 校验帧是否正确
            int error = checkFrameError(frame);
            if (error != 0) {
                frame.setException(error);
            }
        }
        return frame;

    }

    /**
     * 从字符串中获取下行帧实例
     * @param data 待解析字符串
     * @return 通讯帧实例 getException()==null-正确 getException()!=null-错误
     */
    @Override
    public SafetyUnitFrame parseDown(String data) {
        SafetyUnitFrame frame = new SafetyUnitFrame(this);
        // 获取有效的通讯字符串
        String frameString = getFrameString(data);
        if ( frameString == null || frameString.equals(CHECK_ERROR)) {
            // 如果返回的错误值，则将错误值设置到实例的异常中
            EventManager.getDefault().post(new HardwareErroeEvent("从字符串中获取下行帧实例失败，出现在SafetyUnitProtocol类 parseDown()方法中",
                    "data = "+data+" frameString = "+frameString));
            frame.setException(ErrorManager.ErrorType.SafetyFrameError.getValue());
        } else {
            // 保存主功能标识
            frame.setMainSign(frameString.substring(6, 8));
            // 保存响应码
            frame.setControl(frameString.substring(8, 10));
            // 保存状态码
            frame.setStatusCode(frameString.substring(10, 12));
            // 保存数据域
            frame.setData(frameString.substring(12, frameString.length() - 4));
            // 保持校验码
            frame.setCheck(frameString.substring(frameString.length() - 4, frameString.length() - 2));

            /*
             由于设置主功能标识，响应码，状态码和数据域
             都会重新计算帧长度，所以最后进行设置
             */
            frame.setDataLength(frameString.substring(2, 6));
            // 校验帧是否正确
            int error = checkFrameError(frame);
            if (error != 0) {
                frame.setException(error);
            }
        }
        return frame;
    }

    /**
     * 获取待发送的HEX字符串
     * @param frame 通讯帧实例
     * @return null-错误（通过frame.getException()获取） 其它-返回的HEX字符串
     */
    @Override
    public String getSendData(SafetyUnitFrame frame) {
        return frame.getString();
    }

    /**
     * 计算帧的校验码
     * @param frame 通讯帧实例
     * @return 校验码值
     */
    @Override
    public String computeCheckValue(SafetyUnitFrame frame) {
        int error = checkFrameError(frame);
        if (error != 0) {
            frame.setException(error);
            return null;
        }

        // 整合需要计算的数据
        StringBuilder sb = new StringBuilder();
        sb.append(frame.getFrameHead() == null ? "" : frame.getFrameHead())
                .append(frame.getDataLength() == null ? "" : frame.getDataLength())
                .append(frame.getMainSign() == null ? "" : frame.getMainSign())
                .append(frame.getControl() == null ? "" : frame.getControl())
                .append(frame.getStatusCode() == null ? "" : frame.getStatusCode())
                .append(frame.getData() == null ? "" : frame.getData());

         return DataConvert.getSumValue(sb.toString());
    }



    /**
     * 获取帧长度字段值
     * @param frame 通讯帧实例
     * @return null-计算错误 其它-帧长度字段值
     */
    @Override
    public String computeDataLength(SafetyUnitFrame frame) {
        return DataConvert.getHexLength((frame.getMainSign() == null ? "" : frame.getMainSign()) +
                        (frame.getControl() == null ? "" : frame.getControl()) +
                        (frame.getStatusCode() == null ? "" : frame.getStatusCode()) +
                        (frame.getData() == null ? "" : frame.getData()),
                2);
    }

    /**
     * 获取协议起始码
     * @return 起始码
     */
    @Override
    public String getFrameHead() {
        return FRAME_HEAD;
    }

    /**
     * 获取协议结束码
     * @return 结束码
     */
    @Override
    public String getFrameTail() {
        return FRAME_TAIL;
    }

    /**
     * 判断帧实例数据是否符合规约要求
     * @param frame 通讯帧实例
     * @return 0-无错误 SafetyFrameError-安全单元通讯帧格式错误
     */
    @Override
    public int checkFrameError(SafetyUnitFrame frame) {
        // 判断主功能标识是否合法
        if ((frame.getMainSign() == null || frame.getMainSign().length() != 2) ||
                // 判断控制码是否合法
                (frame.getControl() == null || frame.getControl().length() != 2) ||
                // 判断状态码是否合法
                (frame.getStatusCode() != null && !frame.getStatusCode().equals("")
                        && frame.getStatusCode().length() != 2) ||
                // 判断数据长度与是否合法
                (frame.getDataLength() == null || frame.getDataLength().length() != 4)) {
            EventManager.getDefault().post(new HardwareErroeEvent("帧实例数据不符合规约要求，出现在SafetyUnitProtocol类 checkFrameError()方法中",
                    "frame.getMainSign() = "+frame.getMainSign()));
            return ErrorManager.ErrorType.SafetyFrameError.getValue();
        }

        // 判断数据长度值是否合法
        int dataLength;
        try {
            dataLength = Integer.parseInt(frame.getDataLength(), 16);
        } catch (Exception e) {
            return ErrorManager.ErrorType.SafetyFrameError.getValue();
        }

        // 判断帧字段值是否合法
        int totalStringLength = frame.getMainSign().length() + // 主功能标识长度
                frame.getControl().length() + // 命令码长度
                (frame.getStatusCode() == null ? 0 : frame.getStatusCode().length()) + // 响应码长度（如果存在）
                (frame.getData() == null ? 0 : frame.getData().length()); // 数据域长度（如果存在）
        if (totalStringLength != dataLength * 2) {
            EventManager.getDefault().post(new HardwareErroeEvent("帧实例数据不符合规约要求，出现在SafetyUnitProtocol类 checkFrameError()方法中",
                    "totalStringLength = "+totalStringLength));
            return ErrorManager.ErrorType.SafetyFrameError.getValue();
        }

        return 0;
    }

    /**
     * 从一段字符串中获取安全单元规定的通讯帧
     * 完整的帧描述在安全单元规约文件中
     * @param data 输入字符串
     * @return 帧字符串-成功 CHECK_ERROR-数据格式正确但校验值错误 null-帧格式错误
     */
    private String getFrameString(String data) {
        // 消除空格的干扰
        data = data.trim();
        // 将字母都转化为大写
        data = data.toUpperCase();

        if (!data.contains(FRAME_HEAD)) {
            return null;
        }
        if (!data.contains(FRAME_TAIL)) {
            return null;
        }
        if (data.length() % 2 == 1 || data.length() < 12) {
            return null;
        }

        int frameBegin = 0;
        String errorString = "";

        do {
            // 寻找等于起始符的起始位置
            frameBegin = data.indexOf(FRAME_HEAD, frameBegin);

            // 查询到字符串末尾，没有找到符合规约的字符串
            if (frameBegin < 0 || frameBegin + 12 >= data.length()) {
                break;
            }

            // 获取数据域长度
            int dataLength;
            try {
                dataLength = Integer.parseInt(data.substring(frameBegin + 2, frameBegin + 6), 16);
            } catch (Exception e) {
                // 如果出现异常，表示在数据中出现了不合法的字符
                frameBegin++;
                continue;
            }

            if (dataLength < 2) { // 如果帧长度不满足最小长度
                frameBegin++;
                continue;
            }

            // 剩余的数据是否满足目前数据描述的帧长度
            if (frameBegin + 6 + dataLength * 2 + 4 > data.length()) {
                // 如果出现异常，表示帧长度字符错误
                frameBegin++;
                continue;
            }

            // 判断固定位置字符是否为结束符
            if (!data.substring(frameBegin + 6 + dataLength * 2 + 2, frameBegin + 6 + dataLength * 2 + 4)
                    .equals(FRAME_TAIL)) {
                frameBegin++;
                continue;
            }

            // 判断固定位置的校验值是否等于帧校验值
            if (!data.substring(frameBegin + 6 + dataLength * 2, frameBegin + 6 + dataLength * 2 + 2).toUpperCase()
                    .equals(DataConvert.getSumValue(data.substring(frameBegin, frameBegin + 6 + dataLength * 2)).toUpperCase())) {
                errorString = CHECK_ERROR;
                frameBegin++;
                continue;
            }
            return data.substring(frameBegin, frameBegin + 6 + dataLength * 2 + 4);

        } while(true);

        return errorString.equals(CHECK_ERROR) ? CHECK_ERROR : null;
    }
}
