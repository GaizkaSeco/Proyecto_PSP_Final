package clases;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class HiloTrabajo extends Thread {
    Socket c = new Socket();
    JTextArea textArea;
    boolean activo;

    public HiloTrabajo(Socket c, JTextArea textArea, boolean activo) {
        this.c = c;
        this.textArea = textArea;
        this.activo = activo;
    }

    @Override
    public void run() {
        textArea.append("Se ha conectado un usuario.\n");
        byte[] mensajeRecibido = null;
        KeyGenerator keygen = null;
        Cipher desCipher = null;
        String mensajeRecibidoDescifrado = "";
        try {
            //conexion a la bd
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/proyecto_psp_final", "root", "admin");
            textArea.append("Obteniendo claves con cifrado DES\n");
            //generamos la clave y la enviamos
            keygen = KeyGenerator.getInstance("DES");
            SecretKey key = keygen.generateKey();
            desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, key);
            ObjectOutputStream oos = new ObjectOutputStream(c.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(c.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oosbytes = new ObjectOutputStream(bos);
            //enviamos la clave
            oos.writeObject(key);
            textArea.append("Clave enviada.\n");
            //recogemos el login
            textArea.append("Recuperando datos.\n");
            byte[] loginCifrado = (byte[]) ois.readObject();
            byte[] loginBytes = desCipher.doFinal(loginCifrado);
            textArea.append("Recuperando datos.\n");
            ByteArrayInputStream bis = new ByteArrayInputStream(loginBytes);
            ObjectInputStream oisbytes = new ObjectInputStream(bis);
            Login login = (Login) oisbytes.readObject();
            textArea.append("Comprobando credenciales.\n");
            String query = "SELECT * FROM usuarios WHERE usuario = ? and contrasena = ?";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, login.getUsuario());
            ps.setString(2, login.getContrasena());
            textArea.append("Ejecutando consulta a la base de datos.\n");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                textArea.append("Credenciales correctos.\n");
                login.setAcierto(true);
            }
            if (!login.isAcierto()) {
                textArea.append("Credenciales erroneos.\n");
            }
            ps.close();
            //lo comvertimos a bytes
            oosbytes.writeObject(login);
            oosbytes.flush();
            byte[] loginbytes = bos.toByteArray();
            desCipher.init(Cipher.ENCRYPT_MODE, key);
            loginCifrado = desCipher.doFinal(loginbytes);
            oos.writeObject(loginCifrado);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            textArea.append("Error al generar el algoritmo\n");
        } catch (InvalidKeyException | IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
