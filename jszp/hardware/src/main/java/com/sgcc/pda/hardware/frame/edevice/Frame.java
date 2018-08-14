package com.sgcc.pda.hardware.frame.edevice;


import com.sgcc.pda.hardware.util.DataConvert;

/**
 * 现场服务终端外设通讯协议通讯帧
 */
public class Frame {

    // 起始帧
    private static final String HEAD = "97";
    // 结束帧
    private static final String TAIL = "E9";

    // String与Byte相互转换时的进制
    public static final int RADIX = 16;

    // 起始符1在整个帧中的序号
    public static final int INDEX_HEAD = 0;
    // 设备类型在帧中的序号
    public static final int INDEX_TYPE = 1;
    // 设备地址在帧中的序号
    public static final int INDEX_ADDRESS = 2;
    // 起始符2在整个数据帧中的序号
    public static final int INDEX_START = 3;
    // 数据长度在数据帧中的序号
    public static final int INDEX_DATA_LENGTH = 4;
    // 帧序号在帧中的序号
    public static final int INDEX_SEQ = 5;
    // 控制码在帧中的序号
    public static final int INDEX_CONTROL = 6;
    // 功能码在帧中的序号
    public static final int INDEX_FUNCTION = 7;
    // 数据域在帧中的序号
    public static final int INDEX_DATA = 8;
    // 校验码在帧中的序号
    public static final int INDEX_CS = 9;
    // 结束符在帧中的序号
    public static final int INDEX_TAIL = 10;

    // 起始帧数据长度（字符串长度）
    private static final int LENGTH_HEAD = 2;
    // 设备类型数据长度
    private static final int LENGTH_TYPE = 4 * 2;
    // 通讯地址数据长度
    private static final int LENGTH_ADDRESS = 6 * 2;
    // 起始帧数据长度（第二次出现）
    private static final int LENGTH_BEGTIN = 2;
    // 数据域长度字段数据长度
    private static final int LENGTH_LENGTH = 2 * 2;
    // 帧序号长度
    private static final int LENGTH_SEQ = 2;
    // 控制码长度
    private static final int LENGTH_CONTROL = 2;
    // 功能码长度
    private static final int LENGTH_FUNCTION = 2 * 2;
    /**
     * 数据域长度由
     * 数据域长度字段值确定，16进制长度
     */
    // 校验码长度
    private static final int LENGTH_CS = 2;
    // 结束符长度
    private static final int LENGTH_TAIL = 2;

    private static final int MIN_LENGTH = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS
            + LENGTH_BEGTIN + LENGTH_LENGTH + LENGTH_SEQ + LENGTH_CONTROL + LENGTH_FUNCTION;

    // 起始符在字符串中的索引
    private static final int OFFSET_HEAD = 0;
    // 设备类型在字符串中的索引
    private static final int OFFSET_TYPE = LENGTH_HEAD;
    // 通讯地址在字符串中的索引
    private static final int OFFSET_ADDRESS = LENGTH_HEAD + LENGTH_TYPE;
    // 起始帧在字符串中的索引
    private static final int OFFSET_BEGTIN = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS;
    // 数据域长度字段在字符串中的索引
    private static final int OFFSET_LENGTH = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS + LENGTH_BEGTIN;
    // 帧序号在字符串中的索引
    private static final int OFFSET_SEQ = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS + LENGTH_BEGTIN + LENGTH_LENGTH;
    // 控制码在字符串中的索引
    private static final int OFFSET_CONTROL = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS + LENGTH_BEGTIN + LENGTH_LENGTH + LENGTH_SEQ;
    // 功能码在字符串中的索引
    private static final int OFFSET_FUNCTION = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS + LENGTH_BEGTIN + LENGTH_LENGTH + LENGTH_SEQ + LENGTH_CONTROL;
    // 数据域在字符串中的索引
    private static final int OFFSET_DATA = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS + LENGTH_BEGTIN + LENGTH_LENGTH + LENGTH_SEQ + LENGTH_CONTROL + LENGTH_FUNCTION;
    // 校验码在字符串中的索引（正式使用的时候需要加上数据域长度）
    private static final int OFFSET_CS = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS + LENGTH_BEGTIN + LENGTH_LENGTH + LENGTH_SEQ + LENGTH_CONTROL + LENGTH_FUNCTION;
    // 结束符在字符串中的索引（正式使用的时候需要加上数据域长度）
    private static final int OFFSET_TAIL = LENGTH_HEAD + LENGTH_TYPE + LENGTH_ADDRESS + LENGTH_BEGTIN + LENGTH_LENGTH + LENGTH_SEQ + LENGTH_CONTROL + LENGTH_FUNCTION + LENGTH_CS;

