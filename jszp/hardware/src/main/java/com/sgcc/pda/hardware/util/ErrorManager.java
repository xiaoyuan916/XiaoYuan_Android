package com.sgcc.pda.hardware.util;

import android.util.SparseArray;

import java.util.ArrayList;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/23.
 * 平台错误操作类
 */
public class ErrorManager {
    /**
     * 主站错误类型
     */
    private static final int MASTER_STATION_ERROR_MASK = 0x0000000F;
    /**
     * 安全单元错误类型
     */
    private static final int SAFETY_ERROR_MASK = 0x000000F0;
    /**
     * P645错误类型
     */
    private static final int P645_ERROR_MASK = 0x00000F00;
    /**
     * 数据库错误类型
     */
    private static final int DATABASE_ERROR_MASK = 0x0000F000;
    /**
     * 通用错误
     */
    private static final int COMMON_ERROR_MASK = 0x0F000000;
    /**
     * 业务流程错误类型，占8位，可以设置2^8=256种错误，从0x00010000到0x00FF0000
     */
    private static final int BUSINESS_ERROR_MASK = 0x00FF0000;
    /**
     * 错误类型分类集合
     */
    private static ArrayList<Integer> errorMasks = new ArrayList<>();

    // 动态添加错误分类
    static {
        errorMasks.add(MASTER_STATION_ERROR_MASK);
        errorMasks.add(SAFETY_ERROR_MASK);
        errorMasks.add(P645_ERROR_MASK);
        errorMasks.add(DATABASE_ERROR_MASK);
        errorMasks.add(COMMON_ERROR_MASK);
        errorMasks.add(BUSINESS_ERROR_MASK);
    }

    /**
     * 硬件交互错误类型
     */
    public enum ErrorType {
        //业务流程错误类型
        //业务流程工单0x00010000--0x002F0000,48种
        BusinessRegisterInfoNull(0x00010000),
        BusinessOrderAddressNull(0x00020000),
        BusinessOrderPermissionNull(0x00030000),
        BusinessOrderParamsError(0x00040000),
        BusinessTaskParamsError(0x00050000),
        //业务流程安全单元0x00310000--0x007F0000，80种
        BusinessSafeUnitSync(0x00310000),
        BusinessSafeUnitGetPermission(0x00320000),
        BusinessSafeUnitRegisterTask(0x00330000),
        BusinessSafeUnitRemoteAuth(0x00340000),
        BusinessSafeUnitGetRandom(0x00350000),
        BusinessSafeUnitInfraAuth(0x00360000),
        BusinessSafeUnitGetClockData(0x00370000),
        BusinessSafeUnitRegisterAuth(0x00380000),
        BusinessSafeUnitGetParamsSettingData(0x00390000),
        BusinessSafeUnitGetRemoteControlData(0x003A0000),
        //业务流程电表0x00810000--0x00FF0000，128种
        BusinessMeterNoNull(0x00810000),
        BusinessMeterParamsSetting(0x00820000),
        BusinessMeterRemoteAuth(0x00830000),
        BusinessMeterReadRechargeInfo(0x00840000),
        BusinessMeterRechargeInfoError(0x00850000),
        BusinessMeterCustomerIdNotEqual(0x00860000),
        BusinessMeterGetChargeData(0x00870000),
        BusinessMeterRechargeExecute(0x00880000),
        BusinessMeterInfraAuthRequest(0x00890000),
        BusinessMeterInfraAuthExecute(0x008A0000),
        BusinessMeterWriteClockSetting(0x008B0000),
        BusinessMeterReadFreezeDate(0x008C0000),
        BusinessMeterReadDayTimePeriodDate(0x008D0000),
        BusinessMeterReadTime(0x008E0000),
        BusinessMeterReadPowerSaveState(0x008F0000),
        BusinessMeterRemoteControlExecute(0x00900000),
        // 主站错误类型
        MasterStationFrameError(0x00000001),
        MasterStationCheckValueError(0x00000002),
        MasterStationParamError(0x00000003),
        MasterStationReceiveDataError(0x00000004),
        MasterStationBufferError(0x00000005),
        MasterStationConfigError(0x00000006),
        MasterStationSendError(0x00000007),
        MasterStationReceiveError(0x00000008),
        MasterStationFrameMatchError(0x00000009),
        // 安全单元错误类型
        SafetyFrameError(0x00000010),
        SafetyCheckValueError(0x00000020),
        SafetyGetStatusError(0x00000030),
        SafetyParamError(0x00000040),
        SafetyBufferError(0x00000050),
        SafetyConfigError(0x00000060),
        SafetySendError(0x00000070),
        SafetyReceiveError(0x00000080),
        SafetyReceiveDataError(0x00000090),
        SafetyUpdateFileError(0x000000A0),
        SafetyFrameMatchError(0x000000B0),
        // P645错误类型
        P645FrameError(0x00000100),
        P645CheckValueError(0x00000200),
        P645DataLengthError(0x00000300),
        P645SendNoData(0x00000400),
        P645ConfigError(0x00000500),
        P645SendError(0x00000600),
        P645ReceiveBufferError(0x00000700),
        P645ReceivedErrorValue(0x00000800),
        P645UpDownNotMatch(0x00000A00),
        P645ReceivedDataError(0x00000B00),
        P645ErrorParam(0x00000C00),
        P645FrameMatchError(0x00000D00),
        // 数据库错误类型
        DatabaseNotFoundError(0x00001000),
        DatabaseInsertError(0x00002000),
        DatabaseUpdateError(0x00003000),
        DatabaseParserError(0x00004000),
        DatabaseNotReadyError(0x00005000),
        // 通用错误
        CommonReadError(0x01000000),
        CommonBufferError(0x02000000),
        CommonParamError(0x03000000),
        ComputeMD5Error(0x04000000),
        ComputeCrc32Error(0x05000000),
        GZipError(0x06000000),
        FileError(0x07000000),
        CommonDataError(0x08000000),
        SocketError(0x09000000),
        InfraError(0x0A000000),
        SafetyUnitError(0x0B000000),
        CommonWriteError(0x0C000000),
        CommonProtocolError(0x0D000000),
        // 未知错误
        UnKnowError(0xF0000000),;
        int value;

