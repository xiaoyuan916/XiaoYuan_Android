package com.sgcc.pda.hardware.model.founation.meter;

import java.util.List;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 红外基本业务功能接口
 */
public interface IMeter {
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
     * @return 0-成功 其它-错误码
     */
    int infraIdentityReq(String meterAddress, String random1, String operator, StringBuffer meterNo,
                         StringBuffer esamNo, StringBuffer enRandom1, StringBuffer random2);

    /**
     * 电表执行红外认证
     *
     * @param meterAddress 表通讯地址
     * @param enRandom2    经安全单元加密的随机数2密文
     * @param operator     操作员编号
     * @return 0-认证成功 其它-错误码
     */
    int infraIdentity(String meterAddress, String enRandom2, String operator);

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
     * @return 0-成功 其它-错误码
     */
    int identityAuthentication(String meterAddress, String cipher, String random1, String meterNo,
                               String operator, StringBuffer random2, StringBuffer esamNo);

    /**
     * 向电表写入第一类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @param mac          安全单元计算的MAC
     * @return 0-成功 其它-错误码
     */
    int writeClass1Data(String meterAddress, String dataSign, String password, String operator,
                        String data, String mac);

    /**
     * 向电表写入第一类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @param mac          安全单元计算的MAC
     * @param retControl   返回-写入数据后电表的响应数据的控制码
     * @param retData      返回-写入数据后电表的响应数据
     * @return 0-成功 其它-错误码
     */
    int writeClass1Data(String meterAddress, String dataSign, String password, String operator,
                        String data, String mac, StringBuffer retControl, StringBuffer retData);

    /**
     * 向电表写入第二类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @param mac          安全单元计算的MAC
     * @return 0-成功 其它-错误码
     */
    int writeClass2Data(String meterAddress, String dataSign, String password, String operator,
                        String data, String mac);

    /**
     * 向电表写入第二类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @param mac          安全单元计算的MAC
     * @param retControl   返回-写入数据后电表的响应数据的控制码
     * @param retData      返回-写入数据后电表的响应数据
     * @return 0-成功 其它-错误码
     */
    int writeClass2Data(String meterAddress, String dataSign, String password, String operator,
                        String data, String mac, StringBuffer retControl, StringBuffer retData);

    /**
     * 向电表写入第三类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @return 0-成功 其它-错误码
     */
    int writeClass3Data(String meterAddress, String dataSign, String password, String operator,
                        String data);

    /**
     * 向电表写入第三类数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param password     密码
     * @param operator     操作员编号
     * @param data         待写入数据
     * @param retControl   返回-写入数据后电表的响应数据的控制码
     * @param retData      返回-写入数据后电表的响应数据
     * @return 0-成功 其它-错误码
     */
    int writeClass3Data(String meterAddress, String dataSign, String password, String operator,
                        String data, StringBuffer retControl, StringBuffer retData);

    /**
     * 广播校时
     *
     * @return 0-成功 其它-错误码
     */
    int broadcastTiming();

    /**
     * 设置电表时间
     *
     * @param meterAddress 电表通讯地址
     * @param keyLevel     密级
     * @param time         设置的时间字符串
     * @param password     密码
     * @param operator     操作员编号
     * @return 0-成功 其它-错误码
     */
    int Timing(String meterAddress, String keyLevel, String time, String password, String operator);

    /**
     * 设置客户编号
     *
     * @param meterAddress 电表通讯地址
     * @param consNo       客户编号
     * @param password     密码
     * @param operator     操作员编号
     * @param mac          安全单元计算MAC
     * @return 0-成功 其它-错误码
     */
    int writeConsNo(String meterAddress, String consNo, String password, String operator,
                    String mac);

    /**
     * 获取表地址
     *
     * @param cover        表地址掩码
     * @param meterAddress 返回-表地址
     * @return 0-成功 其它-错误码
     */
    int getMeterAddress(String cover, StringBuffer meterAddress);

    /**
     * 获取表号
     *
     * @param meterAddress 电表通讯地址
     * @param meterNo      返回-电表表号
     * @return 0-成功 其它-错误码
     */
    int getMeterNo(String meterAddress, StringBuffer meterNo);

    /**
     * 获取户号
     *
     * @param meterAddress 电表通讯地址
     * @param consNo       返回-户号
     * @return 0-成功 其它-错误码
     */
    int getConsNo(String meterAddress, StringBuffer consNo);

