package swing;

import clases.Cuenta;
import clases.Transferencia;
import clases.User;

import javax.crypto.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class VentanaTransferencia extends JFrame {
    private JPanel panel;
    private JComboBox comboBoxOrigen;
    private JComboBox comboBoxDestino;
    private JButton transferirButton;
    private JTextField cantidadField;
    private JButton atrasButton;
    private JTextField textField1;
    private JTextField descripcionField;
    private User user;

    public VentanaTransferencia(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        setContentPane(panel);
        try {
            oos.writeObject(3);
            byte[] cuentasEncriptadas = (byte[]) ois.readObject();
            Cipher desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cuentasBytes = desCipher.doFinal(cuentasEncriptadas);
            ByteArrayInputStream bis = new ByteArrayInputStream(cuentasBytes);
            ObjectInputStream oisbytes = new ObjectInputStream(bis);
            List<Cuenta> cuentas = (List<Cuenta>) oisbytes.readObject();
            bis.close();
            oisbytes.close();
            for (Cuenta cuenta : cuentas) {
                comboBoxOrigen.addItem(cuenta.getNumero());
            }
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        transferirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double cantidad = Double.parseDouble(cantidadField.getText());
                    if (!textField1.getText().isBlank() && !descripcionField.getText().isBlank()) {
                        oos.writeObject(true);
                        String ncuentadestino = comboBoxOrigen.getSelectedItem().toString();
                        Transferencia transferencia = new Transferencia(ncuentadestino, textField1.getText(), cantidad, descripcionField.getText());
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oosbytes = new ObjectOutputStream(bos);
                        oosbytes.writeObject(transferencia);
                        oosbytes.flush();
                        byte[] transferenciabytes = bos.toByteArray();
                        Cipher desCipher = Cipher.getInstance("DES");
                        //configuramos modo encriptar y ciframos
                        desCipher.init(Cipher.ENCRYPT_MODE, key);
                        byte[] transferenciaCifrado = desCipher.doFinal(transferenciabytes);
                        //enviamos objeto cifrado
                        oos.writeObject(transferenciaCifrado);
                        JFrame frame = new VentanaCodigo(ois, oos, key);
                        frame.setSize(300, 300);
                        frame.setVisible(true);
                        JFrame movil = new VentanaMovil(ois, oos, key);
                        movil.setSize(300, 300);
                        movil.setVisible(true);
                        dispose();
                    }
                } catch (NumberFormatException ex) {
                    //numero error
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalBlockSizeException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (BadPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    oos.writeObject(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JFrame frame = new VentanaMenuPrincipal(ois, oos, key);
                frame.setSize(300, 300);
                frame.setVisible(true);
                dispose();
            }
        });
    }
}
