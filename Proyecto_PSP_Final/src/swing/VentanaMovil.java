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

public class VentanaMovil extends JFrame {
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton cerrarButton;
public VentanaMovil(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
    setContentPane(panel1);
    try {
        Cipher desCipher = Cipher.getInstance("DES");
        desCipher.init(Cipher.DECRYPT_MODE, key);
        int numero = Integer.parseInt(new String(desCipher.doFinal((byte[]) ois.readObject())));
        textArea1.append(String.valueOf(numero));
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    } catch (NoSuchPaddingException e) {
        throw new RuntimeException(e);
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    } catch (InvalidKeyException e) {
        throw new RuntimeException(e);
    } catch (IllegalBlockSizeException e) {
        throw new RuntimeException(e);
    } catch (BadPaddingException e) {
        throw new RuntimeException(e);
    }
    cerrarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    });
}
}