    // 设备类型--超高频
    public static final int TYPE_CGP = 0x80000000;
    // 设备类型--载波检测
    public static final int TYPE_ZBJC = 0x40000000;
    // 设备类型--SIM卡检测
    public static final int TYPE_SIMJC = 0x20000000;
    // 设备类型--现场校验
    public static final int TYPE_XCJY = 0x10000000;
    // 设备类型--微功率无线
    public static final int TYPE_WGLWX = 0x08000000;
    // 设备类型--串户检测
    public static final int TYPE_CHJC = 0x04000000;
    // 设备类型--场强检验
    public static final int TYPE_CQJC = 0x02000000;
    // 设备类型--购电卡
    public static final int TYPE_GDK = 0x01000000;
    // 设备类型--RS485
    public static final int TYPE_RS485 = 0x00800000;
    // 设备类型--RS232
    public static final int TYPE_RS232 = 0x00400000;
    // 设备类型--蓝牙打印机
    public static final int TYPE_BTPRINTER = 0x00200000;
    // 设备类型--广播类型
    public static final int TYPE_GB = 0xFFFFFFFF;

    // 控制码--传输方向
    public static final int CONTROL_TRANS_FX = 0x80;
    // 传输方向--终端发送
    public static final int CONTROL_TRANS_FX_ZD = 0x00;
    // 传输方向--外设发送
    public static final int CONTROL_TRANS_FX_WS = 0x80;

    // 控制码--主从站标志
    public static final int CONTROL_DEVICE_ZC = 0x40;
    // 主从站标志--从设备
    public static final int CONTROL_DEVICE_ZC_CSB = 0x00;
    // 主从站标志--主设备
    public static final int CONTROL_DEVICE_ZC_ZSB = 0x40;

    // 控制码--错误标志
    public static final int CONTROL_RESPONSE_ERROR = 0x20;
    // 错误标志--正常应答
    public static final int CONTROL_RESPONSE_ERROR_ZC = 0x00;
    // 错误标志--错误应答
    public static final int CONTROL_RESPONSE_ERROR_YC = 0x20;
    public static final int CHUAN_HU_ERROR = 0xA0;

    // 控制码--明文密文
    public static final int CONTROL_ENCRIPY = 0x10;
    // 明文
    public static final int CONTROL_ENCRIPY_MING = 0x00;
    // 密文
    public static final int CONTROL_ENCRIPY_MI = 0x10;

    // 控制码--MAC标志
    public static final int CONTROL_MAC = 0x08;
    // 无MAC
    public static final int CONTROL_MAC_WU = 0x00;
    // 有MAC
    public static final int CONTROL_MAC_YOU = 0x08;

    // 功能码通用F1无效标志
    public static final int FN_COMMON_UNDECLARE = 0xFF;

    // 查询通用参数--设备信息
    public static final int FN_REQUEST_DEVICE_INFO = 0x0101;
    // 查询通用参数--设备状态
    public static final int FN_REQUEST_DEVICE_STATUS = 0x0102;
    // 查询通用参数--故障信息
    public static final int FN_REQUEST_FAULT_INFO = 0x0103;

    // 设置通用参数--外设地址
    public static final int FN_SET_ADDRESS = 0x0201;

