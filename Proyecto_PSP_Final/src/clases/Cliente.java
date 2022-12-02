package clases;

import swing.VentanaLogin;
import swing.VentanaServer;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    //Inicia la ventana del cliente
    private static int PUERTO = 5050;
    private static String HOST = "localhost";
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PUERTO);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            SecretKey key = (SecretKey) ois.readObject();
            JFrame frame = new VentanaLogin(ois, oos, key);
            frame.setSize(400, 300);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Ha surgido un error inesperado intentalo mas tarde.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar conectarse con el servidor intentalo mas tarde.");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido cargar la ventana de inicio de sesion.");
        }
    }
}
