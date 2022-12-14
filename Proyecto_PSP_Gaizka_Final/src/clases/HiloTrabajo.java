package clases;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Logger logger;
    private FileHandler fh;

    public HiloTrabajo(Socket c, JTextArea textArea, boolean activo, Logger logger, FileHandler fh) {
        this.c = c;
        this.textArea = textArea;
        this.activo = activo;
        this.fh = fh;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            //se conecta el usuario
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
            while (activo) {
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
                    case 3:
                        //enviamos el documento con firma digital
                        firmaDigital();
                        break;
                    default:
                        textArea.append("Ha iniciado una opcion no valida operacion denegada.\n");
                }
            }
        } catch (NoSuchPaddingException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ha surgido un error al realizar una sentenia SQL");
            logger.addHandler(fh);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, "El algoritmo usado no es correcto");
            logger.addHandler(fh);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "No se ha podido realizar el casteo de datos");
            logger.addHandler(fh);
        } catch (IOException ignored) {

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
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ha surgido un error al realizar una sentenia SQL");
            logger.addHandler(fh);
        } catch (InvalidKeyException e) {
            logger.log(Level.WARNING, "La clave no es la correcta para poder descifrar");
            logger.addHandler(fh);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "No se ha podido realizar el casteo de datos");
            logger.addHandler(fh);
        } catch (IOException ignored) {

        }
    }

    public void menuInicio() {
        boolean repetir = true;
        while (repetir) {
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
                        cargarCuentas();
                        break;
                    case 4:
                        textArea.append(user.getUsuario() + " esta accediendo a sus movimientos.\n");
                        cargarMovimientos();
                        break;
                    case 5:
                        textArea.append(user.getUsuario() + " esta accediendo a hacer una transferencia.\n");
                        hacerTransferencia();
                        break;
                    case 6:
                        repetir = false;
                        break;
                    default:
                        textArea.append(user.getUsuario() + " ha iniciado una opcion no valida operacion denegada.\n");
                }
            } catch (IOException ignored) {
            } catch (ClassNotFoundException e) {
                logger.log(Level.WARNING, "No se ha podido realizar el casteo de datos");
                logger.addHandler(fh);
            }
        }
    }

    public void firmaDigital() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            KeyPair par = keyGen.generateKeyPair();
            PrivateKey clavepriv = par.getPrivate();
            PublicKey clavepub = par.getPublic();
            oos.writeObject(clavepub);
            String documento = "documento con los terminos.";
            oos.writeObject(documento);
            Signature rsa = Signature.getInstance("SHA1WITHRSA");
            rsa.initSign(clavepriv);
            rsa.update(documento.getBytes());
            byte[] firma = rsa.sign();
            oos.writeObject(firma);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, "El algoritmo usado no es correcto");
            logger.addHandler(fh);
        } catch (SignatureException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (InvalidKeyException e) {
            logger.log(Level.WARNING, "La clave no es la correcta para poder descifrar");
            logger.addHandler(fh);
        } catch (IOException ignored) {

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
            while (rs.next()) {
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
            logger.log(Level.WARNING, "Ha surgido un error al realizar una sentenia SQL");
            logger.addHandler(fh);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (InvalidKeyException e) {
            logger.log(Level.WARNING, "La clave no es la correcta para poder descifrar");
            logger.addHandler(fh);
        } catch (IOException ignored) {

        }
    }

    public void nuevaCuenta() {
        try {
            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] ncifrado = (byte[]) ois.readObject();
            String numero = new String(desCipher.doFinal(ncifrado));
            String query = "SELECT * FROM cuentas WHERE ncuenta = ?";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, numero.toUpperCase());
            textArea.append(user.getUsuario() + " comprobando si el numero de cuenta existe.\n");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                textArea.append(user.getUsuario() + " numero de cuenta ya existente, operacion cancelada.\n");
                oos.writeObject(false);
            } else {
                textArea.append(user.getUsuario() + "creando numero de cuenta.\n");
                String queryInsert = "INSERT INTO cuentas(ncuenta, idusuario, saldo) VALUES(?, ?, ?)";
                PreparedStatement psInsert = conexion.prepareStatement(queryInsert);
                psInsert.setString(1, numero.toUpperCase());
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
            logger.log(Level.WARNING, "La clave no es la correcta para poder descifrar");
            logger.addHandler(fh);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ha surgido un error al realizar una sentenia SQL");
            logger.addHandler(fh);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "No se ha podido realizar el casteo de datos");
            logger.addHandler(fh);
        } catch (IOException ignored) {

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
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ha surgido un error al realizar una sentenia SQL");
            logger.addHandler(fh);
        } catch (IOException ignored) {

        } catch (InvalidKeyException e) {
            logger.log(Level.WARNING, "La clave no es la correcta para poder descifrar");
            logger.addHandler(fh);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "No se ha podido realizar el casteo de datos");
            logger.addHandler(fh);
        }
    }

    public void hacerTransferencia() {
        try {
            textArea.append("inciando tranferencia de " + user.getUsuario() + "\n");
            byte[] transCifrada = (byte[]) ois.readObject();
            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] transBytes = desCipher.doFinal(transCifrada);
            ByteArrayInputStream bis = new ByteArrayInputStream(transBytes);
            ObjectInputStream oisbytes = new ObjectInputStream(bis);
            Transferencia transferencia = (Transferencia) oisbytes.readObject();
            //enviar codigo de seguridad aqui
            Random r = new Random();
            int numero = r.nextInt(1000, 9999);
            textArea.append("El numero de seguridad de " + user.getUsuario() + " es " + numero + "\n");
            desCipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] numeroCifrado = desCipher.doFinal(String.valueOf(numero).getBytes());
            oos.writeObject(numeroCifrado);
            byte[] numeroCifradoIntento = (byte[]) ois.readObject();
            desCipher.init(Cipher.DECRYPT_MODE, key);
            int numeroIntento = Integer.parseInt(new String(desCipher.doFinal(numeroCifradoIntento)));
            if (numeroIntento == numero) {
                oos.writeObject(true);
                //insertamos los datos
                String query = "INSERT INTO transacciones(ncuentaorigen, ncuentadestino, fecha, cantidad, descripcion) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, transferencia.getOrigen());
                ps.setString(2, transferencia.getDestino());
                ps.setString(3, String.valueOf(LocalDateTime.now()));
                ps.setDouble(4, transferencia.getCantidad());
                ps.setString(5, transferencia.getDescripcion());
                ps.execute();
                //actulizamos las cuentas
                textArea.append("Actualizando las cuentas de la transferencia que ha realizada " + user.getUsuario() + "\n");
                query = "UPDATE cuentas SET saldo = saldo - ?  WHERE ncuenta = ?";
                ps = conexion.prepareStatement(query);
                ps.setDouble(1, transferencia.getCantidad());
                ps.setString(2, transferencia.getOrigen());
                ps.execute();
                query = "UPDATE cuentas SET saldo = saldo + ?  WHERE ncuenta = ?";
                ps = conexion.prepareStatement(query);
                ps.setDouble(1, transferencia.getCantidad());
                ps.setString(2, transferencia.getDestino());
                ps.execute();
                ps.close();
            } else {
                oos.writeObject(false);
            }
            bis.close();
            oisbytes.close();
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ha surgido un error al realizar una sentenia SQL");
            logger.addHandler(fh);
        } catch (InvalidKeyException e) {
            logger.log(Level.WARNING, "La clave no es la correcta para poder descifrar");
            logger.addHandler(fh);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "No se ha podido realizar el casteo de datos");
            logger.addHandler(fh);
        } catch (IOException ignored) {

        }
    }

    public void cargarCuentas() {
        try {
            List<Cuenta> cuentas = new ArrayList<>();
            textArea.append(user.getUsuario() + " esta cargando los datos para hacer una transferencia.\n");
            String query = "SELECT * FROM cuentas WHERE idusuario = ?";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
            textArea.append("Enviando datos de cuentas a " + user.getUsuario() + "\n");
            oos.writeObject(cuentasCifradas);
            bos.close();
            oosbytes.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ha surgido un error al realizar una sentenia SQL");
            logger.addHandler(fh);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (InvalidKeyException e) {
            logger.log(Level.WARNING, "La clave no es la correcta para poder descifrar");
            logger.addHandler(fh);
        } catch (IOException ignored) {

        }
    }

    public void cargarMovimientos() {
        try {
            List<Transferencia> movimientos = new ArrayList<>();
            textArea.append(user.getUsuario() + " esta cargando los movimientos.\n");
            String query = "SELECT * FROM transacciones WHERE ncuentaorigen IN (SELECT ncuenta FROM cuentas WHERE idusuario = ?) OR ncuentadestino IN (SELECT ncuenta FROM cuentas WHERE idusuario = ?);";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transferencia transferencia = new Transferencia(rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getString(6), rs.getString(4));
                movimientos.add(transferencia);
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oosbytes = new ObjectOutputStream(bos);
            oosbytes.writeObject(movimientos);
            oosbytes.flush();
            byte[] cuentasbytes = bos.toByteArray();
            desCipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cuentasCifradas = desCipher.doFinal(cuentasbytes);
            textArea.append("Enviando datos transacciones de " + user.getUsuario() + "\n");
            oos.writeObject(cuentasCifradas);
            bos.close();
            oosbytes.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ha surgido un error al realizar una sentenia SQL");
            logger.addHandler(fh);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.log(Level.WARNING, "Ha surgido un error inesperado");
            logger.addHandler(fh);
        } catch (InvalidKeyException e) {
            logger.log(Level.WARNING, "La clave no es la correcta para poder descifrar");
            logger.addHandler(fh);
        } catch (IOException ignored) {

        }
    }
}