    // 复位命令--硬件初始化
    public static final int FN_RESET_HARDWARE = 0x0301;
    // 复位命令--参数区初始化
    public static final int FN_RESET_PARAM = 0x0302;
    // 复位命令--参数及全体数据区初始化（恢复出厂设置）
    public static final int FN_RESET_FACTORY = 0x0303;

    // 数据透传
    public static final int FN_DATA_TRANSMIT = 0x0401;

    // 身份认证--发起认证
    public static final int FN_AUTHORITY_REQUEST = 0x0501;
    // 身份认证--身份认证
    public static final int FN_AUTHORITY_USER = 0x0502;
    //密钥协商
    public static final int FN_MYXS= 0x0503;

    // 信息交互--开启信息交互
    public static final int FN_COMMUNICATE_OPEN = 0x0601;
    // 信息交互--关闭信息交互
    public static final int FN_COMMUNICATE_CLOSE = 0x0602;
    // 信息交互--打印信息
    public static final int FN_COMMUNICATE_PRINT = 0x0603;
    // 信息交互--信息选择
    public static final int FN_COMMUNICATE_CHOOSE = 0x0604;

    // 获取帮助文档--帮助文档
    public static final int FN_DOCUMENT_HELP = 0x0701;
    // 获取帮助文档--说明文档
    public static final int FN_DOCUMENT_EXPLAIN = 0x0702;

    // 在线升级--请求升级
    public static final int FN_UPDATE_REQUEST = 0x0F01;
    // 在线升级--文件传输
    public static final int FN_UPDATE_FILE_TRANS = 0x0F02;
    // 在线升级--开始升级
    public static final int FN_UPDATE_START = 0x0F03;

    // 超高频--查询功率范围
    public static final int FN_CGP_CXGLFW = 0x2001;
    // 超高频--查询功率
    public static final int FN_CGP_CXGL = 0x2002;
    // 超高频--设置功率
    public static final int FN_CGP_SZGL = 0x2003;
    // 超高频--查询标签上传参数
    public static final int FN_CGP_CXBQSCCS = 0x2004;
    // 超高频--设置标签上传参数
    public static final int FN_CGP_SZBQSCCS = 0x2005;
    // 超高频--查询基带参数
    public static final int FN_CGP_CXJDCS = 0x2006;
    // 超高频--设置基带参数
    public static final int FN_CGP_SZJDCS = 0x2007;
    // 超高频--读标签数据
    public static final int FN_CGP_EPC_DATA = 0x2008;
    // 超高频--存盘
    public static final int FN_CGP_CP = 0x2009;
    // 超高频--停止存盘
    public static final int FN_CGP_TZCP = 0x200A;
    // 超高频--选择标签
    public static final int FN_CGP_XZBQ = 0x200B;
    // 超高频--发起认证
    public static final int FN_CGP_FQRZ = 0x200C;
    // 超高频--认证
    public static final int FN_CGP_RZ = 0x200D;
    // 超高频--写标签数据
    public static final int FN_CGP_XBQSJ = 0x200E;
    // 超高频--锁标签
    public static final int FN_CGP_GB_DATA = 0x200F;
    // 超高频--灭活标签
    public static final int FN_CGP_MHBQ = 0x2010;

    // 载波检测--电能表整机载波检测
    public static final int FN_ZBJC_DNBZJZBJC = 0x2101;
    // 载波检测--采集器整机载波检测
    public static final int FN_ZBJC_CJQZJZBJC = 0x2102;
    // 载波检测--集中器整机载波检测
    public static final int FN_ZBJC_JZQZJZBJC = 0x2103;
    // 载波检测--电能表载波模块检测
    public static final int FN_ZBJC_DNBZBMKJC = 0x2104;
    // 载波检测--采集器载波模块检测
    public static final int FN_ZBJC_CJQZBMKJC = 0x2105;
    // 载波检测--集中器载波模块检测
    public static final int FN_ZBJC_JZQZBMKJC = 0x2106;
    // 载波检测--电能表主机检测
    public static final int FN_ZBJC_DNBZJJC = 0x2107;
    // 载波检测--集中器主机检测
    public static final int FN_ZBJC_JZQZJJC = 0x2108;
    // 载波检测--采集器主机检测
    public static final int FN_ZBJC_CJQZJJC = 0x2109;
    // 载波检测--链路检测
    public static final int FN_ZBJC_LLJC = 0x210A;

