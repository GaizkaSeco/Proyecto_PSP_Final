package clases;

import swing.VentanaServer;

import javax.swing.*;

public class Servidor {
    //Inicia la ventana del server
    public static void main(String[] args) {
        JFrame frame = new VentanaServer();
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
}
