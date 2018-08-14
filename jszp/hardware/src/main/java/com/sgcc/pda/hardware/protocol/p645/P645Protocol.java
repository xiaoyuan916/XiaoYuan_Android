package com.sgcc.pda.hardware.protocol.p645;


import com.sgcc.pda.hardware.protocol.IBaseProtocol;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * P645协议实现
 */
public class P645Protocol implements IBaseProtocol<P645Frame> {
    private static final String FRAME_HEAD = "68";
    private static final String FRAME_TAIL = "16";

    /**
     * 从字符串中获取通讯帧实例
     * @param data 待解析字符串
     * @return 通讯帧实例 getException()==null-正确 getException()!=null-错误
     */
    @Override
    public P645Frame parseUp(String data) {
        P645Frame frame = new P645Frame(this);
        // 获取有效的通讯帧字符串
        String frameString = getFrameString(data);
        if (frameString == null) {
            // 如果返回的错误值，则将错误设置到实例的异常字段中
            frame.setException(ErrorManager.ErrorType.P645FrameError.getValue());
        } else if (frameString.equals(CHECK_ERROR)) {
            frame.setException(ErrorManager.ErrorType.P645CheckValueError.getValue());
        } else {
            // 保存表通讯地址
            frame.setMeterAddress(frameString.substring(2, 14));
            // 保存控制码
            frame.setControl(frameString.substring(16, 18));
            // 保存数据长度
            frame.setDataLength(frameString.substring(18, 20));
            // 保存数据域
            frame.setData(frameString.substring(20, frameString.length() - 4));
            // 保存校验值
            frame.setCheck(frameString.substring(frameString.length() - 4, frameString.length() - 2));
            int error = checkFrameError(frame);

            if (error != 0) {
                frame.setException(error);
            }
        }
        return frame;
    }

    /**
     * 从字符串中获取通讯帧实例
     * @param data 待解析字符串
     * @return 通讯帧实例 getException()==null-正确 getException()!=null-错误
     */
    @Override
    public P645Frame parseDown(String data) {
        P645Frame frame = parseUp(data);
        if (frame != null) {
            // 根据645规约规定，返回的数据帧的地址为反序，需要进行调换
            if (frame.getMeterAddress() != null) {
                frame.setMeterAddress(DataConvert.strReverse(frame.getMeterAddress(), 0, frame.getMeterAddress().length()));
            }
            // 根据规约需要，将数据域每个字节减去33H
            if (frame.getData() != null) {
                frame.setData(DataConvert.stringHexMinusEach(frame.getData(), (byte) 0x33));
            }
        }
        return frame;
    }

    /**
     * 获取帧实例待发送数据
     * @param frame 帧实例
     * @return null-帧不合法（错误类型见frame.getException()） 其它-待发送数据
     */
    @Override
    public String getSendData(P645Frame frame) {
        return frame.getString();
    }

    /**
     * 计算通讯帧的校验码
     * @param frame 通讯帧实例
     * @return null-帧数据不合法（错误类型见frame.getException()） 其它-校验码
     */
    @Override
    public String computeCheckValue(P645Frame frame) {
        int error = checkFrameError(frame);
        if (error != 0) {
            frame.setException(error);
            return null;
        }

        // 整合需要计算的数据
        StringBuilder sb = new StringBuilder();
        sb.append(frame.getFrameHead())
                .append(frame.getMeterAddress() == null ? "" : frame.getMeterAddress())
                .append(frame.getFrameHead())
                .append(frame.getControl() == null ? "" : frame.getControl())
                .append(frame.getDataLength() == null ? "" : frame.getDataLength())
                .append(frame.getData() == null ? "" : frame.getData());

        return DataConvert.getSumValue(sb.toString());
    }

    /**
     * 获取通讯帧数据长度字段的值
     * @param frame 通讯帧实例
     * @return null-计算长度错误 其它-数据长度
     */
    @Override
    public String computeDataLength(P645Frame frame) {
        return DataConvert.getHexLength(frame.getData(), 1);
    }

    /**
     * 获取通讯帧起始码
     * @return 起始码
     */
    @Override
    public String getFrameHead() {
        return FRAME_HEAD;
    }

    /**
     * 获取通讯帧结束码
     * @return 结束码
     */
    @Override
    public String getFrameTail() {
        return FRAME_TAIL;
    }

