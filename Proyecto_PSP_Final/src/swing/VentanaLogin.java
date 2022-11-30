package swing;

import clases.Login;

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
    private int PUERTO = 5050;
    private String HOST = "localhost";
    private Cipher desCipher;

    public VentanaLogin() {
        setContentPane(panel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usuarioField.getText().isBlank() && !contrasenaField.getText().isBlank()) {
                    try {
                        //le hacemos una peticion al servidor mandandole los datos del mensage y cerramos conexion
                        Socket socket = new Socket(HOST, PUERTO);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oosbytes = new ObjectOutputStream(bos);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        //creamos un objeto con todos los datos del inicio de sesion
                        Login login = new Login(usuarioField.getText(), contrasenaField.getText());
                        //lo comvertimos a bytes
                        oosbytes.writeObject(login);
                        oosbytes.flush();
                        byte[] loginbytes = bos.toByteArray();
                        //recogemos la clave
                        SecretKey key = (SecretKey) ois.readObject();
                        desCipher = Cipher.getInstance("DES");
                        //configuramos modo descifrar
                        desCipher.init(Cipher.ENCRYPT_MODE, key);
                        byte[] loginCifrado = desCipher.doFinal(loginbytes);
                        //enviamos objeto cifrado
                        oos.writeObject(loginCifrado);
                        //recogemos login
                        desCipher.init(Cipher.DECRYPT_MODE, key);
                        loginCifrado = (byte[]) ois.readObject();
                        System.out.println("h");
                        loginbytes = desCipher.doFinal(loginCifrado);

                        ByteArrayInputStream bis = new ByteArrayInputStream(loginbytes);
                        ObjectInputStream oisbytes = new ObjectInputStream(bis);
                        login = (Login) oisbytes.readObject();
                        if (login.isAcierto()) {
                            JFrame frame = new VentanaMenuPrincipal();
                            frame.setSize(300, 300);
                            frame.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos vuelve a intentarlo.");
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Ha surgido un error con el servidor intentalo mas tarde.");
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
    }
}