    // SIM卡检测--获取SIM卡信息
    public static final int FN_SIMJC_HQSIMKZX = 0x2201;
    // SIM卡检测--下发SIM卡检测参数
    public static final int FN_SIMJC_XFSIMJCCS = 0x2202;
    //SIM卡检测--下发SIM卡检测参数-----成功
    public static final int FN_SIMJC_XFSIMJCCS_YES = 0X220201;
    //SIM卡检测--下发SIM卡检测参数----失败
    public static final int FN_SIMJC_XFSIMJCCS_NO = 0X220202;
    // SIM卡检测--获取SIM卡检测结果
    public static final int FN_SIMJC_HQSIMKJCJG = 0x2203;

    // 现场校验--获取电能表误差数据
    public static final int FN_XCJY_HQDNBWCSJ = 0x2301;
    // 现场校验--获取电能表谐波数据
    public static final int FN_XCJY_HQDNBXBSJ = 0x2302;
    // 现场校验--获取电能表接线错误数据
    public static final int FN_XCJY_HQDNBJXCWSJ = 0x2303;

    // 微功率无线--电能表整机微功率无线检测
    public static final int FN_WGLWX_DNBZJWGLWXJC = 0x2401;
    // 微功率无线--采集器整机微功率无线检测
    public static final int FN_WGLWX_CJQZJWGLWXJC = 0x2402;
    // 微功率无线--集中器整机微功率无线检测
    public static final int FN_WGLWX_JZQZJWGLWXJC = 0x2403;
    // 微功率无线--电能表整机微功率无线模块检测
    public static final int FN_WGLWX_DNBZJWGLWXMKJC = 0x2404;
    // 微功率无线--采集器整机微功率无线模块检测
    public static final int FN_WGLWX_CJQZJWGLWXMKJC = 0x2405;
    // 微功率无线--集中器整机微功率无线模块检测
    public static final int FN_WGLWX_JZQZJWGLWXMKJC = 0x2406;
    // 微功率无线--电能表整机检测
    public static final int FN_WGLWX_DNBZJJC = 0x2407;
    // 微功率无线--采集器整机检测
    public static final int FN_WGLWX_CJQZJJC = 0x2408;
    // 微功率无线--集中器整机检测
    public static final int FN_WGLWX_JZQZJJC = 0x2409;
    // 微功率无线--链路检测
    public static final int FN_WGLWX_LLJC = 0x240A;

    // 串户检测台区识别--串户检测
    public static final int FN_CHJCTQSB_CHJC = 0x2501;
    // 串户检测台区识别--台区识别
    public static final int FN_CHJCTQSB_TQSB = 0x2502;
    
    public static final int FN_CHJCTQSB_TQSB_DO = 0x2503;

    //现场校验--取场强检测数据
    public static final int FN_XCJY_HQCQJCSJ = 0x2304;

    //测量值校准（华立扩展）
    //Ua标准值校准
    public static final int FN_CHECK_UA = 0x5101;
    //Ub标准值校准
    public static final int FN_CHECK_UB = 0x5102;
    //Uc标准值校准
    public static final int FN_CHECK_UC = 0x5103;
    //la标准值校准
    public static final int FN_CHECK_LA= 0x5104;
    //lb标准值校准
    public static final int FN_CHECK_LB = 0x5105;
    //lc标准值校准
    public static final int FN_CHECK_LC = 0x5106;
    //PFa标准值校准
    public static final int FN_CHECK_PFA = 0x5107;
    //PFb标准值校准
    public static final int FN_CHECK_PFB = 0x5108;
    //PFc标准值校准
    public static final int FN_CHECK_PFC = 0x5109;