    /**
     * 判断帧实例数据长度是否符合要求
     * @param frame 通讯帧实例
     * @return 0-无错误
     *          P645FrameError-P645通讯帧格式错误
     *          P645DataLengthError-数据长度不符合规约要求
     */
    @Override
    public int checkFrameError(P645Frame frame) {
        // 判断电表地址是否合法
        if ((frame.getMeterAddress() == null || frame.getMeterAddress().length() != 12) ||
                // 控制字段是否合法
                (frame.getControl() == null || frame.getControl().length() != 2) ||
                // 数据长度字段是否合法
                (frame.getDataLength() == null || frame.getDataLength().length() != 2)) {
            return ErrorManager.ErrorType.P645FrameError.getValue();
        }

        int dataLength;
        try {
            dataLength = Integer.parseInt(frame.getDataLength(), 16);
        } catch (Exception e) {
            return ErrorManager.ErrorType.P645FrameError.getValue();
        }
        // 判断数据域是否合法
        if (frame.getData() == null || frame.getData().length() != dataLength * 2) {
            return ErrorManager.ErrorType.P645FrameError.getValue();
        }

        /*
        规约规定读数据时，数据域长度小于等于200
        规定写数据时，数据域长度小于等于50
        读数据控制码为0x11（读数据），0x12（读后续数据）, 0x13（读通信地址）
        写数据控制码为0x14（写数据）, 0x15（写通讯地址）
         */
        // 获取控制码值
        int mask = 0x1F;
        int control;
        try {
            control = Integer.parseInt(frame.getControl(), 16) & mask;
        } catch (Exception e) {
            return ErrorManager.ErrorType.P645FrameError.getValue();
        }

        if (control == 0x11 || control == 0x12 || control == 0x13) {
            if (dataLength > 200) {
                return ErrorManager.ErrorType.P645DataLengthError.getValue();
            }
        }

        if (control == 0x14 || control == 0x15) {
            if (dataLength > 200) {
                return ErrorManager.ErrorType.P645DataLengthError.getValue();
            }
        }
        return 0;
    }

    /**
     * 从一段字符串中获取P645规定的通讯帧
     * 完整的帧描述在P645规约文件中
     * @param data 输入字符串
     * @return 帧字符串-成功 其它字符串-错误
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
        if (data.length() % 2 == 1 || data.length() / 2 < 12) {
            return null;
        }

        int frameBegin = 0;
        String errorString = "";

        do {
            // 寻找等于起始符的起始位置
            frameBegin = data.indexOf(FRAME_HEAD, frameBegin);
            // 查询到字符串末尾，没有找到符合规约的字符串
            if (frameBegin < 0 || frameBegin + 16 >= data.length()) {
                break;
            }
            // 第8字节是否等于起始符
            if (!data.substring(frameBegin + 14, frameBegin + 16).equals(FRAME_HEAD)) {
                frameBegin++;
                continue;
            }
            // 剩余的数据是否满足最小帧长度
            if (frameBegin + 20 >= data.length()) {
                frameBegin++;
                continue;
            }

            // 获取数据域长度
            int dataLength;
            try {
                dataLength = Integer.parseInt(data.substring(frameBegin + 18, frameBegin + 20), 16);
            } catch (Exception e) {
                // 如果出现异常，表示在数据中出现了不合法的字符
                frameBegin++;
                continue;
            }
            // 剩余的数据是否满足目前数据描述的帧长度
            if (frameBegin + 20 + dataLength * 2 + 4 > data.length()) {
                // 如果出现异常，表示在数据中出现了不合法的字符
                frameBegin++;
                continue;
            }
            // 判断固定位置字符是否为结束符
            if (!data.substring(frameBegin + 20 + dataLength * 2 + 2, frameBegin + 20 + dataLength * 2 + 4)
                    .equals(FRAME_TAIL)) {
                frameBegin++;
                continue;
            }
            // 判断固定位置的校验值是否等于帧校验值
            if (!data.substring(frameBegin + 20 + dataLength * 2, frameBegin + 20 + dataLength * 2 + 2)
                    .equals(DataConvert.getSumValue(data.substring(frameBegin, frameBegin + 20 + dataLength * 2)))) {
                errorString = CHECK_ERROR;
                frameBegin++;
                continue;
            }
            return data.substring(frameBegin, frameBegin + 20 + dataLength * 2 + 4);

        } while(true);

        return errorString.equals(CHECK_ERROR) ? CHECK_ERROR : null;
    }
}
