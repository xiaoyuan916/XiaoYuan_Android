package com.xiao.serialport.constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xiao.serialport.serialportapi.frames.BusInfoFrame;
import com.xiao.serialport.serialportapi.frames.LineInfoCheckFrame;
import com.xiao.serialport.serialportapi.frames.LineInfoPushFrame;
import com.xiao.serialport.serialportapi.frames.LineSyncFrame;
import com.xiao.serialport.serialportapi.frames.StopFrame;

import java.util.List;

public class Constants {
    /**
     * 表示当前 已经处理过的 节站屏线路信息查询帧 。
     */
    public static LineInfoCheckFrame lineInfoCheckFrame;
    /**
     * 判断车进出站 0进站、1出站10秒内、2出站10秒以上
     */
    private static int state = -1;

    /**
     * 当前的 节站屏线路同步包 数据。
     */
    public static LineSyncFrame lineSyncFrame;

    /**
     * 上行线路上的所有站点.
     */
    public static List<LineInfoPushFrame> upList;

    /**
     * 下行线路上的所有站点.
     */
    public static List<LineInfoPushFrame> downList;

    /**
     * 公交车当前运行的线路方向。
     */
    public static List<LineInfoPushFrame> currLineDirectionList;

    /**
     * 公交车当前运行的线路的类型。<br>
     * 0:直线型<br>
     * 1:U型<br>
     * 2:环型
     */
    public static int currLineType;

    /**
     * 上行、下行所有站点是否接收完毕。
     */
    public static boolean isAllStations = false;

    /**
     * 表示当前 接收到的 最新的线路信息帧。
     */
    public static StopFrame stopFrame;



    /**
     * 节站屏显示的 默认静态服务用语。
     */
    public static String DEFAULT_STATIC_SERVICE_WORD = "绿色公交，文明出行，构建和谐社会";
    /**
     * 节站屏显示的 默认动态信息。
     */
    // public static String default_DYNAMIC_INFO = "关注天气变化，注意身体健康";
    public static String default_DYNAMIC_INFO = "举手之间显文明，细微之处见公德；文明乘车，绿色出行，共创交通文明，共享安全和谐；";

    /**
     * 当前APP使用的数据库名称。
     */
    public static String db_name = "com.tiamaes.sectionstationscreen4gj";

    /**
     * 当前APP使用的数据库的存储的路径。
     */
    public static String dbDir = "/data/data/com.tiamaes.sectionstationscreen4gj/files";


    /**
     * 获取本APP要使用的数据库。
     */
    public static SQLiteDatabase database;


    /**
     * 处理进出站信息帧（线路信息帧）的系统时间。
     */
    public static long Handle_StopFrame_Time;

    /**
     * 当前的 车辆信息包 数据。
     */
    public static BusInfoFrame busInfoFrame;

    public static byte[] driverInfoFrameMessage;

    /**
     * 处理 相邻两次进出站信息帧（线路信息帧） 的最小时间间隔 5s。
     */
    public static long Handle_StopFrame_DurTime = 8 * 1000;


    /**
     * 对接多媒体后台，注册响应命令中的 passwd字段值。
     */
    public static String PassportWord;

    /**
     * 当前节站屏（硬件设备）的eth0的Mac地址。
     */
    public static String MacAddress;

    /**
     * 从U盘检测更新最新版本的线程是否处于运行状态。
     */
    public static boolean isUpdateNewestVersionRunning = false;

    private static Context context;

    /**
     * 多媒体检测时间间隔<br>
     * 单位分钟
     */
    public static int mediaCheckPeriod = 10;

    /**
     * 过期节目单删除等待时间<br>
     * 单位天
     */
    public static int mediaDeleteCheckPeriod = 10;

    /**
     * 默认程序升级地址
     */
    public static String strUpdateURL = "http://192.168.58.95:10030";
    /**
     * 默认服务器地址
     */
    public static String strSreAdr = "http://192.168.58.95:10030";
    /**
     * 默认进出站低音量
     */
    public static int voiceLow = 2;
    /**
     * 默认正常音量值
     */
    public static int voiceHigh = 10;


    /**
     * 是否提示开门信息
     */
    public static boolean isOpenDoor = false;

    /**
     * 是否显示司机评价
     */
    public static boolean isDriverMsg = false;

    /**
     * 是否打开debug模式
     */
    public static boolean isDebug = false;
}
