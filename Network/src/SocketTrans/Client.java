package SocketTrans;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            //����Socket
            ClientService service=new ClientService(new Socket("127.0.0.1",23456));
            //����GUI
            ClientGUI GUI = new ClientGUI();
            //����client����
            GUI.startService(service);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}