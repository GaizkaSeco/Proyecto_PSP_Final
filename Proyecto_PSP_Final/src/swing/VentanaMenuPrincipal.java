package swing;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class VentanaMenuPrincipal extends JFrame {
    private JPanel panel;
    private JButton consultarCuentasButton;
    private JButton movimientosButton;
    private JButton nuevaTranferenciaButton;
    private JButton nuevaCuentaButton;

    public VentanaMenuPrincipal(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key){
        setContentPane(panel);
        consultarCuentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new VentanaCuentas(ois, oos, key);
                frame.setSize(300, 300);
                frame.setVisible(true);
                dispose();
            }
        });
        movimientosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new VentanaMovimientos(ois, oos, key);
                frame.setSize(300, 300);
                frame.setVisible(true);
                dispose();
            }
        });
        nuevaCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new VentanaNuevaCuenta(ois, oos, key);
                frame.setSize(300, 300);
                frame.setVisible(true);
                dispose();
            }
        });
    }
}
