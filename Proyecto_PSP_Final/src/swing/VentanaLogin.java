package swing;

import clases.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class VentanaLogin extends JFrame {
    private JButton button1;
    private JPanel panel;
    private JButton button2;
    private JTextField contrasenaField;
    private JTextField usuarioField;
    private int PUERTO = 5050;
    private String HOST = "localhost";

    public VentanaLogin() {
        setContentPane(panel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usuarioField.getText().isBlank() && !contrasenaField.getText().isBlank()) {
                    try {
                        //le hacemos una peticion al servidor mandandole los datos del mensage y cerramos conexion
                        Socket socket = new Socket(HOST, PUERTO);
                        Login login = new Login(usuarioField.getText(), contrasenaField.getText());
                        ObjectOutputStream datos = new ObjectOutputStream(socket.getOutputStream());
                        datos.writeObject(login);
                        ObjectInputStream res = new ObjectInputStream(socket.getInputStream());
                        login = (Login) res.readObject();
                        if(login.isAcierto()) {
                            JFrame frame = new VentanaMenuPrincipal();
                            frame.setSize(300, 300);
                            frame.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos vuelve a intentarlo.");
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Ha surgido un error con el servidor intentalo mas tarde.");
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
