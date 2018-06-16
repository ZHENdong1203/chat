package SocketTrans;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            //创建Socket
            ClientService service=new ClientService(new Socket("127.0.0.1",23456));
            //启动GUI
            ClientGUI GUI = new ClientGUI();
            //启动client服务
            GUI.startService(service);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}