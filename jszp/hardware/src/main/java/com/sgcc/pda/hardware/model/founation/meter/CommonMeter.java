package com.sgcc.pda.hardware.model.founation.meter;


import com.sgcc.pda.hardware.protocol.p645.IP645EssentialMethod;
import com.sgcc.pda.hardware.protocol.p645.P645Frame;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.sdk.utils.LogUtil;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 默认红外功能操作类
 */
public class CommonMeter implements IMeter {
    private IP645EssentialMethod<P645Frame> method = null;

    public CommonMeter(IP645EssentialMethod m) {
        method = m;
    }

    /**
     * 向电表发起红外认证请求
     *
     * @param meterAddress 表通讯地址
     * @param random1      安全单元返回的随机数1
     * @param operator     操作员编号
     * @param meterNo      返回-表号
     * @param esamNo       返回-ESAM模块编号
     * @param enRandom1    返回-经过ESAM模块加密的随机数1密文
     * @param random2      返回-随机数2
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int infraIdentityReq(String meterAddress, String random1, String operator, StringBuffer meterNo,
                                StringBuffer esamNo, StringBuffer enRandom1, StringBuffer random2) {
        if (meterNo == null || esamNo == null || enRandom1 == null || random2 == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer retData = new StringBuffer();
        // 调用安全认证命令
        // 根据规约规定，将数据标识，操作员以及随机数进行反序
        int ret = method.safetyCertification(meterAddress, DataConvert.strReverse("078003FF", 0, 8),
                DataConvert.strReverse(operator, 0, operator.length()),
                DataConvert.strReverse(random1, 0, random1.length()), retData);
        if (ret != 0) {
            return ret;
        }
        if (retData.length() != 68) {
            return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
        }
        // 根据规约规定，将返回值中的数据按照数据含义进行反序
        meterNo.delete(0, meterNo.length()).append(DataConvert.strReverse(retData.substring(8, 20), 0, 12));
        esamNo.delete(0, esamNo.length()).append(DataConvert.strReverse(retData.substring(20, 36), 0, 16));
        enRandom1.delete(0, enRandom1.length()).append(DataConvert.strReverse(retData.substring(36, 52), 0, 16));
        random2.delete(0, random2.length()).append(DataConvert.strReverse(retData.substring(52, 68), 0, 16));
        return 0;
    }

    /**
     * 电表执行红外认证
     *
     * @param meterAddress 表通讯地址
     * @param enRandom2    经安全单元加密的随机数2密文
     * @param operator     操作员编号
     * @return 0-认证成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int infraIdentity(String meterAddress, String enRandom2, String operator) {
        StringBuffer retData = new StringBuffer();
        // 调用安全认证命令
        return method.safetyCertification(meterAddress, DataConvert.strReverse("070003FF", 0, 8),
                DataConvert.strReverse(operator, 0, operator.length()),
                DataConvert.strReverse(enRandom2, 0, enRandom2.length()), retData);
    }

    /**
     * 电表执行远程身份认证
     *
     * @param meterAddress 表通讯地址
     * @param cipher       安全单元返回的密文
     * @param random1      安全单元返回的随机数1
     * @param meterNo      电表表号
     * @param operator     操作员编号
     * @param random2      返回-随机数2
     * @param esamNo       返回-ESAM模块编号
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int identityAuthentication(String meterAddress, String cipher, String random1, String meterNo,
                                      String operator, StringBuffer random2, StringBuffer esamNo) {
        StringBuffer retData = new StringBuffer();

        if (esamNo == null || random2 == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        // 处理传入数据
        cipher = DataConvert.getFixedStringWithChar(cipher, 16, false, '0');
        random1 = DataConvert.getFixedStringWithChar(random1, 16, false, '0');
        meterNo = DataConvert.getFixedStringWithChar(meterNo, 16, true, '0');

        // 调用安全认证命令
        // 将数据反向后传入
        int ret = method.safetyCertification(meterAddress, DataConvert.strReverse("070000FF", 0, 8),
                DataConvert.strReverse(operator, 0, operator.length()),
                // 测试数据反向
                DataConvert.strReverse(cipher, 0, 16) + DataConvert.strReverse(random1, 0, random1.length()) +
                        DataConvert.strReverse(meterNo, 0, meterNo.length()),
                retData);
        if (ret != 0) {
            return ret;
        }
        if (retData.length() != 32) {
            return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
        }
        random2.delete(0, random2.length()).append(DataConvert.strReverse(retData.substring(8, 16), 0, 8));
//        random2.delete(0, random2.length()).append(retData.substring(8, 16));
        esamNo.delete(0, esamNo.length()).append(DataConvert.strReverse(retData.substring(16, 32), 0, 16));
        return 0;
    }

    /**
     * 向电表写入第一类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @param mac          安全单元计算的MAC
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int writeClass1Data(String meterAddress, String dataSign, String password, String operator,
                               String data, String mac) {
        StringBuffer sb = new StringBuffer();
        // 处理传入数据
        dataSign = DataConvert.getFixedStringWithChar(dataSign, 8, false, '0');

        // 调用数据写入命令
        return method.writeData(meterAddress, dataSign, "99", password, operator, data, mac, sb);
    }

    /**
     * 向电表写入第二类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @param mac          安全单元计算的MAC
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int writeClass2Data(String meterAddress, String dataSign, String password, String operator,
                               String data, String mac) {
        StringBuffer sb = new StringBuffer();
        // 处理传入数据
        dataSign = DataConvert.getFixedStringWithChar(dataSign, 8, false, '0');

        // 调用数据写入命令q
        return method.writeData(meterAddress, dataSign, "98", password, operator, data, mac, sb);
    }

    /**
     * 向电表写入第三类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int writeClass3Data(String meterAddress, String dataSign, String password, String operator,
                               String data) {
        StringBuffer sb = new StringBuffer();
        // 处理传入数据
        dataSign = DataConvert.getFixedStringWithChar(dataSign, 8, false, '0');

        // 调用数据写入命令
        return method.writeData(meterAddress, dataSign, "02", password, operator, data, null, sb);
    }

    /**
     * 广播校时
     * 时间格式为ssMMhhddmmyy
     *
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int broadcastTiming() {
        String sendTimeString = "";
        Calendar now = Calendar.getInstance();
        sendTimeString += DataConvert.toHexString(now.get(Calendar.SECOND), 1);
        sendTimeString += DataConvert.toHexString(now.get(Calendar.MINUTE), 1);
        // 24小时制
        sendTimeString += DataConvert.toHexString(now.get(Calendar.HOUR_OF_DAY), 1);
        sendTimeString += DataConvert.toHexString(now.get(Calendar.DAY_OF_MONTH), 1);
        sendTimeString += DataConvert.toHexString(now.get(Calendar.MONTH), 1);
        sendTimeString += DataConvert.toHexString(now.get(Calendar.YEAR) % 100, 1);

        return method.broadcast(sendTimeString);
    }

    /**
     * 设置电表时间
     *
     * @param meterAddress 电表通讯地址
     * @param time         设置的时间字符串ssMMhhwwddmmyy（秒分时周日月年）
     * @param password     密码
     * @param operator     操作员编号
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int Timing(String meterAddress, String keyLevel, String time, String password, String operator) {
        StringBuffer sb = new StringBuffer();
        // 首先进行日期设置
        int ret = method.writeData(meterAddress, "04000101", keyLevel, password, operator, time.substring(6, 14), null, sb);
        // 如果日期设置正确，再进行时间设置
        if (ret != 0) {
            return ret;
        } else {
            return method.writeData(meterAddress, "04000102", keyLevel, password, operator, time.substring(0, 6), null, sb);
        }
    }

    /**
     * 设置客户编号
     *
     * @param meterAddress 电表通讯地址
     * @param consNo       客户编号
     * @param password     密码
     * @param operator     操作员编号
     * @param mac          安全单元计算MAC
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int writeConsNo(String meterAddress, String consNo, String password, String operator,
                           String mac) {
        StringBuffer sb = new StringBuffer();
        return method.writeData(meterAddress, "0400040E", "99", password, operator, consNo, mac, sb);
    }

    /**
     * 获取表地址
     *
     * @param cover        表地址掩码
     * @param meterAddress 返回-表地址
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getMeterAddress(String cover, StringBuffer meterAddress) {
        if (meterAddress == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        meterAddress.delete(0, meterAddress.length());
        // 处理表地址掩码，长度小于12则左补A，长度大于12则截取12
        cover = DataConvert.getFixedStringWithChar(cover, 12, true, 'A');
        StringBuffer sb = new StringBuffer();
        // 调用通用通讯方法设置控制码
        int ret = method.commonCommunicate(cover, "13", null, sb);
        if (ret == 0) { // 如果有返回值，则对返回的表地址进行处理，补足12位
            if (sb.length() == 20) { // 判断电表是否返回了前缀
                meterAddress.append(sb.toString().substring(8, 20));
            } else {
                meterAddress.append(DataConvert.getFixedStringWithChar(sb.toString(), 12, true, '0'));
            }
        }
        return ret;
    }

    /**
     * 获取表号
     *
     * @param meterAddress 电表通讯地址
     * @param meterNo      返回-电表表号
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getMeterNo(String meterAddress, StringBuffer meterNo) {
        if (meterNo == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        meterNo.delete(0, meterNo.length());

        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, "04000402", sb);
        if (ret == 0) {
            if (sb.length() == 20) { // P645会返回命令标识+电表表号
                meterNo.append(sb.substring(8, 20));
            } else {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        return ret;
    }

    /**
     * 获取户号
     *
     * @param meterAddress 电表通讯地址
     * @param consNo       返回-户号
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getConsNo(String meterAddress, StringBuffer consNo) {
        if (consNo == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        consNo.delete(0, consNo.length());

        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, "0400040E", sb);
        if (ret == 0) {
            if (sb.length() == 20) { // P645会返回命令标识+电表用户户号
                consNo.append(sb.substring(8, 20));
            } else {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        return ret;
    }

    /**
     * 读取电表档案信息
     */
    public int Connect(String meterAddress, StringBuffer stringBuffer,String comm_code) {
        if (stringBuffer == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        stringBuffer.delete(0, stringBuffer.length());
        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, comm_code, sb);
        if (ret == 0) {
            if (sb.length() == 20) { // P645会返回命令标识+电表用户户号
                sb.append(sb.substring(8, 20));
            } else {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        return ret;

    }




    /**
     * 获取电表时间
     *
     * @param meterAddress 电表通讯地址
     * @param time         返回-电表时间，格式为：yyyymmddhhMMss
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getMeterTime(String meterAddress, StringBuffer time) {
        if (time == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        time.delete(0, time.length());

        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, "04000101", sb); // 获取电表日期
        if (ret == 0) {
            if (sb.length() == 16) { // P645会返回命令标识+电表日期，数据第一字节为星期信息，舍去
                time.append("20");
                time.append(DataConvert.strReverse(sb.substring(10, 16), 0, 6));
            } else {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        sb.delete(0, sb.length());
        ret = method.readData(meterAddress, "04000102", sb); // 获取电表时间
        if (ret == 0) {
            if (sb.length() == 14) {
                time.append(DataConvert.strReverse(sb.substring(8, 14), 0, 6));
            } else {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        return ret;
    }


    /**
     * 读取电表时段数
     * @param meterAddress  通信地址
     * @param datasign   数据标识
     * @param amount    返回 - 电表时段数
     * @return
     */
    public int readMeterPriodAmount(String meterAddress, String datasign, StringBuffer amount) {
        if (amount == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        amount.delete(0, amount.length());

        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, datasign, sb); // 获取电表时段数
        if (ret == 0) {
            if (sb.length() == 10) { // P645会返回命令标识+电表日期，数据第一字节为星期信息，舍去
                amount.append(DataConvert.strReverse(sb.substring(8, 10), 0, 2));
            } else {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        return ret;
    }



    /**
     * 获取电表当前电能数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     要读取的数据标识
     * @param data         返回-电能数据
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    private int getPowerData(String meterAddress, String dataSign, List<String> data) {
        if (data == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        data.clear();
        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, dataSign, sb);
        if (ret == 0) {
            if (sb.length() < 16) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            // 按照XXXXXX.XX的格式保存电能数据
            DecimalFormat df = new DecimalFormat("0.00");
            for (int i = 1; i < sb.length() / 8; i++) {
                String tmp = DataConvert.strReverse(sb.substring(i * 8, (i + 1) * 8), 0, 8);
                if (tmp == null) {
                    return ErrorManager.ErrorType.P645ReceivedErrorValue.getValue();
                }
                try {
                    float f = Integer.parseInt(tmp) / 100;
                    data.add(df.format(f));
                } catch (Exception e) {
                    return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
                }
            }
        }
        return ret;
    }

    /**
     * 获取所有正向有功电能
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-正向有功电能列表
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int getAllPaPower(String meterAddress, List<String> data) {
        return getPowerData(meterAddress, "0001FF00", data);
    }

    /**
     * 获取所有反向有功电能
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-反向有功电能列表
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int getAllNaPower(String meterAddress, List<String> data) {
        return getPowerData(meterAddress, "0002FF00", data);
    }

    /**
     * 获取所有正向无功（组合无功1）电能
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-正向无功（组合无功1）电能列表
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int getAllPrPower(String meterAddress, List<String> data) {
        return getPowerData(meterAddress, "0003FF00", data);
    }


    /**
     * 获取所有反向无功（组合无功2）电能
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-反向无功（组合无功2）电能列表
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int getAllNrPower(String meterAddress, List<String> data) {
        return getPowerData(meterAddress, "0004FF00", data);
    }

    /**
     * 获取最大需量及发生时间
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-最大需量 格式为XX.XXXX
     * @param time         返回-最大需量发生时间 格式为yyyymmddhhMM
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getMaxDemandAndTime(String meterAddress, StringBuffer data, StringBuffer time) {
        if (data == null || time == null) {

            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        data.delete(0, data.length());
        time.delete(0, time.length());

        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, "01010100", sb);
        if (ret == 0) {
            // 获取最大需量数值
            if (sb.length() != 24) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            DecimalFormat df = new DecimalFormat("0.00");
            String tmpString = DataConvert.strReverse(sb.substring(8, 14), 0, 6);
            if (tmpString == null) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            float demand = Integer.parseInt(tmpString) / 10000;
            data.append(df.format(demand));

            time.append("20");
            time.append(DataConvert.strReverse(sb.substring(14, 24), 0, 10));
        }
        return ret;
    }

    /**
     * 获取日冻结时间点
     *
     * @param meterAddress 电表通讯地址
     * @param time         返回-日冻结时间点 格式hhMM
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getFreezenDayTime(String meterAddress, StringBuffer time) {
        if (time == null) {

            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        time.delete(0, time.length());

        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, "04001203", sb);
        if (ret == 0) {
            if (sb.length() < 12) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            // 取字节数组4-5两个字节
            time.append(DataConvert.strReverse(sb.substring(7, 12), 0, 4));
        }
        return ret;
    }

    /**
     * 获取上N次冻结日期
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param date         返回-冻结日期 格式：yyyymmddhhMM
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getFreezeDate(String meterAddress, String dayIndex, StringBuffer date) {
        LogUtil.d("XianChangBuChaoASyncTask", dayIndex);
        if (date == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        date.delete(0, date.length());

        int day;
        try {
            day = Integer.parseInt(dayIndex);
        } catch (Exception e) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
//        if (day < 1 || day < 0x3E) {
//            return ErrorManager.ErrorType.CommonParamError.getValue();
//        }
        //wangguangjie 1003
        if (day < 1 || day > 0x3E) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        int baseDataSign = 0x05060000;

        StringBuffer sb = new StringBuffer();
        ;
//        int ret = method.readData(meterAddress, Integer.toHexString(baseDataSign + day), sb);
        //wangguangjie 1003
        int ret = method.readData(meterAddress, DataConvert.toHexString(baseDataSign + day, 4), sb);
        if (ret == 0) {
            if (sb.length() < 18) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            // 取字节数组4-8共5字节
            date.append("20");
            date.append(DataConvert.strReverse(sb.substring(8, 18), 0, 10));
        } else {
            LogUtil.d("XianChangBuChaoASyncTask", "method.readData ret = " + ret);
        }
        return ret;
    }

    /**
     * 读取电表冻结数据信息
     *
     * @param meterAddress 电表表地址
     * @param dayIndex     日期索引（第几次冻结）
     * @param dataSignBase 要读取的冻结数据数据标识
     * @param data         返回-冻结数据信息
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    private int getFreezePowerData(String meterAddress, String dayIndex, int dataSignBase, List<String> data) {
        if (data == null) {

            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        data.clear();

        // 判断日期索引是否超过了规定
        // 规定为1-62天
        int day;
        try {
            day = Integer.parseInt(dayIndex, 16);
        } catch (Exception e) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (day < 1 || day < 0x3E) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.readData(meterAddress, Integer.toHexString(dataSignBase + day), sb);
        if (ret == 0) {
            if (sb.length() < 16) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            // 按照XXXXXX.XX的格式保存冻结数据
            DecimalFormat df = new DecimalFormat("0.00");
            for (int i = 1; i < sb.length() / 8; i++) {
                String tmp = DataConvert.strReverse(sb.substring(i * 8, (i + 1) * 8), 0, 8);
                if (tmp == null) {
                    return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
                }
                try {
                    float f = Integer.parseInt(tmp) / 100;
                    data.add(df.format(f));
                } catch (Exception e) {
                    return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
                }
            }
        }
        return ret;
    }

    /**
     * 读取日冻结正向有功电能
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param data         返回-日冻结正向有功电能列表
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int getFreezeDayPaPower(String meterAddress, String dayIndex, List<String> data) {
        return getFreezePowerData(meterAddress, dayIndex, 0x05060100, data);
    }

    /**
     * 读取日冻结反向有功电能
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param data         返回-日冻结反向有功电能列表
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getFreezeDayNaPower(String meterAddress, String dayIndex, List<String> data) {
        return getFreezePowerData(meterAddress, dayIndex, 0x05060200, data);
    }

    /**
     * 读取日冻结正向无功(组合无功1)电能
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param data         返回-日冻结正向无功(组合无功1)电能
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getFreezeDayPrPower(String meterAddress, String dayIndex, List<String> data) {
        return getFreezePowerData(meterAddress, dayIndex, 0x05060300, data);
    }

    /**
     * 读取日冻结反向无功(组合无功2)电能
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param data         返回-日冻结反向无功(组合无功2)电能
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int getFreezeDayNrPower(String meterAddress, String dayIndex, List<String> data) {
        return getFreezePowerData(meterAddress, dayIndex, 0x05060400, data);
    }

    /**
     * 更新密钥
     *
     * @param meterAddress 电表通讯地址
     * @param datasign     密钥类型
     * @param mac          安全单元计算MAC
     * @param info         密钥信息
     * @param cipher   密钥密文数据
     * @param operator     操作员编号
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int keyUpdate(String meterAddress, String datasign, String mac, String info, String cipher,
                         String operator) {
        if (datasign.length() != 8 || mac.length() != 8 || info.length() != 8 || cipher.length() != 64) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        StringBuffer sb = new StringBuffer();
//        return method.safetyCertification(meterAddress, datasign, operator, DataConvert.strReverse(cipher + info + mac,0,(cipher + info + mac).length()), sb);
        return method.safetyCertification(meterAddress, datasign, operator, info + mac + cipher, sb);
//        return method.safetyCertification(meterAddress, datasign, operator, cipher + info + mac, sb);
    }

    public int keyUpdate(String meterAddress, String datasign, String msg, String cipher,
                         String operator) {
        if (datasign.length() != 8 || msg.length() != 16 || cipher.length() != 64) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        StringBuffer sb = new StringBuffer();
//        return method.safetyCertification(meterAddress, datasign, operator, msg + cipher, sb);
        return method.safetyCertification(meterAddress, datasign, operator, cipher + msg, sb);
    }

    /**
     * 远程充值并开户
     *
     * @param meterAddress 电表通讯地址
     * @param money        充值金额,以元为单位
     * @param time         充值次数
     * @param mac1         安全单元计算的金额与次数MAC
     * @param consNo       客户编号
     * @param mac2         安全单元计算客户编号MAC
     * @param operator     操作员编号
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int recharge(String meterAddress, String money, String time, String mac1, String consNo,
                        String mac2, String operator) {
        if (money.length() != 8 || time.length() != 8 || mac1.length() != 8 || consNo.length() != 12 || mac2.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        int iTime;
        try {
            iTime = Integer.parseInt(time,16);
        } catch (Exception ex) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        // 根据规约规定， 各个数据项均需要进行倒转
        money = DataConvert.strReverse(money, 0, money.length());
        time = DataConvert.strReverse(time, 0, time.length());
        mac1 = DataConvert.strReverse(mac1, 0, mac1.length());
        consNo = DataConvert.strReverse(consNo, 0, consNo.length());
        mac2 = DataConvert.strReverse(mac2, 0, mac2.length());

        StringBuffer sb = new StringBuffer();
        String taskData = iTime == 1 ? "070101FF" : "070102FF";

        return method.safetyCertification(meterAddress, DataConvert.strReverse(taskData, 0, taskData.length()), DataConvert.strReverse(operator,0,operator.length()),
                money + time + mac1 + consNo + mac2, sb);
//        return method.safetyCertification(meterAddress, DataConvert.strReverse(taskData, 0, taskData.length()), operator,
//                money + time + mac1 + consNo + mac2, sb);
    }

    public int recharge(String meterAddress, String operator, String chargeData) {
        if (operator.length() != 8 || chargeData.length() != 44 ) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        String intoCount = chargeData.substring(8, 16);
        int iTime;
        try {
            iTime = Integer.parseInt(intoCount);
        } catch (Exception ex) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

       /* // 根据规约规定， 各个数据项均需要进行倒转
        money = DataConvert.strReverse(money, 0, money.length());
        time = DataConvert.strReverse(time, 0, time.length());
        mac1 = DataConvert.strReverse(mac1, 0, mac1.length());
        consNo = DataConvert.strReverse(consNo, 0, consNo.length());
        mac2 = DataConvert.strReverse(mac2, 0, mac2.length());*/

        StringBuffer sb = new StringBuffer();
        String taskData = iTime == 1 ? "070101FF" : "070102FF";

//        return method.safetyCertification(meterAddress, DataConvert.strReverse(taskData, 0, taskData.length()), DataConvert.strReverse(operator,0,operator.length()),
//                DataConvert.strReverse(chargeData,0,chargeData.length()), sb);
        return method.safetyCertification(meterAddress, DataConvert.strReverse(taskData, 0, taskData.length()), DataConvert.strReverse(operator,0,operator.length()),
                chargeData, sb);
    }

    /**
     * 事件清零
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param keyLevel     密级
     * @param password     密码
     * @param operator     操作员编号
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int eventClear(String meterAddress, String dataSign, String keyLevel, String password, String operator) {
        if (!"02".equals(keyLevel) || !"04".equals(keyLevel)) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (dataSign.length() != 8 || password.length() != 6 || operator.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();

        return method.commonCommunicate(meterAddress, "1B", dataSign + password + operator, sb);
    }

    /**
     * 电表清零
     *
     * @param meterAddress 电表通讯地址
     * @param keyLevel     密级
     * @param password     密码
     * @param operator     操作员编号
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int meterClear(String meterAddress, String keyLevel, String password, String operator) {
        if (!"02".equals(keyLevel) || !"04".equals(keyLevel)) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (password.length() != 6 || operator.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();

        return method.commonCommunicate(meterAddress, "1A", password + operator, sb);
    }

    /**
     * 设置电表当前通讯速率为其它标准速率
     *
     * @param meterAddress 电表通讯地址
     * @param baud         波特率字符串
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int setTbtl(String meterAddress, String baud) {
        String baudString = DataConvert.getBaudValue(baud);
        if (baudString == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        return method.commonCommunicate(meterAddress, "17", baudString, sb);
    }

    /**
     * 写电表通讯地址
     *
     * @param meterAddress    电表通讯地址
     * @param newMeterAddress 电表新通讯地址
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int writeMeterAddress(String meterAddress, String newMeterAddress) {
        if (newMeterAddress.length() != 12) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        return method.commonCommunicate("999999999999", "15", newMeterAddress, sb);
    }

    /**
     * 获取费率信息
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     读取费率数据标识
     * @param index        费率序号
     * @param rate         返回-费率信息
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    private int readRate(String meterAddress, String dataSign, String index, StringBuffer rate) {
        if (rate == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        rate.delete(0, rate.length());
        StringBuffer sb = new StringBuffer();

        int iDataSign;
        int iIndex;
        try {
            iDataSign = Integer.parseInt(dataSign, 16);
            iIndex = Integer.parseInt(index, 16);
        } catch (Exception e) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (iIndex < 1 || iIndex > 63) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        int ret = method.readData(meterAddress, Integer.toHexString(iDataSign + iIndex), sb);
        if (ret == 0) {
            if (sb.length() < 16) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            // 按照XXXX.XXXX的格式保存费率数据
            DecimalFormat df = new DecimalFormat("0.0000");
            String tmp = DataConvert.strReverse(sb.substring(8, 16), 0, 8);
            if (tmp == null) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            try {
                float f = Integer.parseInt(tmp) / 10000;
                rate.append(df.format(f));
            } catch (Exception e) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        return ret;
    }

    /**
     * 读取电表第一套费率(当前套)
     *
     * @param meterAddress 电表通讯地址
     * @param rateIndex    费率序号
     * @param rate         返回-费率列表
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int readRate1(String meterAddress, String rateIndex, StringBuffer rate) {
        return readRate(meterAddress, "04050100", rateIndex, rate);
    }

    /**
     * 读取电表第二套费率(备用套)
     *
     * @param meterAddress 电表通讯地址
     * @param rateIndex    费率序号
     * @param rate         返回-费率列表
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     */
    public int readRate2(String meterAddress, String rateIndex, StringBuffer rate) {
        return readRate(meterAddress, "04050200", rateIndex, rate);
    }

    /**
     * 读取电表余额
     *
     * @param meterAddress 电表通讯地址
     * @param amount       返回-电表余额
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int readResidualAmount(String meterAddress, StringBuffer amount) {
        if (amount == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        amount.delete(0, amount.length());
        StringBuffer sb = new StringBuffer();

        int ret = method.readData(meterAddress, "00900200", sb);
        if (ret == 0) {
            if (sb.length() < 16) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            // 按照XXXXXX.XX的格式保存费率数据
            DecimalFormat df = new DecimalFormat("0.00");
            String tmp = DataConvert.strReverse(sb.substring(8, 16), 0, 8);
            if (tmp == null) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            try {
                float f = Integer.parseInt(tmp) / 100;
                amount.append(df.format(f));
            } catch (Exception e) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        return ret;
    }

    @Override
    public int readRechargeInfo(String meterAddress, String operator, StringBuffer surplusAmount, StringBuffer rechargeTimes, StringBuffer customCode) {
        if (surplusAmount == null || rechargeTimes == null || customCode == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer retData = new StringBuffer();
        // 调用安全认证命令
        // 根据规约规定，将数据标识，操作员以及随机数进行反序
        int ret = method.safetyCertification(meterAddress, DataConvert.strReverse("078102FF", 0, 8),
                DataConvert.strReverse(operator, 0, operator.length()),
                null, retData);
        if (ret != 0) {
            return ret;
        }
        if (retData.length() != 60) {
            return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
        }
        // 根据规约规定，将返回值中的数据按照数据含义进行反序
        String surplusMoeny = DataConvert.strReverse(retData.substring(8, 16), 0, 8);
        int rechargeTime = Integer.parseInt(DataConvert.strReverse(retData.substring(24, 32), 0, 8),16);
        String customCod = DataConvert.strReverse(retData.substring(40, 52), 0, 12);
        double oldMoney = Integer.parseInt(surplusMoeny, 16) / 100.0;
        surplusAmount.delete(0, surplusAmount.length()).append(new DecimalFormat("#.00").format(oldMoney));
        rechargeTimes.delete(0, rechargeTimes.length()).append(rechargeTime);
        customCode.delete(0, customCode.length()).append(customCod);
        return 0;
    }


    /**
     * 读取电表透支金额
     *
     * @param meterAddress 电表通讯地址
     * @param amount       返回-电表透支金额
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int readOverdrawAmount(String meterAddress, StringBuffer amount) {
        if (amount == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        amount.delete(0, amount.length());
        StringBuffer sb = new StringBuffer();

        int ret = method.readData(meterAddress, "00900201", sb);
        if (ret == 0) {
            if (sb.length() < 16) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            // 按照XXXXXX.XX的格式保存费率数据
            DecimalFormat df = new DecimalFormat("0.00");
            String tmp = DataConvert.strReverse(sb.substring(8, 16), 0, 8);
            if (tmp == null) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
            try {
                float f = Integer.parseInt(tmp) / 100;
                amount.append(df.format(f));
            } catch (Exception e) {
                return ErrorManager.ErrorType.P645ReceivedDataError.getValue();
            }
        }
        return ret;
    }

    /**
     * 执行电表控制功能
     *
     * @param meterAddress 电表通讯地址
     * @param operator     操作员
     * @param cipherText   电表控制数据
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int powerOperate(String meterAddress, String operator, String cipherText) {
        StringBuffer sb = new StringBuffer();
        return method.powerOperate(meterAddress, "98", operator, null, cipherText, sb);
    }

    /**
     * 根据数据标识读取电表数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retData      返回-读取到的返回值
     * @return 0-成功
     * CommonParamError-输入参数错误
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * CommonReadError-数据读取错误
     * InfraError-红外设备操作失败
     * P645FrameError-P645帧格式错误
     * P645FrameMatchError-上下行帧不匹配
     * P645ReceivedExceptionValue-P645接收了一个包含异常信息的返回帧
     * P645DataLengthError-数据长度不符合规约要求
     * P645ReceivedDataError-P645接收到的数据错误
     */
    public int commonRead(String meterAddress, String dataSign, StringBuffer retData) {
        if (retData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (dataSign.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        return method.readData(meterAddress, dataSign, retData);
    }

    @Override
    public int commonRead(String meterAddress, String dataSign, StringBuffer retData, boolean isMeter97) {
        if (retData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (isMeter97) {
            if (dataSign.length() != 4) {
                return ErrorManager.ErrorType.CommonParamError.getValue();
            }
        } else {
            if (dataSign.length() != 8) {
                return ErrorManager.ErrorType.CommonParamError.getValue();
            }
        }

        return method.readData(meterAddress, dataSign, retData, true);
    }
}
