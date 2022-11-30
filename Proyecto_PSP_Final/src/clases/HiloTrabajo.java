package clases;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HiloTrabajo extends Thread {
    private Socket c = new Socket();
    private JTextArea textArea;
    boolean activo;
    private User user;
    private Connection conexion;
    private Cipher desCipher;
    private SecretKey key;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    //private ByteArrayOutputStream bos;
    //private ObjectOutputStream oosbytes;

    public HiloTrabajo(Socket c, JTextArea textArea, boolean activo) {
        this.c = c;
        this.textArea = textArea;
        this.activo = activo;
    }

    @Override
    public void run() {
        try {
            textArea.append("\nSe ha conectado un usuario.\n");
            oos = new ObjectOutputStream(c.getOutputStream());
            ois = new ObjectInputStream(c.getInputStream());
            //bos = new ByteArrayOutputStream();
            //oosbytes = new ObjectOutputStream(bos);
            while (true) {
                int opcion = (int) ois.readObject();
                switch (opcion) {
                    case 1:
                        textArea.append("Ha iniciado la opcin login.");
                        login();
                        if (user.isAcierto()) {
                            menuInicio();
                        }
                        break;
                    case 2:
                        textArea.append("Ha iniciado la opcin registrarse.");
                        //regstrarse();
                        break;
                    default:
                        textArea.append("Ha iniciado una opcion no valida operacion denegada.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void login() {
        try {
            //conexion a la bd
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/proyecto_psp_final", "root", "admin");
            textArea.append("Obteniendo claves con cifrado DES\n");
            //generamos la clave y la enviamos
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            key = keygen.generateKey();
            desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, key);
            //enviamos la clave
            oos.writeObject(key);
            textArea.append("Clave enviada.\n");
            //recogemos el login
            textArea.append("Recuperando datos.\n");
            byte[] loginCifrado = (byte[]) ois.readObject();
            byte[] loginBytes = desCipher.doFinal(loginCifrado);
            ByteArrayInputStream bis = new ByteArrayInputStream(loginBytes);
            ObjectInputStream oisbytes = new ObjectInputStream(bis);
            user = (User) oisbytes.readObject();
            textArea.append("Comprobando credenciales.\n");
            String query = "SELECT * FROM usuarios WHERE usuario = ? and contrasena = ?";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, user.getUsuario());
            ps.setString(2, user.getContrasena());
            textArea.append("Ejecutando consulta a la base de datos.\n");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                textArea.append("Credenciales correctos.\n");
                user.setId(rs.getInt(1));
                System.out.println(user.getId());
                user.setAcierto(true);
            }
            if (!user.isAcierto()) {
                textArea.append("Credenciales erroneos.\n");
            }
            ps.close();
            //lo comvertimos a bytes y ciframos
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oosbytes = new ObjectOutputStream(bos);
            oosbytes.writeObject(user);
            oosbytes.flush();
            byte[] loginbytes = bos.toByteArray();
            desCipher.init(Cipher.ENCRYPT_MODE, key);
            loginCifrado = desCipher.doFinal(loginbytes);
            oos.writeObject(loginCifrado);
            bos.close();
            oosbytes.close();
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

    public void menuInicio() {
        while (true) {
            try {
                int opcion = (int) ois.readObject();
                switch (opcion) {
                    case 1:
                        textArea.append(user.getUsuario() + ": esta accediendo a sus cuentas.\n");
                        cuentas();
                        break;
                    case 2:
                        break;
                    default:
                        textArea.append(user.getUsuario() + ": ha iniciado una opcion no valida operacion denegada.\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cuentas() {
        try {
            //recuperar las cuentas del usuario
            List<Cuenta> cuentas = new ArrayList<Cuenta>();
            String query = "SELECT * FROM cuentas WHERE idusuario = ?";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Cuenta cuenta = new Cuenta(rs.getInt(1), rs.getString(2), rs.getDouble(3));
                cuentas.add(cuenta);
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oosbytes = new ObjectOutputStream(bos);
            oosbytes.writeObject(cuentas);
            oosbytes.flush();
            byte[] cuentasbytes = bos.toByteArray();
            desCipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cuentasCifradas = desCipher.doFinal(cuentasbytes);
            oos.writeObject(cuentasCifradas);
            bos.close();
            oosbytes.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
