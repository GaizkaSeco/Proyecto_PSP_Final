package swing;

import clases.NuevoUsuario;

import javax.crypto.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VentanaRegistrarse extends JFrame {
    private JTextField contrasenaField;
    private JTextField usuarioField;
    private JTextField emailField;
    private JTextField edadField;
    private JTextField apellidoField;
    private JTextField nombreField;
    private JPanel panel1;
    private JButton registrarseButton;
    private JButton cancelarButton;

    public VentanaRegistrarse(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        setContentPane(panel1);
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int edad = Integer.parseInt(edadField.getText());
                    Pattern mayus = Pattern.compile("^([A-Z]{1}[a-z]+)$");
                    if (!nombreField.getText().isBlank() && !apellidoField.getText().isBlank() && !emailField.getText().isBlank() && !usuarioField.getText().isBlank() && !contrasenaField.getText().isBlank()) {
                        Matcher nombre = mayus.matcher(nombreField.getText());
                        Matcher apellido = mayus.matcher(apellidoField.getText());
                        if (nombre.find() && apellido.find()) {
                            //duda preguntar
                            Pattern edadp = Pattern.compile("^[0-9]{1,3}$");
                            Matcher edadmatch = edadp.matcher(edadField.getText());
                            if (edadmatch.find()) {
                                oos.writeObject(2);
                                //creamos un objeto con todos los datos para registrase
                                MessageDigest md = MessageDigest.getInstance("SHA");
                                byte[] contrasenaBytes = contrasenaField.getText().getBytes();
                                md.update(contrasenaBytes);
                                byte[] resumen = md.digest();
                                String hashContrasena = new String(resumen);
                                NuevoUsuario registrarse = new NuevoUsuario(nombreField.getText(), apellidoField.getText(), edad, emailField.getText(), usuarioField.getText(), hashContrasena);
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
                                JFrame frame = new VentanaLogin(ois, oos, key);
                                frame.setSize(300, 300);
                                frame.setVisible(true);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Los datos del campo edad no son correctos.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Comprueba que el nombre y apellido esta bien escrito.");
                        }
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
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new VentanaLogin(ois, oos, key);
                frame.setSize(300, 300);
                frame.setVisible(true);
                dispose();
            }
        });
    }
}
