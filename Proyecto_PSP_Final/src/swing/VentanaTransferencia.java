package swing;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class VentanaTransferencia extends JFrame {
    private JPanel panel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton transferirButton;
    private JTextField textField1;
    private JButton atrasButton;

    public VentanaTransferencia(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
    transferirButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                oos.writeObject(3);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
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
