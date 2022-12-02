package swing;

import clases.Cuenta;
import clases.Transferencia;
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

public class VentanaMovimientos extends JFrame {
    private JPanel panel;
    private JTable table1;
    private JButton atrasButton;
    private SecretKey key;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public VentanaMovimientos(ObjectInputStream ois, ObjectOutputStream oos, SecretKey key) {
        this.key = key;
        this.oos = oos;
        this.ois = ois;
        setContentPane(panel);
        cargarDatos();
        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new VentanaMenuPrincipal(ois, oos, key);
                frame.setSize(300, 300);
                frame.setVisible(true);
                dispose();
            }
        });
    }

    public void cargarDatos() {
        try {
            oos.writeObject(4);
            Cipher desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] movimientosCifradas = (byte[]) ois.readObject();
            byte[] movimientosBytes = desCipher.doFinal(movimientosCifradas);
            ByteArrayInputStream bis = new ByteArrayInputStream(movimientosBytes);
            ObjectInputStream oisbytes = new ObjectInputStream(bis);
            List<Transferencia> movimientos = (List<Transferencia>) oisbytes.readObject();
            bis.close();
            oisbytes.close();
            int cantidad = movimientos.size();
            String[][] d = new String[cantidad][5];
            String[] nombreColumnas = {"Origen", "Destino", "Descripcion", "Cantidad", "Fecha"};
            for (int i = 0; i < movimientos.size(); i++) {
                d[i][0] = String.valueOf(movimientos.get(i).getOrigen());
                d[i][1] = String.valueOf(movimientos.get(i).getDestino());
                d[i][2] = String.valueOf(movimientos.get(i).getDescripcion());
                d[i][3] = String.valueOf(movimientos.get(i).getCantidad());
                d[i][4] = String.valueOf(movimientos.get(i).getFecha());
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
