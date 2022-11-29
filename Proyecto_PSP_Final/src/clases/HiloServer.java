package clases;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class HiloServer extends Thread{
    ServerSocket server;
    JTextArea textArea;
    boolean activo;

    public HiloServer(ServerSocket server, JTextArea textArea, boolean activo) {
        this.server = server;
        this.textArea = textArea;
        this.activo = activo;
    }

    @Override
    public void run() {
        try {
            //nos quedamos en espera de solicitudes
            while (activo) {
                Socket cliente = server.accept();

            }
        } catch (IOException e) {
            System.out.println();
        }
    }
}
