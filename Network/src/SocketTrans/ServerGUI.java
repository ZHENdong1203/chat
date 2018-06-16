package SocketTrans;

import javax.swing.*;
import java.awt.*;

class ServerGUI extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private static Container container = null;
    private static JPanel panel = null;
    private static JTextArea textArea = null;
    private static JScrollPane scrollPane = null;

    ServerGUI() {
        super("Server");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        container = this.getContentPane();
        panel = new JPanel();

        textArea = new JTextArea(11, 25);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(textArea);

        panel.add(textArea);
        panel.add(scrollPane);

        container.add(panel);
        this.setVisible(true);
    }

    public void showMessage(int i, String s) {
        textArea.append("[Connection " + i + "]: " + s + "\r\n");
    }
}
