package SocketTrans;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class ClientService {
    private Socket client = null;//创建client
    private InputStream inStream = null;//client的输入流，用于从server接受信息
    private OutputStream outStream = null;//client的输出流，用于向server发送信息
    private Scanner inFromServer = null;//从流中读入字符

    ClientService(Socket client) throws IOException {
        this.client = client;
        inStream = client.getInputStream();
        outStream = client.getOutputStream();
        inFromServer = new Scanner(inStream);
    }

    public String getRecvMessage() {
        return inFromServer.nextLine();
    }

    public void sendToServer(String Message) {
        PrintWriter outToServer = new PrintWriter(outStream, true);
        outToServer.println(Message);
    }

    public void endService() throws IOException {
        client.close();
        System.exit(0);
    }
}