    /**
     * 获取电表时间
     *
     * @param meterAddress 电表通讯地址
     * @param time         返回-电表时间
     * @return 0-成功 其它-错误码
     */
    int getMeterTime(String meterAddress, StringBuffer time);


    /**
     * 获取电表时段数
     *
     * @param meterAddress 通信地址
     * @param datasign     数据标识
     * @param amount       返回 - 电表时段数
     * @return
     */
    int readMeterPriodAmount(String meterAddress, String datasign, StringBuffer amount);


    /**
     * 获取所有正向有功电能
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-正向有功电能列表
     * @return 0-成功 其它-错误码
     */
    int getAllPaPower(String meterAddress, List<String> data);

    /**
     * 获取所有正向无功（组合无功1）电能
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-正向无功（组合无功1）电能列表
     * @return 0-成功 其它-错误码
     */
    int getAllPrPower(String meterAddress, List<String> data);

    /**
     * 获取所有反向有功电能
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-反向有功电能列表
     * @return 0-成功 其它-错误码
     */
    int getAllNaPower(String meterAddress, List<String> data);

    /**
     * 获取所有反向无功（组合无功2）电能
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-反向无功（组合无功2）电能列表
     * @return 0-成功 其它-错误码
     */
    int getAllNrPower(String meterAddress, List<String> data);

    /**
     * 获取最大需量及发生时间
     *
     * @param meterAddress 电表通讯地址
     * @param data         返回-最大需量
     * @param time         返回-最大需量发生时间
     * @return 0-成功 其它-错误码
     */
    int getMaxDemandAndTime(String meterAddress, StringBuffer data, StringBuffer time);

    /**
     * 获取日冻结时间点
     *
     * @param meterAddress 电表通讯地址
     * @param time         返回-日冻结时间点
     * @return 0-成功 其它-错误码
     */
    int getFreezenDayTime(String meterAddress, StringBuffer time);

    /**
     * 获取上N次冻结日期
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param date         返回-冻结日期
     * @return 0-成功 其它-错误码
     */
    int getFreezeDate(String meterAddress, String dayIndex, StringBuffer date);

    /**
     * 读取日冻结正向有功电能
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param data         返回-日冻结正向有功电能列表
     * @return 0-成功 其它-错误码
     */
    int getFreezeDayPaPower(String meterAddress, String dayIndex, List<String> data);

    /**
     * 读取日冻结反向有功电能
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param data         返回-日冻结反向有功电能列表
     * @return 0-成功 其它-错误码
     */
    int getFreezeDayNaPower(String meterAddress, String dayIndex, List<String> data);

    /**
     * 读取日冻结正向无功(组合无功1)电能
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param data         返回-日冻结正向无功(组合无功1)电能
     * @return 0-成功 其它-错误码
     */
    int getFreezeDayPrPower(String meterAddress, String dayIndex, List<String> data);

    /**
     * 读取日冻结反向无功(组合无功2)电能
     *
     * @param meterAddress 电表通讯地址
     * @param dayIndex     冻结数据索引
     * @param data         返回-日冻结反向无功(组合无功2)电能
     * @return 0-成功 其它-错误码
     */
    int getFreezeDayNrPower(String meterAddress, String dayIndex, List<String> data);

    /**
     * 更新密钥
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     密钥类型
     * @param mac          安全单元计算MAC
     * @param info         密钥信息
     * @param cipherText   密钥密文数据
     * @param operator     操作员编号
     * @return 0-成功 其它-错误码
     */
    int keyUpdate(String meterAddress, String dataSign, String mac, String info, String cipherText,
                  String operator);

    int keyUpdate(String meterAddress, String dataSign, String operator, String msg, String cipherText
    );

    /**
     * 13表密钥更新
     *
     * @param meterAddress 通信地址
     * @param dataSign     数据标识
     * @param operator     操作者代码
     * @param cipher       密钥数据
     * @param mac          mac
     * @return 0-成功 其它-错误码
     */
    int keyUpdate13(String meterAddress, String dataSign, String operator, String cipher, String mac
    );


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
     * @return 0-成功 其它-错误码
     */
    int recharge(String meterAddress, String money, String time, String mac1, String consNo,
                 String mac2, String operator);

    /**
     * 事件清零
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param keyLevel     密级
     * @param password     密码
     * @param operator     操作员编号
     * @return 0-成功 其它-错误码
     */
    int eventClear(String meterAddress, String dataSign, String keyLevel, String password, String operator);