    //设置参数（华立扩展）
    //电能表类型选择
    public static final int FN_SET_DNBLXXZ = 0x5001;
    //电压量程选择
    public static final int FN_SET_DYLCXZ = 0x5002;
    //电流输入选择
    public static final int FN_SET_DLSRXZ = 0x5003;
    //钳表II量程
    public static final int FN_SET_QB2LC = 0x5004;
    //钳表III量程
    public static final int FN_SET_QB3LC = 0x5005;
    //电能输出选择
    public static final int FN_SET_DNSCXZ = 0x5006;
    //脉冲高低频输出
    public static final int FN_SET_MCGDPSC = 0x5007;
    //电能脉冲输出选择
    public static final int FN_SET_DNMCSCXZ = 0x5008;
    //数字滤波
    public static final int FN_SET_SZLB = 0x5009;
    //波形选择
    public static final int FN_SET_BXXZ = 0x500A;
    //谐波分析选择
    public static final int FN_SET_XBFXXZ = 0x500B;
    //校验圈数
    public static final int FN_SET_JYQS = 0x500C;
    //被检表常数
    public static final int FN_SET_BJBCS = 0x500D;
    //误差启动
    public static final int FN_SET_WCQD = 0x5010;

    //获取设置参数
    public static final int FN_GET_PARAMS = 0x5201;

    //获取环境参数
    // 获取温度数据
    public static final int FN_ENVIR_WD = 0x0801;
    // 获取温度数据
    public static final int FN_ENVIR_SD = 0x0802;


    // 场强检测--获取场强检测数据
    public static final int FN_CQJC_HQCQJCSJ = 0x2601;

    // 购电卡--数据透传
    public static final int FN_GDK_SJTC = 0x2701;

    // RS485--参数查询
    public static final int FN_RS485_CSCX = 0x2801;
    // RS485--参数设置
    public static final int FN_RS485_CSSZ = 0x2802;

    // RS232--参数查询
    public static final int FN_RS232_CSCX = 0x2901;
    // RS232--参数设置
    public static final int FN_RS232_CSSZ = 0x2902;
    // 退出任务执行命令
    public static final int FN_QUIT = 0x2B01;


    // 通用错误编码--失败
    public static final int ERROR_COMMON = 0x01;
    // 通用错误编码--参数越限
    public static final int ERROR_CSYX = 0xFE;
    // 通用错误编码--无此功能
    public static final int ERROR_WCGN = 0xFD;
    // 通用错误编码--数据非法
    public static final int ERROR_SJFF = 0xFC;
    // 通用错误编码--通讯超时
    public static final int ERROR_TXCS = 0xFB;
    // 通用错误编码--外设地址非法
    public static final int ERROR_WSDZFF = 0xFA;
    // 通用错误编码--初始化失败
    public static final int ERROR_CSHSB = 0xF9;
    // 通用错误编码--认证失败
    public static final int ERROR_RZSB = 0xF8;

    // 错误编码--电能表带负载异常
    public static final int ERROR_CODE_DNBDFZYC = 0x80000000;
    // 错误编码--终端主机异常
    public static final int ERROR_CODE_ZDZJYC = 0x40000000;
    // 错误编码--终端带负载异常
    public static final int ERROR_CODE_ZDDFZYC = 0x20000000;
    // 错误编码--模块功耗异常
    public static final int ERROR_CODE_MKGHYC = 0x10000000;
    // 错误编码--模块通信能力异常
    public static final int ERROR_CODE_MKTXNLYC = 0x08000000;
    // 错误编码--载波模块逻辑单元异常
    public static final int ERROR_CODE_ZBMKLJDYYC = 0x04000000;
    // 错误编码--组网异常
    public static final int ERROR_CODE_ZWYC = 0x02000000;
    // 错误编码--串口异常
    public static final int ERROR_CODE_CKYC = 0x01000000;
    // 错误编码--采集器整机通信异常
    public static final int ERROR_CODE_CJQZJTXYC = 0x00200000;
    // 错误编码--电能表整机通信异常
    public static final int ERROR_CODE_DNBZJTXYC = 0x00100000;
    // 错误编码--终端整机通信异常
    public static final int ERROR_CODE_ZDZJTXYC = 0x00080000;
    // 错误编码--采集器主机异常
    public static final int ERROR_CODE_CJQZJYC = 0x00040000;
    // 错误编码--采集器带负载异常
    public static final int ERROR_CODE_CJQDFZYC = 0x00020000;
    // 错误编码--电能表主机异常
    public static final int ERROR_CODE_DNBZJYC = 0x00010000;

