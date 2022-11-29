package swing;

import clases.HiloServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MulticastSocket;
import java.net.ServerSocket;

public class VentanaServer extends JFrame {
    private JPanel panel1;
    private JButton botonIniciar;
    private JButton botonDetener;
    private JTextArea textArea1;
    private ServerSocket server;
    private boolean activo = true;

    public VentanaServer() throws HeadlessException {
        setContentPane(panel1);
        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //iniciamos el servicio de escucha del servidor
                    server = new ServerSocket(5050);
                    textArea1.append("--Servidor Iniciado--");
                    HiloServer hilo = new HiloServer(server, textArea1, activo);
                    hilo.start();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        botonDetener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activo = false;
                dispose();
            }
        });
    }
}
