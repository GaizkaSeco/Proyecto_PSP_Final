package swing;

import clases.Cuenta;
import clases.User;

import javax.crypto.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class VentanaCuentas extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton atrasButton;
    private Socket socket;
    private SecretKey key;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public VentanaCuentas(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        this.key = key;
        this.oos = oos;
        this.ois = ois;
        setContentPane(panel1);
        cargarDatos();

        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //volver a la ventana principal
                JFrame frame = new VentanaMenuPrincipal(ois, oos, key);
                frame.setSize(300, 300);
                frame.setVisible(true);
                dispose();
            }
        });
    }

    public void cargarDatos() {
        try {
            oos.writeObject(1);
            Cipher desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cuentasCifradas = (byte[]) ois.readObject();
            byte[] cuentasBytes = desCipher.doFinal(cuentasCifradas);
            ByteArrayInputStream bis = new ByteArrayInputStream(cuentasBytes);
            ObjectInputStream oisbytes = new ObjectInputStream(bis);
            List<Cuenta> cuentas = (List<Cuenta>) oisbytes.readObject();
            bis.close();
            oisbytes.close();
            int cantidad = cuentas.size();
            String[][] d = new String[cantidad][5];
            String[] nombreColumnas = {"Numero de cuenta", "Saldo"};
            for (int i = 0; i < cuentas.size(); i++) {
                d[i][0] = String.valueOf(cuentas.get(i).getNumero());
                d[i][1] = String.valueOf(cuentas.get(i).getSaldo());
            }
            table1.setModel(new DefaultTableModel(d, nombreColumnas));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
