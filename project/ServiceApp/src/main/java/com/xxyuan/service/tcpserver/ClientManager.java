package com.xxyuan.service.tcpserver;

/**
 * author:xuxiaoyuan
 * date:2019/5/7
 */
import android.content.Context;
import android.content.SharedPreferences;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

// 客户端的管理类
public class ClientManager {
    private static SharedPreferences sp;
    private static Context mContect;
    private static String lola = null;//经纬度
    private static String addr = null;//详细地址
    private static String province =null;//省
    private static String city =null;//市
    private static String district = null;//区
    private static String street = null;//街道
    private static String streetnumber = null;
    private static String locationdescribe = null;

    private static Map<String,Socket> clientList = new HashMap<>();
    private static ServerThread serverThread = null;
    private static PrintStream writer;

    private static class ServerThread implements Runnable {

        private int port = 1234;
        private boolean isExit = false;
        private ServerSocket server;

        public ServerThread() {
            try {
                server = new ServerSocket(port);
                System.out.println("启动服务成功" + "port:" + port);
            } catch (IOException e) {
                System.out.println("启动server失败，错误原因：" + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                while (!isExit) {
                    // 进入等待环节
                    System.out.println("等待设备的连接... ... ");
                    final Socket socket = server.accept();
                    // 获取手机连接的地址及端口号
                    final String address = socket.getRemoteSocketAddress().toString();
                    System.out.println("连接成功，连接的设备为：" + address);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 单线程索锁
                                synchronized (this){
                                    // 放进到Map中保存
                                    clientList.put(address,socket);
                                }
                                // 定义输入流
                                InputStream inputStream = socket.getInputStream();

                                InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");

                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = inputStream.read(buffer)) != -1){
                                    String text = new String(buffer,0,len);
                                    System.out.println("收到的数据为：\n" + text);

                                    // 必须先关闭输入流才能获取下面的输出流
                                    socket.shutdownInput();
                                    // 在这里群发消息
                                    sendGps();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                System.out.println("错误信息为：" + e.getMessage());
                            }finally {
                                synchronized (this){
                                    System.out.println("关闭链接：" + address);
                                    clientList.remove(address);
                                }
                            }
                        }
                    }).start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void Stop(){
            isExit = true;
            if (server != null){
                try {
                    server.close();
                    System.out.println("已关闭server");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ServerThread startServer(Context contect){
        mContect=contect;
        System.out.println("开启服务");
        if (serverThread != null){
            showDown();
        }
        serverThread = new ServerThread();
        new Thread(serverThread).start();
        System.out.println("开启服务成功");
        return serverThread;
    }

    // 关闭所有server socket 和 清空Map
    public static void showDown(){
        if (serverThread!=null&&clientList!=null){
            for (Socket socket : clientList.values()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer!=null){
                writer.close();
            }
            serverThread.Stop();
            clientList.clear();

        }

    }

    // 群发的方法
    public static boolean sendMsgAll(){
        try {
            for (Socket socket : clientList.values()) {
                String returnServer="lola="+lola+"&addr="+addr+"&province="+province+"&city="+city+"&district="+district+"&street="+ street+"&streetnumber="+streetnumber+"&locationdescribe="+locationdescribe;
                if (writer!=null){
                    writer.close();
                }
                writer = new PrintStream(socket.getOutputStream());//告诉客户端连接成功 并传状态过去
                writer.println("HTTP/1.1 200 OK");// 返回应答消息,并结束应答
                writer.println("Content-Length: "+ returnServer.getBytes().length );// 返回内容字节数
                writer.println("Content-Type: text/plain;charset=utf-8");
                writer.println();// 根据 HTTP 协议, 空行将结束头信息
                writer.write(returnServer.getBytes());
                writer.flush();
                // 关闭输出流
                socket.shutdownOutput();

//                OutputStream outputStream = socket.getOutputStream();
//                outputStream.write("文件接收成功".getBytes());
//                outputStream.flush();//直接返回缓存区数据
//                outputStream.close();
//                writer.write(returnServer.getBytes());
//                writer.close();
//                OutputStream outputStream = socket.getOutputStream();
//                outputStream.write(msg.getBytes("utf-8"));
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private static void sendGps(){

        sp = mContect.getSharedPreferences("Location", Context.MODE_PRIVATE);
        province = sp.getString("province","");
        city = sp.getString("city","");
        district = sp.getString("district","");
        street = sp.getString("street","");
        streetnumber = sp.getString("streetnumber","");
        locationdescribe = sp.getString("locationdescribe","");
        lola = sp.getString("lola","");
        addr = sp.getString("addr","");
        sendMsgAll();

    }

}
