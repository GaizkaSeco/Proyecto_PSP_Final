package clases;

import swing.VentanaLogin;
import swing.VentanaServer;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
