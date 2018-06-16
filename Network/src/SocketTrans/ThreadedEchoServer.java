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
                InputStream inStream = inComing.getInputStream();//输入流，用于接受client通信
                OutputStream outStream = inComing.getOutputStream();//输出流，用于向client回信
                Scanner in = new Scanner(inStream);
                //开启autoFlush可以保证每次回信都刷新缓冲区
                PrintWriter out = new PrintWriter(outStream, true);
                out.println("Hello!");//连接建立时向client发送"Hello!"

                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    GUI.showMessage(cnt, line);
                    out.println(line);
                }
            } finally {
                //通信结束后打印“连接终止”
                GUI.showMessage(cnt, "Connection " + cnt + " is terminated.");
                inComing.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}