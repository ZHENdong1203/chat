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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class uiUDPClient extends JFrame implements ActionListener {
    Container con = null;
    JPanel jp = null;
    static JTextArea jta = null;//�ı�����
    JScrollPane jsp = null;//������
    JTextField tf = null;
    JButton jb = null;
    static DatagramSocket ds = null;//UDP���׽���
    static byte[] buf = new byte[1000];//���1000�ֽ�
    static DatagramPacket dp = new DatagramPacket(buf, buf.length);//���ݰ�
    static InetAddress address = null;//������Ҫ���͵��ķ������ĵ�ַ

    //�ͻ���GUI������
    uiUDPClient() {
        super("�ͻ���");
        setDefaultCloseOperation(EXIT_ON_CLOSE);//���ر�ʱ:�˳�
        setExtendedState(NORMAL);//��չ״̬
        setLocation(400, 250);//λ��
        setSize(400, 300);//�ߴ�

        /*��ʼ����������Container���м�����JPanel*/
        con = this.getContentPane();//��������ֱ�Ӵ�JFrame���
        jp = new JPanel();//��ʼ��һ���м�����JPanel

        /*��ʼ����������*/
        jta = new JTextArea(11, 30);//�ı�����
        jta.setEditable(false);//���ò��ܱ༭
        jta.setLineWrap(true);//�����Զ����й���
        jta.setWrapStyleWord(true);//���ö��в��ϵ��ʹ���
        jsp = new JScrollPane(jta);//��JTextArea��ʼ��JScrollPane
        tf = new JTextField(24);//�ı���
        jb = new JButton("����");//��ť

        /*����Ƕ��*/
        jp.add(jsp);
        jp.add(tf);
        jp.add(jb);
        con.add(jp);

        //Ϊ��ť��Ӷ�����Ӧ
        jb.addActionListener(this);

        this.setVisible(true);
    }


    //������
    public static void main(String[] args) throws Exception {
        address = InetAddress.getByName("localhost");
        ds = new DatagramSocket();//�ͻ��˵�UDP�׽���,������ʲô�˿�
        new uiUDPClient();//GUI
        while (true) {
            ds.receive(dp);//����ֱ�����յ���Ϣ
            String s_rc = new String(dp.getData(), 0, dp.getLength());//����Ϣת�����ַ���
            jta.append("[��������]  " + s_rc + "\r\n");//��ʾ����
        }
    }

    //��������
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();//���ݶ�������,�����зֱ���
        if (cmd.equals("����"))//������ťʱ
        {
            String s_n = tf.getText();//��ȡ����
            tf.setText("");//��������
            //System.out.println(s_n);
            byte[] bt = s_n.getBytes();//ת����ֽ�����
            //�ӿͻ��˷���ʱҪָ����������ַ��������Ķ˿ں�
            DatagramPacket packet = new DatagramPacket(bt, bt.length, address, 1234);
            try {
                ds.send(packet);//���Լ���UDP�׽��ַ��������
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                jta.append("[��˵]  " + s_n + "\r\n");
            }
        }
    }

}