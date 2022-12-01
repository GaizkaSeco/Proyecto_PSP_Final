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
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/proyecto_psp_final", "root", "admin");
            textArea.append("Obteniendo claves con cifrado DES\n");
            //generamos la clave y la enviamos
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            key = keygen.generateKey();
            //enviamos la clave
            oos.writeObject(key);
            desCipher = Cipher.getInstance("DES");
            textArea.append("Clave enviada.\n");
            while (true) {
                int opcion = (int) ois.readObject();
                switch (opcion) {
                    case 1:
                        textArea.append("Ha iniciado la opcin login.\n");
                        login();
                        if (user.isAcierto()) {
                            menuInicio();
                        }
                        break;
                    case 2:
                        textArea.append("Ha iniciado la opcin registrarse.\n");
                        registrarse();
                        break;
                    default:
                        textArea.append("Ha iniciado una opcion no valida operacion denegada.\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private void login() {
        try {
            desCipher.init(Cipher.DECRYPT_MODE, key);
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
                        textArea.append(user.getUsuario() + " esta accediendo a sus cuentas.\n");
                        cuentas();
                        break;
                    case 2:
                        textArea.append(user.getUsuario() + " esta accediendo a crear una nueva cuenta.\n");
                        nuevaCuenta();
                        break;
                    case 3:
                        textArea.append(user.getUsuario() + " esta accediendo a hacer una transferencia.\n");
                        break;
                    case 4:
                        textArea.append(user.getUsuario() + " esta accediendo a sus movimientos.\n");
                        break;
                    default:
                        textArea.append(user.getUsuario() + " ha iniciado una opcion no valida operacion denegada.\n");
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

    public void nuevaCuenta() {
        try{
            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] ncifrado = (byte[]) ois.readObject();
            String numero = new String(desCipher.doFinal(ncifrado));
            String query = "SELECT * FROM cuentas WHERE ncuenta = ?";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, numero);
            textArea.append(user.getUsuario() + " comprobando si el numero de cuenta existe.\n");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                textArea.append(user.getUsuario() + " numero de cuenta ya existente, operacion cancelada.\n");
                oos.writeObject(false);
            } else {
                textArea.append(user.getUsuario() + "creando numero de cuenta.\n");
                String queryInsert = "INSERT INTO cuentas(ncuenta, idusuario, saldo) VALUES(?, ?, ?)";
                PreparedStatement psInsert = conexion.prepareStatement(queryInsert);
                psInsert.setString(1, numero);
                psInsert.setInt(2, user.getId());
                psInsert.setDouble(3, 0);
                psInsert.execute();
                psInsert.close();
                textArea.append(user.getUsuario() + " numero de cuenta creado.\n");
                oos.writeObject(true);
            }
            rs.close();
            ps.close();
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void registrarse() {
        try {
            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] registrarseCifrado = (byte[]) ois.readObject();
            byte[] registraseBytes = desCipher.doFinal(registrarseCifrado);
            ByteArrayInputStream bis = new ByteArrayInputStream(registraseBytes);
            ObjectInputStream oisbytes = new ObjectInputStream(bis);
            NuevoUsuario registrarse = (NuevoUsuario) oisbytes.readObject();
            bis.close();
            oisbytes.close();
            textArea.append("Registrando nuevo usuario" + registrarse.getUsuario() + ".\n");
            String query = "INSERT INTO usuarios(nombre, apellido, edad, email, usuario, contrasena) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, registrarse.getNombre());
            ps.setString(2, registrarse.getApellido());
            ps.setInt(3, registrarse.getEdad());
            ps.setString(4, registrarse.getEmail());
            ps.setString(5, registrarse.getUsuario());
            ps.setString(6, registrarse.getContrasena());
            textArea.append("Creando un nuevo usuario llamado " + registrarse.getUsuario() + ".\n");
            ps.execute();
            ps.close();
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
