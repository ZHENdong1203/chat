package SocketTrans;

import java.io.*;
import java.net.*;
import java.util.*;


public class ThreadedEchoServer {
    private static ServerSocket server = null;
    private static Socket inComing = null;

    public static void main(String[] args) {
        ServerGUI GUI = new ServerGUI();
        try {
            int i = 1;
            server = new ServerSocket(23456);
            while (true) {
                inComing = server.accept();
                GUI.showMessage(i, "Connection " + i + " has established.");
                Runnable r = new ThreadedEchoHandler(inComing, i, GUI);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ThreadedEchoHandler implements Runnable {
    private Socket inComing;
    private int cnt;
    ServerGUI GUI;

    public ThreadedEchoHandler(Socket i, int cnt, ServerGUI GUI) {
        inComing = i;
        this.cnt = cnt;
        this.GUI = GUI;
    }

    public void run() {
        try {
            try {
                InputStream inStream = inComing.getInputStream();//�����������ڽ���clientͨ��
                OutputStream outStream = inComing.getOutputStream();//�������������client����
                Scanner in = new Scanner(inStream);
                //����autoFlush���Ա�֤ÿ�λ��Ŷ�ˢ�»�����
                PrintWriter out = new PrintWriter(outStream, true);
                out.println("Hello!");//���ӽ���ʱ��client����"Hello!"

                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    GUI.showMessage(cnt, line);
                    out.println(line);
                }
            } finally {
                //ͨ�Ž������ӡ��������ֹ��
                GUI.showMessage(cnt, "Connection " + cnt + " is terminated.");
                inComing.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}