package com.xxyuan.service.tcpserver;

import android.content.Intent;
import android.util.Log;

import com.xxyuan.service.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jason Zhu on 2017-04-24.
 * Email: cloud_happy@163.com
 */

public class TcpServer implements Runnable{
    private String TAG = "TcpServer";
    private int port = 1234;
    private boolean isListen = true;   //线程监听标志位
    public ArrayList<ServerSocketThread> SST = new ArrayList<ServerSocketThread>();
    public TcpServer(int port){
        this.port = port;
    }

    //更改监听标志位
    public void setIsListen(boolean b){
        isListen = b;
    }

    public void closeSelf(){
        isListen = false;
        for (ServerSocketThread s : SST){
            s.isRun = false;
        }
        SST.clear();
    }

    private Socket getSocket(ServerSocket serverSocket){
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "run: 监听超时");
            return null;
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(4*60*1000);
            while (isListen){
                Log.i(TAG, "run: 开始监听...");
                Socket socket = getSocket(serverSocket);
                if (socket != null){
                    new ServerSocketThread(socket);
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ServerSocketThread extends Thread{
        Socket socket = null;
        private PrintWriter pw;
        private InputStream is = null;
        private OutputStream os = null;
        private String ip = null;
        private boolean isRun = true;

        ServerSocketThread(Socket socket){
            this.socket = socket;
            ip = socket.getInetAddress().toString();
            Log.i(TAG, "ServerSocketThread:检测到新的客户端联入,ip:" + ip);

            try {
                socket.setSoTimeout(5000);
                os = socket.getOutputStream();
                is = socket.getInputStream();
                pw = new PrintWriter(os,true);
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            byte buff[]  = new byte[4096];
            String rcvMsg;
            int rcvLen;
            SST.add(this);
            while (isRun && !socket.isClosed() && !socket.isInputShutdown()){
                try {
                    if ((rcvLen = is.read(buff)) != -1 ){
                        rcvMsg = new String(buff,0,rcvLen);
                        Log.i(TAG, "run:收到消息: " + rcvMsg);
                        Intent intent =new Intent();
                        intent.setAction("tcpServerReceiver");
                        intent.putExtra("tcpServerReceiver",rcvMsg);
//                        MainActivity..sendBroadcast(intent);//将消息发送给主界面
                        if (rcvMsg.equals("QuitServer")){
                            isRun = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
                SST.clear();
                Log.i(TAG, "run: 断开连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}