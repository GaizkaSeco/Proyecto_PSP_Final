package clases;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
            //configguramos el log
            Logger logger = Logger.getLogger("MyLog");
            FileHandler fh = new FileHandler(".\\src\\MyLogFile.log", true);
            logger.setUseParentHandlers(false);
            SimpleFormatter formato = new SimpleFormatter();
            fh.setFormatter(formato);
            logger.setLevel(Level.ALL);
            //nos quedamos en espera de solicitudes
            while (activo) {
                Socket cliente = server.accept();
                HiloTrabajo hilo = new HiloTrabajo(cliente, textArea, activo, logger, fh);
                hilo.start();
            }
        } catch (IOException e) {
            System.out.println();
        }
    }
}