    /**
     * 电表清零
     *
     * @param meterAddress 电表通讯地址
     * @param keyLevel     密级
     * @param password     密码
     * @param operator     操作员编号
     * @return 0-成功 其它-错误码
     */
    int meterClear(String meterAddress, String keyLevel, String password, String operator);

    /**
     * 设置电表当前通讯速率为其它标准速率
     *
     * @param meterAddress 电表通讯地址
     * @param baud         波特率字符串
     * @return 0-成功 其它-错误码
     */
    int setTbtl(String meterAddress, String baud);

    /**
     * 写电表通讯地址
     *
     * @param meterAddress    电表通讯地址
     * @param newMeterAddress 电表新通讯地址
     * @return 0-成功 其它-错误码
     */
    int writeMeterAddress(String meterAddress, String newMeterAddress);

    /**
     * 读取电表第一套费率(当前套)
     *
     * @param meterAddress 电表通讯地址
     * @param rates        返回-费率
     * @return 0-成功 其它-错误码
     */
    int readRate1(String meterAddress, String rateIndex, StringBuffer rates);

    /**
     * 读取电表第二套费率(备用套)
     *
     * @param meterAddress 电表通讯地址
     * @param rates        返回-费率
     * @return 0-成功 其它-错误码
     */
    int readRate2(String meterAddress, String rateIndex, StringBuffer rates);

    /**
     * 读取电表余额
     *
     * @param meterAddress 电表通讯地址
     * @param amount       返回-电表余额
     * @return 0-成功 其它-错误码
     */
    int readResidualAmount(String meterAddress, StringBuffer amount);

    /**
     * 读取电表充值信息
     *
     * @param meterAddress  电表通讯地址
     * @param operator      操作员编号
     * @param surplusAmount 剩余金额
     * @param rechargeTimes 充值次数
     * @param customCode    客户编号
     * @return 0-成功 其它-错误码
     */
    int readRechargeInfo(String meterAddress, String operator, StringBuffer surplusAmount, StringBuffer rechargeTimes, StringBuffer customCode);

    /**
     * 读取电表透支金额
     *
     * @param meterAddress 电表通讯地址
     * @param amount       返回-电表透支金额
     * @return 0-成功 其它-错误码
     */
    int readOverdrawAmount(String meterAddress, StringBuffer amount);

    /**
     * 执行电表控制功能
     *
     * @param meterAddress 电表通讯地址
     * @param operator     操作员
     * @param cipherText   电表控制数据
     * @return 0-成功 其它-错误码
     */
    int powerOperate(String meterAddress, String operator, String cipherText);

    /**
     * 根据数据标识读取电表数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retData      返回-读取到的返回值
     * @return 0-成功 其它-错误码
     */
    int commonRead(String meterAddress, String dataSign, StringBuffer retData);

    /**
     * 根据数据标识读取电表数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retControl   电表返回的控制码
     * @param retData      返回-读取到的返回值
     * @return 0-成功 其它-错误码
     */
    int commonRead(String meterAddress, String dataSign, StringBuffer retControl, StringBuffer retData);

    /**
     * 根据数据标识读取电表数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param dataNum      负荷记录块数
     * @param dateTime     时间
     * @param retControl   电表返回的控制码
     * @param retData      返回-读取到的返回值
     * @return 0-成功 其它-错误码
     */
    int commonRead(String meterAddress, String dataSign, String dataNum, String dateTime, StringBuffer retControl, StringBuffer retData);

    /**
     * 根据数据标识读取电表后续数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retControl   电表返回的控制码
     * @param retData      返回-读取到的返回值（这个值不包含数据标识）
     * @return 0-成功 其它-错误码
     */
    int commonReadContinue(String meterAddress, String dataSign, StringBuffer retControl, StringBuffer retData);

    /**
     * 根据数据标识读取电表数据
     *
     * @param meterAddress 电表通讯地址
     * @param dataSign     数据标识
     * @param retData      返回-读取到的返回值
     * @param isMeter97    是否为97表
     * @return 0-成功 其它-错误码
     */
    int commonRead(String meterAddress, String dataSign, StringBuffer retData, boolean isMeter97);


    /**
     * 获取电表当前运行的时区
     *
     * @param meterAddress 通信地址
     * @param timeZone     当前运行时区套(0 第一套  1 第二套)
     * @return 0-成功 其它-错误码
     */
    int readMeterTimezone(String meterAddress, StringBuffer timeZone);
}
