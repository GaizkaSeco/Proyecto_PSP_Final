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

public class VentanaCodigo extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JTextField numeroField;

public VentanaCodigo(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
    setContentPane(panel1);
    button1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (!numeroField.getText().isBlank()) {
                    Cipher desCipher = Cipher.getInstance("DES");
                    desCipher.init(Cipher.ENCRYPT_MODE, key);
                    byte[] codigoCirado = desCipher.doFinal(numeroField.getText().getBytes());
                    oos.writeObject(codigoCirado);
                    if ((boolean) ois.readObject()) {
                        JOptionPane.showMessageDialog(null, "Numero correcto, iniciando transferencia.");

                    } else {
                        JOptionPane.showMessageDialog(null, "Numero incorrecto operacion cancelada.");
                    }
                    JFrame frame = new VentanaMenuPrincipal(ois, oos, key);
                    frame.setSize(300, 300);
                    frame.setVisible(true);
                    dispose();
                } else {
                    //poner que rellene el campo
                }
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
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
}
}
