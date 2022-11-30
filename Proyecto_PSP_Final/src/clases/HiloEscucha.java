package clases;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HiloEscucha extends Thread {
    ServerSocket server;
    JTextArea textArea;
    boolean activo;

    public HiloEscucha(ServerSocket server, JTextArea textArea, boolean activo) {
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
                HiloTrabajo hilo = new HiloTrabajo(cliente, textArea, activo);
                hilo.start();
            }
        } catch (IOException e) {
            System.out.println();
        }
    }
}
