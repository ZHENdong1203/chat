package SocketTrans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ClientGUI extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private static Container container = null;
    private static JPanel panel = null;
    private static JTextArea textArea = null;
    private static JTextField textField = null;
    private static JButton button = null;
    private static JScrollPane scrollPane = null;
    private static String inputMessage = null;
    private static String cmd = null;
    private ClientService client;

    ClientGUI() {
        super("Client");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        container = this.getContentPane();
        panel = new JPanel();

        textArea = new JTextArea(6, 25);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(textArea);
        textField = new JTextField(11);
        button = new JButton("send");

        panel.add(textArea);
        panel.add(scrollPane);
        panel.add(textField);
        panel.add(button);

        buttonAction action = new buttonAction();
        button.addActionListener(action);

        container.add(panel);
        this.setVisible(true);
    }

    public void startService(ClientService client) {
        this.client = client;
        String recvMessage = client.getRecvMessage();
        serverMessage(recvMessage);
    }

    private class buttonAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            cmd = event.getActionCommand();
            if (cmd.equals("send")) {
                inputMessage = textField.getText();
                textField.setText("");

                //当按下send按钮则调用ClientService类中的方法向Server发送信息
                client.sendToServer(inputMessage);
                userMessage(inputMessage);
                String recvMessage = client.getRecvMessage();
                serverMessage(recvMessage);

            }
        }
    }

    private void userMessage(String s) {
        textArea.append("[User]: " + s + "\r\n");
    }

    private void serverMessage(String s) {
        textArea.append("[Server]: " + s + "\r\n");
    }
}
