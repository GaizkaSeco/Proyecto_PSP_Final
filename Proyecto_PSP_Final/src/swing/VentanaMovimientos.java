package swing;

import clases.User;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class VentanaMovimientos extends JFrame {
    private JPanel panel;
    private JTable table1;
    private JButton atrasButton;
    private SecretKey key;
    private Socket socket;

    public VentanaMovimientos(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        this.key = key;
        this.socket = socket;
        setContentPane(panel);
        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new VentanaMenuPrincipal(ois, oos, key);
                frame.setSize(300, 300);
                frame.setVisible(true);
                dispose();
            }
        });
    }
}
