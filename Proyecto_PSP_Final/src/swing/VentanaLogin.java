package swing;

import clases.User;

import javax.crypto.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class VentanaLogin extends JFrame {
    private JButton button1;
    private JPanel panel;
    private JButton button2;
    private JTextField contrasenaField;
    private JTextField usuarioField;
    private JButton registrarseButton;
    private int PUERTO = 5050;
    private String HOST = "localhost";
    private Cipher desCipher;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private SecretKey key;

    public VentanaLogin() {
        setContentPane(panel);
        try {
            Socket socket = new Socket(HOST, PUERTO);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            key = (SecretKey) ois.readObject();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usuarioField.getText().isBlank() && !contrasenaField.getText().isBlank()) {
                    try {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oosbytes = new ObjectOutputStream(bos);
                        oos.writeObject(1);
                        //creamos un objeto con todos los datos del inicio de sesion
                        User user = new User(usuarioField.getText(), contrasenaField.getText());
                        //lo comvertimos a bytes
                        oosbytes.writeObject(user);
                        oosbytes.flush();
                        byte[] userbytes = bos.toByteArray();
                        //recogemos la clave
                        desCipher = Cipher.getInstance("DES");
                        //configuramos modo encriptar
                        desCipher.init(Cipher.ENCRYPT_MODE, key);
                        byte[] userCifrado = desCipher.doFinal(userbytes);
                        //enviamos objeto cifrado
                        oos.writeObject(userCifrado);
                        //recogemos login
                        desCipher.init(Cipher.DECRYPT_MODE, key);
                        userCifrado = (byte[]) ois.readObject();
                        userbytes = desCipher.doFinal(userCifrado);
                        ByteArrayInputStream bis = new ByteArrayInputStream(userbytes);
                        ObjectInputStream oisbytes = new ObjectInputStream(bis);
                        user = (User) oisbytes.readObject();
                        bis.close();
                        oisbytes.close();
                        if (user.isAcierto()) {
                            JFrame frame = new VentanaMenuPrincipal(ois, oos, key);
                            frame.setSize(300, 300);
                            frame.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos vuelve a intentarlo.");
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Ha surgido un error con el servidor intentalo mas tarde.");
                        ex.printStackTrace();
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
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR tienes que rellenar todos los campos.");
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new VentanaRegistrarse(ois, oos, key);
                frame.setSize(400, 400);
                frame.setVisible(true);
                dispose();
            }
        });
    }
}