    // 起始符赋值/获取操作
    private String head = HEAD;

    public String getHead() {
        return head;
    }

    // 设备类型赋值/获取操作
    private String type = "";

    public String getType() {
        return type;
    }

    public int getTypeValue() {
        return Integer.parseInt(type, RADIX);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(int typeValue) {
        this.type = DataConvert.toHexString(typeValue, LENGTH_TYPE / 2);
    }

    // 设备地址赋值/获取操作
    private String address = "";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // 起始符2赋值/获取操作
    private String start = HEAD;

    public String getStart() {
        return start;
    }

    // 数据域长度赋值/获取操作
    private String dataLength = "";

    public String getDataLength() {
        return dataLength;
    }

    public int getDataLengthValue() {
        return Integer.parseInt(dataLength, RADIX);
    }

    private void setDataLength(String dataLength, boolean reverse) {
        if (reverse) {
            this.dataLength = DataConvert.strReverse(dataLength, 0, dataLength.length());
        } else {
            this.dataLength = dataLength;
        }
    }

    private void setDataLength() {
        if (data == null) {
            this.dataLength = "0000";
        } else {
            String len = DataConvert.toHexString(data.length() / 2, LENGTH_LENGTH / 2);
            setDataLength(len, true);
        }
    }

    // 帧序号赋值/获取操作
    private String seq = "";

    public String getSeq() {
        return seq;
    }

    public int getSeqValue() {
        return Integer.parseInt(seq, RADIX);
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public void setSeq(int seqValue) {
        this.seq = DataConvert.toHexString(seqValue, LENGTH_SEQ / 2);
    }

    // 控制码赋值/获取操作
    private String control = "";

    public String getControl() {
        return control;
    }

    public int getControlValue() {
        return Integer.parseInt(control, RADIX);
    }

    public void setControl(String control) {
        this.control = control;
    }

    public void setControl(int controlValue) {
        this.control = DataConvert.toHexString(controlValue, LENGTH_CONTROL / 2);
    }

    // 功能码赋值/获取操作
    private String function = "";

    public String getFunction() {
        return function;
    }

    public int getFunctionValue() {
        return Integer.parseInt(function, RADIX);
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setFunction(int functionValue) {
        this.function = DataConvert.toHexString(functionValue, LENGTH_FUNCTION / 2);
    }

    // 数据域赋值/获取操作
    private String data;

    public String getData() {
        return this.data;
    }

    /**
     * 设置数据域
     *
     * @param data         数据域内容
     * @param updateLength 是否更新数据域长度字段
     */
    public void setData(String data, boolean updateLength) {
        this.data = data;
        if (updateLength) {
            setDataLength();
        }
    }

    // 校验码赋值/获取操作
    private String cs;

    public String getCs() {
        return this.cs;
    }

    private void setCs(String cs) {
        this.cs = cs;
    }

    // 结束符赋值/获取操作
    private String tail = TAIL;

    public String getTail() {
        return this.tail;
    }

    /**
     * 检测帧字段长度是否合法
     *
     * @param frameIndex 帧字段索引
     * @return true-长度合法 false-长度不合法
     */
    public boolean checkFrameLength(int frameIndex) {
        switch (frameIndex) {
            case INDEX_HEAD:
                return head != null && head.length() == LENGTH_HEAD;
            case INDEX_TYPE:
                return type != null && type.length() == LENGTH_TYPE;
            case INDEX_ADDRESS:
                return address != null && address.length() == LENGTH_ADDRESS;
            case INDEX_START:
                return start != null && start.length() == LENGTH_HEAD;
            case INDEX_DATA_LENGTH:
                return dataLength != null && dataLength.length() == LENGTH_LENGTH;
            case INDEX_SEQ:
                return seq != null && seq.length() == LENGTH_SEQ;
            case INDEX_CONTROL:
                return control != null && control.length() == LENGTH_CONTROL;
            case INDEX_FUNCTION:
                return function != null && function.length() == LENGTH_FUNCTION;
            case INDEX_DATA:
                return checkDataLength();
            case INDEX_CS:
                return cs != null && cs.length() == LENGTH_CS;
            case INDEX_TAIL:
                return tail != null && tail.length() == LENGTH_TAIL;
            default:
                return false;
        }
    }

    /**
     * 获取数据域长度与数据域长度字段标记的长度是否符合
     *
     * @return true-符合 false-不符合
     */
    public boolean checkDataLength() {
        if (dataLength == null || dataLength.length() != LENGTH_LENGTH) {
            return false;
        }
        int trueLength = Integer.parseInt(DataConvert.strReverse(dataLength, 0, dataLength.length()), 16); // 获取数据长度字段表示的数值
        if (data == null) { // 如果数据域为空，则判断数据长度字段是否为0
            return trueLength == 0;
        } else { // 如果数据域不为空，则判断数据域长度是否等于数据长度字段标记的长度×2
            return data.length() == trueLength * 2;
        }
    }

    /**
     * 获取帧校验码是否正确
     *
     * @return true-正确 false-不正确
     */
    public boolean checkSum() {
        return cs != null && cs.equals(DataConvert.getSumValue(getStringToSum()));
    }

    /**
     * 获取参与校验码计算的字符串内容
     *
     * @return 帧字段除去校验码和结束符的字符串
     */
    public String getStringToSum() {
        StringBuilder sb = new StringBuilder();
        sb.append(head == null ? "" : head);
        sb.append(type == null ? "" : type);
        sb.append(address == null ? "" : address);
        sb.append(start == null ? "" : start);
        sb.append(dataLength == null ? "" : dataLength);
        sb.append(seq == null ? "" : seq);
        sb.append(control == null ? "" : control);
        sb.append(function == null ? "" : function);
        sb.append(data == null ? "" : data);
        return sb.toString();
    }

    /**
     * 获取帧的字节形式
     *
     * @return HEX字符串
     */
    public String getFrameString() {
        for (int i = INDEX_HEAD; i <= INDEX_TAIL; i++) {
            if (!checkFrameLength(i)) {
                return null;
            }
        }
        if (!checkSum()) {
            return null;
        }
        return getStringToSum() + DataConvert.getSumValue(getStringToSum()) + tail;
    }

    /**
     * 获取帧byte数组
     *
     * @return null--帧结构错误或存在异常
     * 其它--Frame的byte数组形式
     */
    public byte[] getFrameBytes() {
        return DataConvert.toBytes(getFrameString());
    }

    /**
     * 默认构造函数
     */
    public Frame() {
    }

    /**
     * @param type
     * @param address
     * @param seq
     * @param control
     * @param function
     * @param data
     */
    public Frame(int type, String address, int seq, int control, int function, String data) {
        setType(type);
        setAddress(address);
        setSeq(seq);
        setControl(control);
        setFunction(function);
        setData(data, true);
        setCs(DataConvert.getSumValue(getStringToSum()));
    }

    /**
     * 从byte数组中获取Frame实例
     *
     * @param data byte数组
     * @return null--帧解析错误
     * 其它--Frame实例
     */
    public static Frame fromBytes(byte[] data) {
        return fromString(DataConvert.toHexString(data, 0, data.length));
    }

    /**
     * 从HEX字符串获取首个有效的Frame实例
     *
     * @param data HEX字符串
     * @return null--不包含有效的Frame实例
     * 其它--找到Frame实例并适配
     */
    public static Frame fromString(String data) {
        if (data == null) {
            return null;
        }
        // 消除空格和大小写的干扰
        data = data.trim().toUpperCase();
        // 不包含起始符或结束符
        if (!data.contains(HEAD) || !data.contains(TAIL)) {
            return null;
        }
        // data长度为奇数且长度小于最小帧长度
        if (data.length() % 2 == 1 || data.length() < MIN_LENGTH) {
            return null;
        }
        Frame frame = new Frame();
        int frameBegin = 0;
        do {
            // 寻找等于起始符的起始位置
            frameBegin = data.indexOf(HEAD, frameBegin);
            // 查询到字符串末尾，没有找到符合规约的字符串
            if (frameBegin < 0 || frameBegin + MIN_LENGTH >= data.length()) {
                break;
            }

            if (!data.substring(frameBegin + OFFSET_HEAD, frameBegin + OFFSET_HEAD + 2).equals(HEAD)) {
                frameBegin += 2;
                continue;
            }

            // 获取数据域长度
            int dataLength;
            try {
                byte[] dataLen = DataConvert.toBytes(data.substring(frameBegin + OFFSET_LENGTH, frameBegin + OFFSET_LENGTH + LENGTH_LENGTH));
                dataLength = byteToInt(dataLen, 0, dataLen.length);
//                dataLength = Integer.parseInt(data.substring(frameBegin + OFFSET_LENGTH, frameBegin + OFFSET_LENGTH + LENGTH_LENGTH), 16);
            } catch (Exception e) {
                // 如果出现异常，表示在数据中出现了不合法的字符
                frameBegin += 2;
                continue;
            }

            // 剩余的数据是否满足目前数据描述的帧长度
            // 不满足则不再进行查找
            if (frameBegin + MIN_LENGTH + dataLength * 2 > data.length()) {
                break;
            }

            // 判断固定位置字符是否为结束符
            // 如果不是，则寻找下一个起始符
            if (!data.substring(frameBegin + OFFSET_TAIL + dataLength * 2, frameBegin + OFFSET_TAIL + dataLength * 2 + 2).equals(TAIL)) {
                frameBegin += 2;
                continue;
            }
            frame.setType(data.substring(frameBegin + OFFSET_TYPE, frameBegin + OFFSET_TYPE + LENGTH_TYPE));
            frame.setAddress(data.substring(frameBegin + OFFSET_ADDRESS, frameBegin + OFFSET_ADDRESS + LENGTH_ADDRESS));
            frame.setDataLength(data.substring(frameBegin + OFFSET_LENGTH, frameBegin + OFFSET_LENGTH + LENGTH_LENGTH), false);
            frame.setSeq(data.substring(frameBegin + OFFSET_SEQ, frameBegin + OFFSET_SEQ + LENGTH_SEQ));
            frame.setControl(data.substring(frameBegin + OFFSET_CONTROL, frameBegin + OFFSET_CONTROL + LENGTH_CONTROL));
            frame.setFunction(data.substring(frameBegin + OFFSET_FUNCTION, frameBegin + OFFSET_FUNCTION + LENGTH_LENGTH));
            // 更新数据域，但不更新数据域长度字段
            frame.setData(data.substring(frameBegin + OFFSET_DATA, frameBegin + OFFSET_DATA + dataLength * 2), false);
            frame.setCs(data.substring(frameBegin + OFFSET_CS + dataLength * 2, frameBegin + OFFSET_CS + dataLength * 2 + LENGTH_CS));
            if (frame.checkSum() && frame.checkDataLength()) {
                return frame;
            } else {
                frameBegin += 2;
                continue;
            }
        } while (true);

        return null;
    }


    /**
     * byte数组中的数据转为Int数据
     *
     * @param b      byte数组(低字节在前，高字节在后)
     * @param offset 转换开始的位置
     * @param len    byte数据的个数
     * @return
     */
    public static int byteToInt(byte[] b, int offset, int len) {

        int mask = 0xff;
        int temp = 0;
        int n = 0;
        for (int i = offset + len - 1; i >= offset; i--) {
            n <<= 8;
            temp = b[i] & mask;
            n |= temp;
        }
        return n;
    }

}
