package clases;

import swing.VentanaLogin;
import swing.VentanaServer;

import javax.swing.*;

public class Cliente {
    //Inicia la ventana del cliente
    public static void main(String[] args) {
        JFrame frame = new VentanaLogin();
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
