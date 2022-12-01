package swing;

import clases.NuevoUsuario;
import clases.User;

import javax.crypto.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class VentanaRegistrarse extends JFrame {
    private JTextField contrasenaField;
    private JTextField usuarioField;
    private JTextField emailField;
    private JTextField edadField;
    private JTextField apellidoField;
    private JTextField nombreField;
    private JPanel panel1;
    private JButton registrarseButton;

    public VentanaRegistrarse(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        setContentPane(panel1);
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int edad = Integer.parseInt(edadField.getText());
                    if (!nombreField.getText().isBlank() && !apellidoField.getText().isBlank() && !emailField.getText().isBlank() && !usuarioField.getText().isBlank() && !contrasenaField.getText().isBlank()) {
                        oos.writeObject(2);
                        //creamos un objeto con todos los datos para registrase
                        NuevoUsuario registrarse = new NuevoUsuario(nombreField.getText(), apellidoField.getText(), edad, emailField.getText(), usuarioField.getText(), contrasenaField.getText());
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oosbytes = new ObjectOutputStream(bos);
                        //lo comvertimos a bytes
                        oosbytes.writeObject(registrarse);
                        oosbytes.flush();
                        byte[] registrarsebytes = bos.toByteArray();
                        Cipher desCipher = Cipher.getInstance("DES");
                        //configuramos modo descifrar
                        desCipher.init(Cipher.ENCRYPT_MODE, key);
                        byte[] registrarseCifrado = desCipher.doFinal(registrarsebytes);
                        //enviamos objeto cifrado
                        oos.writeObject(registrarseCifrado);
                        JOptionPane.showMessageDialog(null, "El usuario se ha creado correctamente.");
                        JFrame frame = new VentanaMenuPrincipal(ois, oos, key);
                        frame.setSize(300, 300);
                        frame.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos para registrarse.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los datos del campo de edad no son validos.");
                } catch (NoSuchPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalBlockSizeException ex) {
                    throw new RuntimeException(ex);
                } catch (BadPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