        ErrorType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 错误信息集
     */
    private static final SparseArray<String> errorMessage = new SparseArray<>();

    static {
        //主站相关错误
        errorMessage.put(ErrorType.MasterStationFrameError.value, "主站通讯帧格式错误");
        errorMessage.put(ErrorType.MasterStationCheckValueError.value, "主站通讯帧校验错误");
        errorMessage.put(ErrorType.MasterStationParamError.value, "主站通讯参数错误");
        errorMessage.put(ErrorType.MasterStationReceiveDataError.value, "读取的返回的数据错误");
        errorMessage.put(ErrorType.MasterStationBufferError.value, "保存数据缓存区错误");
        errorMessage.put(ErrorType.MasterStationConfigError.value, "协议和帧结构定义为空");
        errorMessage.put(ErrorType.MasterStationSendError.value, "向主站发送数据错误");
        errorMessage.put(ErrorType.MasterStationReceiveError.value, "从主站获取数据错误");
        errorMessage.put(ErrorType.MasterStationFrameMatchError.value, "上下行帧不匹配");
        //安全单元相关错误
        errorMessage.put(ErrorType.SafetyFrameError.value, "安全单元通讯帧格式错误");
        errorMessage.put(ErrorType.SafetyCheckValueError.value, "安全单元通讯帧校验错误");
        errorMessage.put(ErrorType.SafetyGetStatusError.value, "安全单元下行帧返回了异常帧");
        errorMessage.put(ErrorType.SafetyParamError.value, "安全单元输入参数错误");
        errorMessage.put(ErrorType.SafetyBufferError.value, "安全单元接用于收返回的缓冲区错误");
        errorMessage.put(ErrorType.SafetyConfigError.value, "协议和帧结构定义为空");
        errorMessage.put(ErrorType.SafetySendError.value, "安全单元发送数据错误");
        errorMessage.put(ErrorType.SafetyReceiveError.value, "安全单元接收数据错误");
        errorMessage.put(ErrorType.SafetyReceiveDataError.value, "从安全单元接受到的数据错误");
        errorMessage.put(ErrorType.SafetyUpdateFileError.value, "安全单元升级文件错误");
        errorMessage.put(ErrorType.SafetyFrameMatchError.value, "上下行帧不匹配");
        //P645相关错误
        errorMessage.put(ErrorType.P645FrameError.value, "P645通讯帧格式错误");
        errorMessage.put(ErrorType.P645CheckValueError.value, "P645通讯帧校验错误");
        errorMessage.put(ErrorType.P645DataLengthError.value, "P645通讯帧数据长度错误");
        errorMessage.put(ErrorType.P645SendNoData.value, "P645待发送数据为空");
        errorMessage.put(ErrorType.P645ConfigError.value, "P645通讯参数设置错误");
        errorMessage.put(ErrorType.P645SendError.value, "P645数据发送错误");
        errorMessage.put(ErrorType.P645ReceiveBufferError.value, "用来接收P645返回数据的缓冲区错误");
        errorMessage.put(ErrorType.P645ReceivedErrorValue.value, "P645接收了一个包含异常信息的返回帧");
        errorMessage.put(ErrorType.P645UpDownNotMatch.value, "P645上下行帧不匹配");
        errorMessage.put(ErrorType.P645ReceivedDataError.value, "P645获取的数据域错误");
        errorMessage.put(ErrorType.P645ErrorParam.value, "P645命令输入参数错误");
        errorMessage.put(ErrorType.P645FrameMatchError.value, "上下行帧不匹配");
        //数据库操作错误
        errorMessage.put(ErrorType.DatabaseNotFoundError.value, "在数据库中没有查找到对应的记录");
        errorMessage.put(ErrorType.DatabaseInsertError.value, "向数据库中插入数据错误");
        errorMessage.put(ErrorType.DatabaseUpdateError.value, "更新数据库数据错误");
        errorMessage.put(ErrorType.DatabaseParserError.value, "数据格式错误");
        errorMessage.put(ErrorType.DatabaseNotReadyError.value, "没有数据库实例");
        // 通用错误类型
        errorMessage.put(ErrorType.CommonReadError.value, "读取数据错误");
        errorMessage.put(ErrorType.CommonBufferError.value, "返回值缓冲区为空");
        errorMessage.put(ErrorType.CommonParamError.value, "输入参数不合法");
        errorMessage.put(ErrorType.ComputeMD5Error.value, "计算MD5错误");
        errorMessage.put(ErrorType.ComputeCrc32Error.value, "计算CRC32错误");
        errorMessage.put(ErrorType.GZipError.value, "文件解压缩错误");
        errorMessage.put(ErrorType.FileError.value, "文件操作错误");
        errorMessage.put(ErrorType.CommonDataError.value, "数据错误");
        errorMessage.put(ErrorType.SocketError.value, "SOCKET操作错误");
        errorMessage.put(ErrorType.InfraError.value, "红外设备操作失败");
        errorMessage.put(ErrorType.SafetyUnitError.value, "安全单元设备操作失败");
        errorMessage.put(ErrorType.CommonWriteError.value, "数据写入错误");
        errorMessage.put(ErrorType.CommonProtocolError.value, "协议定义为空");
        //业务流程错误
        errorMessage.put(ErrorType.BusinessRegisterInfoNull.value, "本地数据库获取安全单元注册信息为空");
        errorMessage.put(ErrorType.BusinessOrderAddressNull.value, "工单表地址为空");
        errorMessage.put(ErrorType.BusinessOrderPermissionNull.value, "工单无权限信息");
        errorMessage.put(ErrorType.BusinessOrderParamsError.value, "工单参数有误");
        errorMessage.put(ErrorType.BusinessTaskParamsError.value, "工单执行时异步任务参数错误");

        errorMessage.put(ErrorType.BusinessSafeUnitSync.value, "安全单元同步失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetPermission.value, "安全单元获取操作员代码和权限失败");
        errorMessage.put(ErrorType.BusinessSafeUnitRegisterTask.value, "安全单元注册任务标识失败");
        errorMessage.put(ErrorType.BusinessSafeUnitRemoteAuth.value, "安全单元远程身份认证失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetRandom.value, "安全单元获取随机数失败");
        errorMessage.put(ErrorType.BusinessSafeUnitInfraAuth.value, "安全单元确认红外认证失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetClockData.value, "安全单元计算时钟设置数据失败");
        errorMessage.put(ErrorType.BusinessSafeUnitRegisterAuth.value, "安全单元注册权限失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetParamsSettingData.value, "安全单元获取参数设置任务数据失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetRemoteControlData.value, "安全单元获取远程控制命令数据失败");

        errorMessage.put(ErrorType.BusinessMeterNoNull.value, "电能表根据表地址读取表号失败，请检查表地址");
        errorMessage.put(ErrorType.BusinessMeterParamsSetting.value, "电能表参数设置失败");
        errorMessage.put(ErrorType.BusinessMeterRemoteAuth.value, "电能表远程身份认证失败");
        errorMessage.put(ErrorType.BusinessMeterReadRechargeInfo.value, "电能表查询充值信息失败");
        errorMessage.put(ErrorType.BusinessMeterRechargeInfoError.value, "电能表充值信息有误");
        errorMessage.put(ErrorType.BusinessMeterCustomerIdNotEqual.value, "电能表内读取客户编号与主站档案不一致");
        errorMessage.put(ErrorType.BusinessMeterGetChargeData.value, "电能表获取充值任务数据失败");
        errorMessage.put(ErrorType.BusinessMeterRechargeExecute.value, "电能表执行充值任务失败");
        errorMessage.put(ErrorType.BusinessMeterInfraAuthRequest.value, "电能表请求红外认证失败");
        errorMessage.put(ErrorType.BusinessMeterInfraAuthExecute.value, "电能表执行红外认证失败");
        errorMessage.put(ErrorType.BusinessMeterWriteClockSetting.value, "向电表发送时间设置命令失败");
        errorMessage.put(ErrorType.BusinessMeterReadFreezeDate.value, "电能表读取冻结数据失败");
        errorMessage.put(ErrorType.BusinessMeterReadDayTimePeriodDate.value, "电能表读取日时段数失败");
        errorMessage.put(ErrorType.BusinessMeterReadTime.value, "电能表读取时间失败");
        errorMessage.put(ErrorType.BusinessMeterReadPowerSaveState.value, "电能表读取保电状态失败");
        errorMessage.put(ErrorType.BusinessMeterRemoteControlExecute.value, "电能表执行电表控制失败");
    }

    /**
     * 获取错误文字说明
     *
     * @param error 具体错误类型
     * @return 错误字符串，多个错误以":"隔开
     */
    public static String getErrorMessage(int error) {
        if (error <= 0){
            return "";
        }
        StringBuilder ret = new StringBuilder();
        String msg;
        for (Integer mask : errorMasks) {
            msg = errorMessage.get(error & mask);
            if (null != msg) {
                ret.append(msg).append(":");
            }
        }
        return ret.length() > 0 ? ret.substring(0, ret.length() - 1) : ret.toString();
    }

    /**
     * 获取特定的错误类型
     *
     * @param error 错误码
     * @param mask  特定的错误类型筛选器
     * @return 0-无错误 其它-特定的错误类型
     */
    public static int getErrorValue(int error, int mask) {
        if (errorMasks.contains(mask)) {
            return -1;
        } else {
            return error & mask;
        }
    }

    /**
     * 根据主站通讯协议，业务执行过程中的错误码
     * 返回长度为6的HEX字符串
     *
     * @param error 错误码
     * @return 000000-无错误 成功-错误码 未知错误-040009
     */
    public static String getErrorSign(int error) {
        if (error == 0) {
            return "000000";
        }

        String ret = "";
        for (Integer mask : errorMasks) {
            int value = error & mask;
            if (value != 0) {
                switch (mask) {
                    case SAFETY_ERROR_MASK: {
                        ret += "01";
                        value = value >> 8; // 转化为可保存错误码
                        ret += DataConvert.toHexString(value, 2);
                        return ret;
                    }
                    case P645_ERROR_MASK: {
                        ret += "03";
                        value = value >> 16;
                        ret += DataConvert.toHexString(value, 2);
                        return ret;
                    }
                }
            }
        }
        return "040009";
    }

}
