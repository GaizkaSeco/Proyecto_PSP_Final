package swing;

import javax.crypto.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class VentanaNuevaCuenta extends JFrame {
    private JButton anadirButton;
    private JTextField numeroField;
    private JPanel panel;
    private JButton atrasButton;

    public VentanaNuevaCuenta(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        setContentPane(panel);
        anadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!numeroField.getText().isBlank()) {
                        oos.writeObject(2);
                        Cipher desCipher = Cipher.getInstance("DES");
                        desCipher.init(Cipher.ENCRYPT_MODE, key);
                        byte[] nencriptado = desCipher.doFinal(numeroField.getText().getBytes());
                        oos.writeObject(nencriptado);
                        if ((boolean) ois.readObject()) {
                            JOptionPane.showMessageDialog(null, "EL numero de cuenta se ha creado correctamente.");
                            JFrame frame = new VentanaMenuPrincipal(ois, oos, key);
                            frame.setSize(300, 300);
                            frame.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "El numero de cuenta ya existe, usa otro.");
                            numeroField.setText("");
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalBlockSizeException ex) {
                    throw new RuntimeException(ex);
                } catch (BadPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
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