package SocketTrans;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class ClientService {
    private Socket client = null;//����client
    private InputStream inStream = null;//client�������������ڴ�server������Ϣ
    private OutputStream outStream = null;//client���������������server������Ϣ
    private Scanner inFromServer = null;//�����ж����ַ�

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