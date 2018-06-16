package SocketTrans;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class uiUDPServer extends JFrame implements ActionListener {
    Container con = null;
    JPanel jp = null;
    static JTextArea jta = null;//文本区域
    JScrollPane jsp = null;//滚动条
    JTextField tf = null;
    JButton jb = null;
    static DatagramSocket ds = null;//UDP的套接字
    static byte[] buf = new byte[1000];//最大1000字节
    static DatagramPacket dp = new DatagramPacket(buf, buf.length);//数据包
    static InetAddress address = null;//用来存要发送到的客户端的地址
    static int ClientPort = -1;//客户端的端口号,在接收到客户端发来的包后就能从包里获得

    //服务器端GUI构造器
    uiUDPServer() {
        super("服务器端");
        setDefaultCloseOperation(EXIT_ON_CLOSE);//按关闭时:退出
        setExtendedState(NORMAL);//扩展状态
        setLocation(400, 250);//位置
        setSize(400, 300);//尺寸

        /*初始化顶层容器Container和中间容器JPanel*/
        con = this.getContentPane();//顶层容器直接从JFrame获得
        jp = new JPanel();//初始化一个中间容器JPanel

        /*初始化其它容器*/
        jta = new JTextArea(11, 30);//文本区域
        jta.setEditable(false);//设置不能编辑
        jta.setLineWrap(true);//设置自动换行功能
        jta.setWrapStyleWord(true);//设置断行不断单词功能
        jsp = new JScrollPane(jta);//用JTextArea初始化JScrollPane
        tf = new JTextField(24);//文本框
        jb = new JButton("发送");//按钮

        /*容器嵌套*/
        jp.add(jsp);
        jp.add(tf);
        jp.add(jb);
        con.add(jp);

        //为按钮添加动作相应
        jb.addActionListener(this);

        this.setVisible(true);
    }

    //主方法
    public static void main(String[] args) throws Exception {
        ds = new DatagramSocket(1234);//服务器端的UDP套接字,端口1234用来监听信息
        new uiUDPServer();//GUI
        while (true) {
            ds.receive(dp);//阻塞直至接收到消息
            ClientPort = dp.getPort();//从包里获取客户端用的端口号
            address = dp.getAddress();//从包里获取客户端地址
            //System.out.println(ClientPort);
            String s_rc = new String(dp.getData(), 0, dp.getLength());//把消息转换成字符串
            jta.append("[客户端(port=" + ClientPort + ")]  " + s_rc + "\r\n");//显示出来
        }
    }

    //动作监听
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();// 根据动作命令,来进行分别处理
        if (cmd.equals("发送"))//单击按钮时
        {
            if (-1 == ClientPort)//说明客户端还没有给服务器发信息,没法从包里获得端口号
            {
                JOptionPane.showMessageDialog(null, "还没有客户端连接到你!");//警告框
                tf.setText("");//清空输入框
                return;//立即结束,不做后面的事
            }
            String s_n = tf.getText();//获取内容字符串
            tf.setText("");//清空输入框
            byte[] bt = s_n.getBytes();//转变成字节数组
            //从服务器发送时要指定客户端地址和其使用的端口号
            DatagramPacket packet = new DatagramPacket(bt, bt.length, address, ClientPort);
            try {
                ds.send(packet);//用自己的UDP套接字发送这个包
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                jta.append("[你说] " + s_n + "\r\n");
            }
        }
    }

}